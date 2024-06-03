package androidtown.org.data;

public class Graduate {
    public void setJol_credit(int jol_credit) {
        this.jol_credit = jol_credit;
    }

    public void setMajbas_credit(int majbas_credit) {
        this.majbas_credit = majbas_credit;
    }

    public void setMajsel_credit(int majsel_credit) {
        this.majsel_credit = majsel_credit;
    }

    public void setCuless_credit(int culess_credit) {
        this.culess_credit = culess_credit;
    }

    public void setComb_cul_tot_credit(int comb_cul_tot_credit) {
        this.comb_cul_tot_credit = comb_cul_tot_credit;
    }

    public void setCul1_credit(int cul1_credit) {
        this.cul1_credit = cul1_credit;
    }

    public void setCul2_credit(int cul2_credit) {
        this.cul2_credit = cul2_credit;
    }

    public void setCul3_credit(int cul3_credit) {
        this.cul3_credit = cul3_credit;
    }

    public void setCul4_credit(int cul4_credit) {
        this.cul4_credit = cul4_credit;
    }

    public void setCul5_credit(int cul5_credit) {
        this.cul5_credit = cul5_credit;
    }

    public void setCul6_credit(int cul6_credit) {
        this.cul6_credit = cul6_credit;
    }

    public void setCulbranch_credit(int culbranch_credit) {
        this.culbranch_credit = culbranch_credit;
    }

    //졸업 학점
    private  int jol_credit;
    //전공 필수
    private  int majbas_credit;
    //전공 선택
    private  int majsel_credit;
    //교양 필수(기초)
    private  int culess_credit;
    //취득 영역 소계
    private  int comb_cul_tot_credit;
    //인간과예술(융합)
    private  int cul1_credit;
    //사회와역사(융합)
    private  int cul2_credit;
    //과학과정보통신(융합)
    private  int cul3_credit;
    //세계와문화(융합)
    private  int cul4_credit;
    //자유교양
    private  int cul5_credit;
    //창의와융합(기초)
    private  int cul6_credit;
    //계열 교양
    private  int culbranch_credit;

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
