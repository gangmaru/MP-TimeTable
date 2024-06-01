package androidtown.org.data;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Schedule {

    private final String subject_nm_kor;
    private final String class_day;
    private final String start_time;
    private final String end_time;
    private final String loc_nm;

    public Schedule(String subjectNmKor, String classDay, String startTime, String endTime, String locNm) {
        subject_nm_kor = subjectNmKor;
        class_day = classDay;
        start_time = startTime;
        end_time = endTime;
        loc_nm = locNm;
    }

    public String getSubject_nm_kor() {
        return subject_nm_kor;
    }

    public String getClass_day() {
        return class_day;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getLoc_nm() {
        return loc_nm;
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject_nm_kor, class_day, start_time, end_time, loc_nm);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return hashCode() == (obj != null ? obj.hashCode() : 0);
    }
}
