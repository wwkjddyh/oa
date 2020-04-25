package com.oa.platform.util;

import com.google.common.collect.Maps;
import com.oa.platform.common.Constants;
import com.oa.platform.common.FileType;
import com.oa.platform.vo.MyField;
import com.oa.platform.vo.MyFieldVo;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

import static com.oa.platform.util.DateUtil.FORMAT_YEAR_MONTH_DAY;

/**
 * Poi组件操作
 * @author feng
 * @date 2019/04/26
 */
public class PoiUtil {

    private PoiUtil() {

    }

    private final static Logger LOGGER = LoggerFactory.getLogger("PoiUtil");

    /**
     * 解析Excel文件
     * @param file 文件
     * @param sheetHeaders sheet表头字段名字(key: sheet名字, value: 表头字段名列表)
     * @return
     * @throws Exception
     */
    public static Map<String, List<Map<String, Object>>> parseExcel(
            File file, Map<String, List<String>> sheetHeaders) {
        Map<String, List<Map<String, Object>>> ret = new LinkedHashMap<>();
        try {
            Workbook workBook = buildWorkbookByFile(file);
            if(workBook != null) {
                int sheetNums = workBook.getNumberOfSheets();
                if(sheetNums > 0) {
                    Sheet sheet = null;
                    for(int i=0; i<sheetNums; i++) {
                        sheet = workBook.getSheetAt(i);
                        List<Map<String, Object>> rows = new ArrayList<>();
                        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                        String sheetName = sheet.getSheetName();
                        if(physicalNumberOfRows > 0) {
                            List<String> header = null;
                            if (sheetHeaders != null && sheetHeaders.containsKey(sheetName)) {
                                header = sheetHeaders.get(sheetName);
                            }
                            Row firstRow = sheet.getRow(0);
                            if (header == null || header.isEmpty()) {
                                header = new ArrayList<>(0);
                                for(int k=0; k<firstRow.getPhysicalNumberOfCells(); k++) {
                                    Cell cell = firstRow.getCell(k);
                                    if(cell != null) {
                                        header.add(k + "");
                                    }
                                }
                            }

                            int headerSize = header.size();
                            if(headerSize > 0) {
                                // 从下标为1的row开始取数据
                                for (int j = 1; j < physicalNumberOfRows; j++) {
                                    Row row = sheet.getRow(j);
                                    Map<String, Object> rowData = new LinkedHashMap<>();
                                    for (int k = 0; k<headerSize; k++) {
                                        Cell cell = row.getCell(k);
                                        String cellVal = "";
                                        if(cell != null) {
                                            cellVal = cell.toString();
                                        }
                                        rowData.put(header.get(k), cellVal);
                                    }
                                    rows.add(rowData);
                                }
                            }
                        }
                        ret.put(sheetName, rows);
                    }
                }
            }
        }
        catch (Exception e) {
            LOGGER.error("PoiUtil.parseExcel", e);
        }
        return ret;
    }

    /**
     * 构建Excel文件
     * @param parentDir 目录
     * @param fileName 文件名
     * @param fieldVos 数据信息
     * @throws Exception
     */
    public static void buildExcelFile(String parentDir, String fileName, List<MyFieldVo> fieldVos) throws Exception {
        parentDir = StringUtil.trim(parentDir);
        fileName = StringUtil.trim(fileName);
        if(!"".equals(fileName) && !"".equals(parentDir)) {
            FileOutputStream fos = null;
            Workbook workBook = null;
            try {
                if(fileName.toLowerCase(Constants.LOCALE_DEFAULT).endsWith(FileType.XLS.getFormat())) {
                    workBook = new HSSFWorkbook();
                }
                else if(fileName.toLowerCase(Constants.LOCALE_DEFAULT).endsWith(FileType.XLSX.getFormat())) {
                    workBook = new XSSFWorkbook();
                }

                fillFieldVos(workBook, fieldVos);
                fos = new FileOutputStream(FileUtil.createFile(parentDir, fileName));
//                workBook.write(fos);
//                fos.flush();
                writeToOutputStream(workBook, fos);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(workBook != null) {
                    workBook.close();
                }
                if(fos != null) {
                    fos.close();
                }
            }
        }
    }

