package androidtown.org.data.type;

public enum DataType {

    GRADE("PTL008"), SCHEDULE("PTL035");

    private final String urlNum;

    DataType(String urlNum){
        this.urlNum = urlNum;
    }

    public String getUrlNum() {
        return urlNum;
    }
}
