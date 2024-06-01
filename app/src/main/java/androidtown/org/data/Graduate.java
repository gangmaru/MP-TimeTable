package androidtown.org.data;

public class Graduate {

    //졸업 학점
    private final int jol_credit;
    //전공 필수
    private final int majbas_credit;
    //전공 선택
    private final int majsel_credit;
    //교양 필수(기초)
    private final int culess_credit;
    //취득 영역 소계
    private final int comb_cul_tot_credit;
    //인간과예술(융합)
    private final int cul1_credit;
    //사회와역사(융합)
    private final int cul2_credit;
    //과학과정보통신(융합)
    private final int cul3_credit;
    //세계와문화(융합)
    private final int cul4_credit;
    //자유교양
    private final int cul5_credit;
    //창의와융합(기초)
    private final int cul6_credit;
    //계열 교양
    private final int culbranch_credit;

    public Graduate(int jolCredit, int majbasCredit, int majselCredit, int culessCredit, int combCulTotCredit, int cul1Credit, int cul2Credit, int cul3Credit, int cul4Credit, int cul5Credit, int cul6Credit, int culbranchCredit) {
        jol_credit = jolCredit;
        majbas_credit = majbasCredit;
        majsel_credit = majselCredit;
        culess_credit = culessCredit;
        comb_cul_tot_credit = combCulTotCredit;
        cul1_credit = cul1Credit;
        cul2_credit = cul2Credit;
        cul3_credit = cul3Credit;
        cul4_credit = cul4Credit;
        cul5_credit = cul5Credit;
        cul6_credit = cul6Credit;
        culbranch_credit = culbranchCredit;
    }

    public int getJol_credit() {
        return jol_credit;
    }

    public int getMajbas_credit() {
        return majbas_credit;
    }

    public int getMajsel_credit() {
        return majsel_credit;
    }

    public int getCuless_credit() {
        return culess_credit;
    }

    public int getComb_cul_tot_credit() {
        return comb_cul_tot_credit;
    }

    public int getCul1_credit() {
        return cul1_credit;
    }

    public int getCul2_credit() {
        return cul2_credit;
    }

    public int getCul3_credit() {
        return cul3_credit;
    }

    public int getCul4_credit() {
        return cul4_credit;
    }

    public int getCul5_credit() {
        return cul5_credit;
    }

    public int getCul6_credit() {
        return cul6_credit;
    }

    public int getCulbranch_credit() {
        return culbranch_credit;
    }
}