    /**
     * 写入文件输出流
     * @param workBook 工作表
     * @param os 输出流
     * @throws IOException
     */
    public static void writeToOutputStream(Workbook workBook, OutputStream os) throws IOException {
        if (workBook != null && os != null) {
            workBook.write(os);
            os.flush();
        }
    }

    /**
     * 填充数据
     * @param workBook
     * @param fieldVos
     */
    public static void fillFieldVos(Workbook workBook, List<MyFieldVo> fieldVos) {
        if(workBook != null && null != fieldVos && fieldVos.size() > 0) {
            int voSize = fieldVos.size();
            for (int v=0;v<voSize;v++) {
                MyFieldVo myFieldVo = fieldVos.get(v);
                String sheetName = myFieldVo.getName();

                Sheet sheet = workBook.createSheet(sheetName);
                Row row = sheet.createRow(0);
                Cell cell = null;

                // 工作薄格式
                CellStyle textStyle = workBook.createCellStyle();
                DataFormat format = workBook.createDataFormat();
                textStyle.setDataFormat(format.getFormat("@"));

                //单元格格式
                CellStyle cellStyle = workBook.createCellStyle();
                Font font = workBook.createFont();
                font.setFontName("宋体");
                font.setFontHeightInPoints((short) 11);//设置字体大小
//                    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
                cellStyle.setFont(font);//选择需要用到的字体格式

                List<MyField> fields = myFieldVo.getMyFields();
                if(null != fields && fields.size() > 0) {
                    for (int i = 0; i < fields.size(); i++) {
                        MyField field = fields.get(i);
                        String typeName = field.getTypeName();
                        String filedName = field.getFieldName();
                        int length = NumUtil.averageNumber(filedName.getBytes().length * 1.7 * 256, 1);
                        sheet.setColumnWidth(i, length);
                        sheet.setDefaultColumnStyle(i, textStyle);
                        // 单元格格式暂时就这两种类型，有需要可以自行添加
                        switch (typeName.toUpperCase(Constants.LOCALE_DEFAULT)) {
                            case "STRING":
                                cell = row.createCell(i);
                                cell.setCellType(CellType.STRING);
                                break;
                            case "INTEGER":
                                cell = row.createCell(i);
                                cell.setCellType(CellType.NUMERIC);
                                break;
                            case "BOOLEAN":
                                cell = row.createCell(i);
                                cell.setCellType(CellType.BOOLEAN);
                                break;
                            case "FORMULA":
                                cell = row.createCell(i);
                                cell.setCellType(CellType.FORMULA);
                                break;
                        }
                        cell.setCellValue(filedName);
                        cell.setCellStyle(cellStyle);;
                    }
                }
                //添加数据
                Row dataRow = null;
                if(null != myFieldVo.getDataRows()) {
                    List<List<MyField>> dataRows = myFieldVo.getDataRows();
                    int size = dataRows.size();
                    for(int i=0; i<size; i++) {
                        dataRow = sheet.createRow(i+1);
                        List<MyField> rowData = dataRows.get(i);
                        int len = rowData.size();
                        for(int j=0;j<len;j++) {
                            cell = dataRow.createCell(j);
                            cell.setCellValue(rowData.get(j).getFieldName());
                        }
                    }
                }
            }
        }
    }

