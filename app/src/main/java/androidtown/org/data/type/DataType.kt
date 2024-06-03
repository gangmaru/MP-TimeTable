package androidtown.org.data.type;

public enum DataType {

    GRADE("PTL008"),
    SCHEDULE("PTL014"),
    GRADUATE("PTL031"),
    DEPARTMENT("PTL011"),
    NAME("PTL024");

    private final String urlNum;

    DataType(String urlNum){
        this.urlNum = urlNum;
    }

    public String getUrlNum() {
        return urlNum;
    }
}
