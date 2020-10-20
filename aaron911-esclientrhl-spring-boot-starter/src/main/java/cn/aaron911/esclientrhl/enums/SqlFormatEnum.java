package cn.aaron911.esclientrhl.enums;

/**
 * 
 * @description: sql请求返回类型
 * 
 **/
public enum SqlFormatEnum {
    CSV("csv", "text/csv"),
    JSON("json", "application/json"),
    TSV("tsv", "text/tab-separated-values"),
    TXT("txt", "text/plain"),
    YAML("yaml", "application/yaml"),
    CBOR("cbor", "application/cbor"),
    SMILE("smile", "application/smile");


    private String format;
    private String acceptHttpHeader;

    private SqlFormatEnum(String format, String acceptHttpHeader) {
        this.format = format;
        this.acceptHttpHeader = acceptHttpHeader;
    }


    @Override
    public String toString() {
        return this.format;
    }


    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getAcceptHttpHeader() {
        return acceptHttpHeader;
    }

    public void setAcceptHttpHeader(String acceptHttpHeader) {
        this.acceptHttpHeader = acceptHttpHeader;
    }
}