    /**
     * 单元格值转换为字符串
     * @param cell 单元格对象
     * @return 返回该单元格相应的类型的值
     */
    public static String cellToStr(Cell cell) {
        String str = null;
        if(cell != null) {
            CellType cellType = cell.getCellType();
            if(cellType.equals(CellType.STRING)) {
                str = cell.getStringCellValue();
            }
            else if(cellType.equals(CellType.FORMULA)) {
                try {
                    str = String.valueOf(cell.getCellFormula());
                } catch (IllegalStateException e) {
                    str = String.valueOf(cell.getRichStringCellValue());
                }
            }
            else if(cellType.equals(CellType.NUMERIC)) {
                short format = cell.getCellStyle().getDataFormat();
                if (format == 14 || format == 31 || format == 57 || format == 58) { // excel中的时间格式
                    double value = cell.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                    str = DateUtil.dateToStr(date, FORMAT_YEAR_MONTH_DAY);
                }
                // 判断当前的cell是否为Date
                else if (HSSFDateUtil.isCellDateFormatted(cell)) { // 先注释日期类型的转换，在实际测试中发现HSSFDateUtil.isCellDateFormatted(cell)只识别2014/02/02这种格式。
                    // 如果是Date类型则，取得该Cell的Date值 // 对2014-02-02格式识别不出是日期格式
                    Date date = cell.getDateCellValue();
                    str = DateUtil.dateToStr(date, FORMAT_YEAR_MONTH_DAY);
                } else { // 如果是纯数字
                    // 取得当前Cell的数值
                    str = NumberToTextConverter.toText(cell.getNumericCellValue());

                }
            }
            else if(cellType.equals(CellType.BLANK)) {
                cell.setCellType(CellType.BLANK);
                str = cell.getStringCellValue();
            }
        }
        return StringUtil.trim(str);
    }

    /**
     * 解析excel文件
     * @param file excel文件(例如：C:\\work\\test\\test-demo1.xlsx)
     * @param models 模版信息(sheet列表，每个MyFieldVo代表一个表单模版;sheet名称不能重复，单个sheet列名不能重复)
     * @param showCnName (工作薄)显示中文名｛true:根据'cnName'获取sheet，false:根据'name'获取sheet｝
     * @param isNeedTitle 是否需要标题
     * @return  e.g：{"tb_base_info":[{"password":"11111","userid":"1","username":"李三"},{"password":"11111","userid":"2","username":"张三"}],
     * 			"tb_dtl_info":[{"address":"不告诉你","netaddr":"qq.com","telphone":"123456","userid":"1"},{"address":"不告诉你","netaddr":"qq.com","telphone":"123456","userid":"2"}]}
     */
    public static Map<String,List<Map<String,Object>>> parserExcel(File file, List<MyFieldVo> models, boolean showCnName, boolean isNeedTitle) {
        Map<String,List<Map<String,Object>>> vos = new HashMap<>();
        if(null != file && null != models && !models.isEmpty() && file.exists()) {
            try {
                Workbook workBook = buildWorkbookByFile(file);
                if(null != workBook && workBook.getNumberOfSheets() == models.size()) {
                    int rowStart = isNeedTitle ? 0 : 1;
                    for(MyFieldVo model : models) {
                        String sheetName = model.getCnName();
                        String tbName = model.getName();
                        Sheet sheet = workBook.getSheet(tbName);
                        List<MyField> fields = model.getMyFields();
                        List<Map<String,Object>> sheetValues = parseSheetData(sheet, fields, rowStart);
                        if(!showCnName) {
                            sheetName = tbName;
                        }
                        vos.put(sheetName, sheetValues);
                    }
                }
            }
            catch(Exception e) {
                LOGGER.error("PoiUtil.parserExcel", e);
            }
        }
        return vos;
    }

    /**
     * 解析excel文件
     * @param parentDir 父级文件目录
     * @param fileName 文件名(带路径)
     * @param models 模版(key：工作表名称, value：工作表字段名列表)
     * @param isNeedTitle 是否显示标题
     * @return {key：工作簿名称，value：工作簿数据}
     */
    public static Map<String, List<Map<String,Object>>> parserExcel(String parentDir, String fileName,
                                                                   LinkedHashMap<String,List<MyField>> models,
                                                                   boolean isNeedTitle) {
        Map<String,List<Map<String,Object>>> vos = new HashMap<>();
        try {
            parentDir = StringUtil.trim(parentDir);
            fileName = StringUtil.trim(fileName);
            if("".equals(fileName)) {
                throw new Exception("file does not exist");
            }
            vos = parserExcel(FileUtil.createFile(parentDir, fileName), models, isNeedTitle);
        }
        catch (Exception e) {
            LOGGER.error("PoiUtil.parserExcel", e);
        }
        return vos;
    }

