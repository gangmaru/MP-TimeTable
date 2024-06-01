package androidtown.org.data.type;

public enum DataType {

    GRADE("PTL008"),
    SCHEDULE("PTL014"),
    GRADUATE("PTL031"),
    QR("https://portal.gachon.ac.kr/gc/common/UserInfoChange.eps");

    private final String urlNum;

    DataType(String urlNum){
        this.urlNum = urlNum;
    }

    public String getUrlNum() {
        return urlNum;
    }
}
