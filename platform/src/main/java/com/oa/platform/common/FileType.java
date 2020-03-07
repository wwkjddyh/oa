package com.oa.platform.common;

/**
 * 文件类型
 * @author feng
 * @date 2019/04/27
 */
public enum FileType {

    XLS("Excel文件格式：xls", "xls", ".xls"),
    XLSX("Excel文件格式：xlsx", "xlsx", ".xlsx"),
    DOC("文档文件格式：doc","doc", ".doc"),
    DOCX("文档文件格式：docx", "docx", ".docx"),
    PNG("图片文件格式：png", "png", ".png"),
    JPG("图片文件格式：jpg", "jpg", ".jpg"),
    JPEG("图片文件格式：jpeg", "jpeg", ".jpeg"),
    IMG("图片文件格式：img", "img", ".img"),
    BMP("图片文件格式：bmp", "bmp", ".bmp"),
    PDF("PDF文件格式：", "pdf", ".pdf");



    FileType(String name, String suffix, String format) {
        this.name = name;
        this.suffix = suffix;
        this.format = format;
    }

    /**
     * 文件类型说明/名称
     */
    private String name;

    /**
     * 文件后缀（例如：xls）
     */
    private String suffix;

    /**
     * 文件格式（例如：.xls）
     */
    private String format;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * 根据文件后缀获得文件类型信息
     * @param suffix 文件后缀（例如：xls）
     * @return
     */
    public static FileType getBySuffix(String suffix) {
        suffix = suffix == null ? "" : suffix.trim().toLowerCase(Constants.LOCALE_DEFAULT);
        if(!"".equals(suffix)) {
            for (FileType fileType : values()) {
                if (fileType.suffix.equals(suffix)) {
                    return fileType;
                }
            }
        }
        throw new IllegalArgumentException("Invalid FileType suffix: " + suffix);
    }

    /**
     * 根据文件格式获得文件类型信息
     * @param format 文件格式（例如：.xls）
     * @return
     */
    public static FileType getByFormat(String format) {
        format = format == null ? "" : format.trim().toLowerCase(Constants.LOCALE_DEFAULT);
        if(!"".equals(format)) {
            for (FileType fileType : values()) {
                if (fileType.format.equals(format)) {
                    return fileType;
                }
            }
        }
        throw new IllegalArgumentException("Invalid FileType format: " + format);
    }
}