    /**
     * 解析excel文件
     * @param file 文件
     * @param models 模版(key：工作表名称, value：工作表字段名列表)
     * @param isNeedTitle 是否显示标题
     * @return {key：工作簿名称，value：工作簿数据}
     */
    public static Map<String,List<Map<String,Object>>> parserExcel(File file,
                                                                   LinkedHashMap<String,List<MyField>> models,
                                                                   boolean isNeedTitle) {
        Map<String,List<Map<String,Object>>> vos = new HashMap<>();
        try {
            Workbook workBook = buildWorkbookByFile(file);
            if(null != workBook && workBook.getNumberOfSheets() == models.keySet().size()) {
                int rowStart = isNeedTitle ? 0 : 1;
                for(Map.Entry<String, List<MyField>> entry : models.entrySet()) {
                    String sheetName = entry.getKey();
                    List<MyField> fields = entry.getValue();
                    Sheet sheet = workBook.getSheet(sheetName);
                    List<Map<String,Object>> sheetData = parseSheetData(sheet, fields, rowStart);
                    vos.put(sheetName, sheetData);
                }
            }
        }
        catch (Exception e) {
            LOGGER.error("PoiUtil.parserExcel", e);
        }
        return vos;
    }

    /**
     * 解析excel文件
     * @param in 输入流
     * @param isXls 是否为xls格式文件
     * @param models 模版(key：工作表名称, value：工作表字段名列表)
     * @param isNeedTitle 是否显示标题
     * @return {key：工作簿名称，value：工作簿数据}
     */
    public static Map<String,List<Map<String,Object>>> parserExcel(InputStream in, boolean isXls,
                                                                   LinkedHashMap<String,List<MyField>> models,
                                                                   boolean isNeedTitle) {
        Map<String,List<Map<String,Object>>> vos = new HashMap<>();
        try {
            Workbook workBook = buildWorkbookByInputStream(in, isXls);
            if(null != workBook && workBook.getNumberOfSheets() == models.keySet().size()) {
                int rowStart = isNeedTitle ? 0 : 1;
                for(Map.Entry<String, List<MyField>> entry : models.entrySet()) {
                    String sheetName = entry.getKey();
                    List<MyField> fields = entry.getValue();
                    Sheet sheet = workBook.getSheet(sheetName);
                    List<Map<String,Object>> sheetData = parseSheetData(sheet, fields, rowStart);
                    vos.put(sheetName, sheetData);
                }
            }
        }
        catch (Exception e) {
            LOGGER.error("PoiUtil.parserExcel", e);
        }
        return vos;
    }

    /**
     * 解析工作表数据(工作表的列需要与fields的长度一致)
     * @param sheet 工作表
     * @param fields 标题列信息
     * @param rowStart 起始行
     * @return
     */
    private static List<Map<String,Object>> parseSheetData(Sheet sheet, List<MyField> fields, int rowStart) {
        List<Map<String,Object>> sheetData = new ArrayList<>();
        try {
            if (sheet == null) {    // 工作表名称无效
                throw new Exception("sheet name is invalid");
            }
            if (fields == null || fields.isEmpty()) {   //  标头数据无效
                throw new Exception("header data are invalid");
            }
            if (rowStart < 0) { //  起始行下标不能为负数
                throw new Exception("rowStart cannot be negative");
            }
            for(int j = rowStart; j<sheet.getPhysicalNumberOfRows(); j++) {
                Row row = sheet.getRow(j);
                Map<String,Object> rowData = new HashMap<>();
                for(int n = 0; n< fields.size();n++) {
                    MyField myField = fields.get(n);
                    Cell cell = row.getCell(n);
                    rowData.put(myField.getFieldName(), cellToStr(cell));
                }
                sheetData.add(rowData);
            }
        }
        catch (Exception e) {
            LOGGER.error("PoiUtil.parseSheetData", e);
        }
        return sheetData;
    }

