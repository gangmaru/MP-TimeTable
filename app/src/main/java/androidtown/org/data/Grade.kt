package androidtown.org.data;

public class Grade {

    private final String subjectName;
    private final String rank;
    private final String nvl;
    private final String year;
    private final String semester;
    private final String isuName;
    private final String credit;

    public Grade(String subjectName, String rank, String nvl, String year, String semester, String isuName, String credit) {
        this.subjectName = subjectName;
        this.rank = rank;
        this.nvl = nvl;
        this.year = year;
        this.semester = semester;
        this.isuName = isuName;
        this.credit = credit;
    }

    public String getCredit() {
        return credit;
    }

    public String getIsuName() {
        return isuName;
    }

    public String getSemester() {
        return semester;
    }

    public String getNvl() {
        return nvl;
    }

    public String getYear() {
        return year;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getRank() {
        return rank;
    }
}
