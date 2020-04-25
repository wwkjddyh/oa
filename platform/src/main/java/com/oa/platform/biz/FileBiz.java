package com.oa.platform.biz;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oa.platform.common.Constants;
import com.oa.platform.common.FileType;
import com.oa.platform.util.FileUtil;
import com.oa.platform.util.PoiUtil;
import com.oa.platform.util.StringUtil;
import com.oa.platform.vo.MyField;
import com.oa.platform.vo.MyFieldVo;

import opennlp.tools.parser.Cons;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * 文件上传下载
 * @author feng
 * @date 2019/10/20
 */
@Component
public class FileBiz extends BaseBiz {
    private final Logger log = LoggerFactory.getLogger(FileBiz.class);

    /**
     * 上传文件保存目录
     */
    @Value(value="${upload.save-dir}")
    private String saveDir;

    /**
     * 单文件上传
     * @param file 文件
     * @param request HttpServlet请求对象
     * @return
     */
    public Map<String, Object> upload(MultipartFile file, HttpServletRequest request) {

        try {
            //姓名
            String name = getParameter(request,"name");
            System.err.println("姓名：" + name);

            System.err.println(file.getSize());

            System.err.println("saveDir:::"+saveDir);

            // 类型
            String type = getParameter(request, "type", "other");
            System.err.println("类型： " + type);

            // 文件名
            String fileName = file.getOriginalFilename();
            System.err.println("文件名： " + fileName);

            // 过滤从MacOS获取的异常文件名: 如: ":Users:baby:Downloads:哈哈22.jpg"
            int _index = fileName.lastIndexOf(":");
            if (_index >= 0) {
                fileName = fileName.substring(_index);
            }

            // 文件后缀
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            System.err.println("文件后缀名： " + suffixName);

            // 重新生成唯一文件名，用于存储数据库
            String newFileName = UUID.randomUUID().toString()+suffixName;
            System.err.println("新的文件名： " + newFileName);

            //创建文件
            String parent = saveDir + File.separator + type;
            File dest = FileUtil.createFile(parent, newFileName);


            Map<String,Object> map = new HashMap<>();
            map.put("filePath", dest.getAbsolutePath());
            map.put("name", name);
            map.put("size", file.getSize());
            map.put("fileName", fileName);
            map.put("newFileName", newFileName);
            map.put("destName", File.separator + type + File.separator + newFileName);
            File parentFile = dest.getParentFile();
            if (!parentFile.exists() && !parentFile.isDirectory()) {
                parentFile.mkdirs();
            }
            file.transferTo(dest);

            String content = "";
            // 是否解析文件(0, 不解析;1, 解析)
            String parse = StringUtil.trim(request.getParameter("parse"), "0");
            if ("1".equals(parse)) {
                String _fileName = fileName.toLowerCase(Constants.LOCALE_DEFAULT);
                if (_fileName.endsWith(FileType.PDF.getFormat()) ||
                        _fileName.endsWith(FileType.DOC.getFormat()) ||
                        _fileName.endsWith(FileType.DOCX.getFormat())) {
                    try {
                        Tika tika = new Tika();
                        content = tika.parseToString(dest);
                    } catch (Exception e) {
                        System.err.println("文件解析失败：" + fileName);
                        e.printStackTrace();
                    }
                }
            }
            // 是否解析XLS或XLSX文件
            String parseExcel = StringUtil.trim(request.getParameter("parseXls"), "0");
            if ("1".equals(parseExcel) && !"".equals(type)) {
                parseExcelData(file.getInputStream(), fileName, type);
            }

            map.put("content", content);
            ret = this.getSuccessVo("", map);
        } catch (IOException e) {
            ret = this.getErrorVo();
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 多文件上传
     * @param request HttpServlet请求对象
     * @return
     */
    public Map<String, Object> batchUpload(HttpServletRequest request) {
        try {
            String type = getParameter(request, "type", "other");

            List<MultipartFile> fileList = ((MultipartHttpServletRequest)request).getFiles("file");
            /*String contextPath = request.getSession().getServletContext().getRealPath("/") + "\\uploads\\";
            System.err.println("contextPath:"+contextPath);

            contextPath = saveDir;

            File template = new File(contextPath);
            if(!template.exists()) {
                template.mkdir();
            }*/
            Map<String, Object> data = new HashMap<>();
            if(fileList != null && !fileList.isEmpty()) {
                int len = fileList.size();
                for(int i=0;i<len;i++) {
                    MultipartFile file = fileList.get(i);
                    String fileName = file.getOriginalFilename();
                    String suffixName = fileName.substring(fileName.lastIndexOf("."));
                    String newFileName = UUID.randomUUID().toString()+suffixName;

                    String parent = saveDir + File.separator + type;
                    File dest = FileUtil.createFile(parent, newFileName);

                    Map<String,Object> map = new HashMap<>();
                    map.put("size", file.getSize());
                    map.put("contentType", file.getContentType());
                    map.put("destName", File.separator + type + File.separator + newFileName);
                    map.put("fileName", fileName);
                    map.put("newFileName", newFileName);
                    File parentFile = dest.getParentFile();
                    if (!parentFile.exists() && !parentFile.isDirectory()) {
                        parentFile.mkdirs();
                    }
                    file.transferTo(dest);
                    data.put(String.valueOf(i), map);
                }
            }
            ret = this.getSuccessVo("", data);
        }
        catch(IOException e) {
            ret = this.getErrorVo();
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 文件下载
     * @param response 响应对象
     * @param fileName 文件名(若为空，则不下载)
     */
    public void dl(HttpServletResponse response, String fileName) {
        dl(response, saveDir, "", fileName, fileName);
    }

    /**
     * 文件下载
     * @param response 响应对象
     * @param type 文件所属类型(如：res)
     * @param fileName 文件名(若为空，则不下载)
     * @param viewName 显示名称(若为空，则设置为fileName)
     */
    public void dl(HttpServletResponse response, String type, String fileName, String viewName) {
        dl(response, saveDir, type, fileName, viewName);
    }

    /**
     * 文件下载
     * @param response 响应对象
     * @param filePath 文件所在目录(默认为系统设置：saveDir)
     * @param type 文件所属类型(如：res)
     * @param fileRealName 文件真实名称(若为空，则不下载)
     * @param viewName 显示名称(若为空，则设置为fileRealName)
     */
    public void dl(HttpServletResponse response, String filePath, String type, String fileRealName, String viewName) {
        fileRealName = StringUtil.trim(fileRealName);
        if (!"".equals(fileRealName)) {
            BufferedInputStream bis = null;
            OutputStream os = null;
            try {
                filePath = StringUtil.trim(filePath, saveDir);
                type = StringUtil.trim(type);
                if (!"".equals(type)) {
                    filePath = filePath + File.separator + type;
                }
                System.err.println("fileRealName:" + fileRealName);
                viewName = StringUtil.trim(viewName, fileRealName);

                File file = FileUtil.createFile(filePath, fileRealName);
                byte[] buff = new byte[1024];
                response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
                response.setContentType("text/html;charset=" + Constants.DEFAULT_CHARSET);
//            response.setContentType("multipart/form-data");
                // 设置被下载而不是被打开
                response.setContentType("application/gorce-download");
                // 设置被第三方工具打开,设置下载的文件名
                response.setHeader("Content-Disposition", "attachment; fileName="
                        + viewName +";filename*=" + Constants.DEFAULT_CHARSET + "''"
                        + URLEncoder.encode(viewName, Constants.DEFAULT_CHARSET));

                os = response.getOutputStream();
                bis = new BufferedInputStream(new FileInputStream(file));

                int i = bis.read(buff);
                while (i != -1) {
                    os.write(buff,0,buff.length);
                    os.flush();
                    i = bis.read(buff);
                }
            }
            catch(IOException ioe) {
                //ioe.printStackTrace();
                log.error(ioe.getMessage());
            }
            finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bis != null) {
                    try {
                        bis.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 解析Excel文件
     * @param is 输入流
     * @param fileName 文件名
     * @param type (业务)类型
     */
    public void parseExcelData(InputStream is, String fileName, String type) throws IOException {
        type = StringUtil.trim(type);
        String __fileName = StringUtil.trim(fileName).toLowerCase(Constants.LOCALE_DEFAULT);
        if (is != null && !"".equals(__fileName) && !"".equals(type)) {
            if (__fileName.endsWith(FileType.XLS.getFormat()) || __fileName.endsWith(FileType.XLSX.getFormat())) {
                // 根据业务类型(type)进行解析XLS/XLSX文件
                boolean isXls = false;
                if (__fileName.endsWith(FileType.XLS.getFormat())) {
                    isXls = true;
                }
                //解析结果：{key：工作簿名称，value：工作簿数据}
                Map<String, List<Map<String, Object>>> ret = PoiUtil.parseExcel(is, isXls, null);

                // 业务处理，比如入库等等
            }
        }
    }

    /**
     * 根据类型初始化数据
     * @param type (业务)类型
     */
    public List<MyFieldVo> initExcelDataByType(String type) {
        type = StringUtil.trim(type);
        List<MyFieldVo> fieldVos = Lists.newArrayList();
        if ("".equals(type)) {  // 默认数据
            List<MyField> header = Lists.newArrayList();
            header.add(new MyField("field1", MyField.TYPE_STRING, 255, false, "field1", "field1"));
            header.add(new MyField("field2", MyField.TYPE_STRING, 255, false, "field2", "field2"));
            header.add(new MyField("field3", MyField.TYPE_STRING, 255, false, "field3", "field3"));
            // 数据
            List<List<MyField>> dataRows = Lists.newArrayList();
            dataRows.add(Lists.newArrayList(
                    new MyField("field1-value", MyField.TYPE_STRING),
                    new MyField("field2-value", MyField.TYPE_STRING),
                    new MyField("field3-value", MyField.TYPE_STRING)));
            fieldVos.add(new MyFieldVo(){{
                setMyFields(header);
                setDataRows(dataRows);
                setCnName("sheet1");
                setName("sheet1");
            }});
        }
        else {
            // 根据type封装数据
            List<MyField> header = Lists.newArrayList();
            header.add(new MyField("field1", MyField.TYPE_STRING, 255, false, "field1", "field1"));
            header.add(new MyField("field2", MyField.TYPE_STRING, 255, false, "field2", "field2"));
            header.add(new MyField("field3", MyField.TYPE_STRING, 255, false, "field3", "field3"));
            // 数据
            List<List<MyField>> dataRows = Lists.newArrayList();
            dataRows.add(Lists.newArrayList(
                    new MyField("field1-value", MyField.TYPE_STRING),
                    new MyField("field2-value", MyField.TYPE_STRING),
                    new MyField("field3-value", MyField.TYPE_STRING)));
            fieldVos.add(new MyFieldVo(){{
                setMyFields(header);
                setDataRows(dataRows);
                setCnName("sheet1");
                setName("sheet1");
            }});
        }
        return fieldVos;
    }

    /**
     * 导出Excel(默认XLXS格式)
     * @param request HTTP请求对象
     * @param response HTTP响应对象
     * @param isDownload 是否下载(0, 否; 1, 是)
     * @param type (业务)类型
     * @param viewName 显示名称(可不带文件格式后缀), 不能为空
     */
    public void exportExcel(HttpServletRequest request, HttpServletResponse response,
                            Integer isDownload, String type, String viewName) {
        viewName = StringUtil.trim(viewName);
        if (!"".equals(viewName)) {
            try {
                // 默认XLXS格式
                if (!viewName.toLowerCase(Constants.LOCALE_DEFAULT).endsWith(FileType.XLS.getFormat()) &&
                        !viewName.toLowerCase(Constants.LOCALE_DEFAULT).endsWith(FileType.XLSX.getFormat())) {
                    viewName = viewName + FileType.XLSX.getFormat();
                }
                isDownload = isDownload == null ? 1 : 0;
                boolean isXls = false;
                if (viewName.toLowerCase(Constants.LOCALE_DEFAULT).endsWith(FileType.XLS.getFormat())) {
                    isXls = true;
                }

                request.setCharacterEncoding(Constants.DEFAULT_CHARSET);
                response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
                response.setContentType("text/html;charset=" + Constants.DEFAULT_CHARSET);
//                response.reset();
                response.addHeader("Content-Disposition", "attachment; fileName="
                        + viewName +";filename*=" + Constants.DEFAULT_CHARSET + "''"
                        + URLEncoder.encode(viewName, Constants.DEFAULT_CHARSET));
                if (isDownload == 1) {
                    response.setContentType("application/gorce-download");
                }
                else {
                    if (isXls) {
                        response.setContentType("application/x-msexcel");
                    }
                    else {
                        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    }
                }
                //
                List<MyFieldVo> fieldVos = initExcelDataByType(type);
                Workbook workBook = null;
                if (isXls) {
                    workBook = new HSSFWorkbook();
                }
                else {
                    workBook = new XSSFWorkbook();
                }
                // 填充数据
                PoiUtil.fillFieldVos(workBook, fieldVos);
                OutputStream os = response.getOutputStream();
                PoiUtil.writeToOutputStream(workBook, os);
                os.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