    /**
     * 根据文件构建工作薄
     * @param file 文件对象
     * @return
     */
    public static Workbook buildWorkbookByFile(File file) {
        Workbook workbook = null;
        try {
            if(file == null || !file.exists()) {
                throw new Exception("file does not exist");
            }
            String fileName = file.getName();
            InputStream in = new FileInputStream(file);
            if(fileName.toLowerCase(Constants.LOCALE_DEFAULT).endsWith(FileType.XLS.getFormat())) {
                workbook = new HSSFWorkbook(in);
            }
            else if(fileName.toLowerCase(Constants.LOCALE_DEFAULT).endsWith(FileType.XLSX.getFormat())) {
                workbook = new XSSFWorkbook(in);
            }
        }
        catch (Exception e) {
            LOGGER.error("PoiUtil.buildWorkbookByFile", e);
        }
        return workbook;
    }

    /**
     * 根据流、文件名构建工作薄
     * @param in 输入流
     * @param isXls 是否为xls格式文件
     * @return
     */
    public static Workbook buildWorkbookByInputStream(InputStream in, boolean isXls) {
        Workbook workbook = null;
        try {
            if(isXls) {
                workbook = new HSSFWorkbook(in);
            }
            else {
                workbook = new XSSFWorkbook(in);
            }
        }
        catch (Exception e) {
            LOGGER.error("PoiUtil.buildWorkbookByFile", e);
        }
        return workbook;
    }

    /**
     * 根据文件名构建工作薄
     * @param parentDir 文件所在目录
     * @param fileName 文件名
     * @return
     */
    public static Workbook buildWorkbookByFileName(String parentDir, String fileName) {
        parentDir = StringUtil.trim(parentDir);
        fileName = StringUtil.trim(fileName);
        try {
            if("".equals(fileName) || "".equals(parentDir)) {
                throw new Exception("file does not exist");
            }
            return buildWorkbookByFile(FileUtil.createFile(parentDir, fileName));
        }
        catch (Exception e) {
            LOGGER.error("PoiUtil.buildWorkbookByFileName", e);
        }
        return null;
    }

    public static void main(String... args) throws Exception {
        String parentDir = "D:\\work\\test";
        String fileName = "test.xls";
        List<MyFieldVo> fieldVos = new ArrayList<>();
        // 标题信息
        List<MyField> header = new ArrayList<MyField>() {
            {
                add(new MyField("userName", MyField.TYPE_STRING, 255, false, "用户名", "用户的姓名"));
                add(new MyField("nickname", MyField.TYPE_STRING, 255, false, "用户昵称", "用户的昵称"));
                add(new MyField("age", MyField.TYPE_STRING, 16, false, "用户性别", "用户的性别"));
            }
        };
        // 数据
        List<List<MyField>> dataRows = new ArrayList<>();
        dataRows.add(new ArrayList<MyField>() {
            {
                add(new MyField("张三", MyField.TYPE_STRING));
                add(new MyField("张三", MyField.TYPE_STRING));
                add(new MyField("男", MyField.TYPE_STRING));
            }
        });
        fieldVos.add(new MyFieldVo(){{
            setMyFields(header);
            setDataRows(dataRows);
            setCnName("测试xls生成");
            setName("test-xls");
        }});
        buildExcelFile(parentDir, fileName, fieldVos);

        // 解析xls
//        List<MyFieldVo> models = new ArrayList<>();
//        models.add(new MyFieldVo() {
//            {
//                setMyFields(header);
//                setCnName("测试xls生成");
//                setName("test-xls");
//            }
//        });
//        File file = new File(parentDir, fileName);
//        System.err.println(file.getName() + "\n" + file.getPath());
//        Map<String,List<Map<String,Object>>> ret = parserExcel(file, models, true, true);
//        System.err.println("ret => " + ret);
//
//        LinkedHashMap<String, List<MyField>> models2 = Maps.newLinkedHashMap();
//        models2.put("test-xls", header);
//        ret = parserExcel(parentDir, fileName, models2, true);
//        System.err.println("ret => " + ret);
    }

}
