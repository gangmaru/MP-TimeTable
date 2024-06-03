package androidtown.org.data

data class Graduate(//졸업 학점
        var jol_credit: Int = 0,//계열 교양
        var culbranch_credit: Int = 0,//전공 필수
        var majbas_credit: Int = 0,//전공 선택
        var majsel_credit: Int = 0,//교양 필수(기초)
        var culess_credit: Int = 0,//취득 영역 소계
        var comb_cul_tot_credit: Int = 0,//인간과예술(융합)
        var cul1_credit: Int = 0,//사회와역사(융합)
        var cul2_credit: Int = 0,//과학과정보통신(융합)
        var cul3_credit: Int = 0,//세계와문화(융합)
        var cul4_credit: Int = 0,//자유교양
        var cul5_credit: Int = 0,//창의와융합(기초)
        var cul6_credit: Int = 0) {
}
