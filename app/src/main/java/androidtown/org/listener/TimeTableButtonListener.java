package androidtown.org.listener;

import android.graphics.Color;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashSet;
import java.util.Set;

import androidtown.org.R;
import androidtown.org.data.type.DataType;
import androidtown.org.fragments.fragment_timetable;

public class TimeTableButtonListener implements View.OnClickListener, WebDataListener {

    private final LinearLayout welcome;
    private final Button timetable;
    private final Button grade;
    private final Button qr;
    private final Button setting;
    private final FragmentManager fragmentManager;
    private final WebView dataWebView1;
    private final WebView dataWebView2;
    private final WebView dataWebView3;
    private final WebView dataWebView4;
    private final WebView dataWebView5;
    private final WebView dataWebView6;
    private final WebView dataWebView7;
    private final Set<Schedule> scheduleSet = new HashSet<>();

    public TimeTableButtonListener(LinearLayout welcome, Button timetable, Button grade, Button qr, Button setting, FragmentManager fragmentManager,
                                   WebView dataWebView1, WebView dataWebView2, WebView dataWebView3, WebView dataWebView4, WebView dataWebView5, WebView dataWebView6, WebView dataWebView7) {
        this.welcome = welcome;
        this.timetable = timetable;
        this.grade = grade;
        this.qr = qr;
        this.setting = setting;
        this.fragmentManager = fragmentManager;
        this.dataWebView1 = dataWebView1;
        this.dataWebView2 = dataWebView2;
        this.dataWebView3 = dataWebView3;
        this.dataWebView4 = dataWebView4;
        this.dataWebView5 = dataWebView5;
        this.dataWebView6 = dataWebView6;
        this.dataWebView7 = dataWebView7;
    }


    @Override
    public void receive(String data, DataType type) {
        if (type != DataType.SCHEDULE) return;
        //Data parsing
        JsonObject json = JsonParser.parseString(data).getAsJsonObject();
        json.get("time_table_list")
                .getAsJsonArray()
                .forEach(
                        element -> {
                            JsonObject object = element.getAsJsonObject();
                            Schedule schedule = new Schedule();
                            schedule.setClassTitle(object.get("subject_nm_kor").getAsString()); // sets subject
                            schedule.setDay(dayToInt(object.get("class_day").getAsString()));
                            schedule.setClassPlace(object.get("loc_nm").getAsString()); // sets place
                            String start = object.get("start_time").getAsString();
                            String end = object.get("end_time").getAsString();
                            int h = Integer.parseInt(start.substring(0, 2));
                            int m = Integer.parseInt(start.substring(2));
                            schedule.setStartTime(new Time(h, m)); // sets the beginning of class time (hour,minute)
                            h = Integer.parseInt(end.substring(0, 2));
                            m = Integer.parseInt(end.substring(2));
                            schedule.setEndTime(new Time(h, m)); // sets the end of class time (hour,minute)

                            scheduleSet.add(schedule);
                        }
                );
    }

    @Override
    public void onClick(View view) {
        //7일 중에는 무조건 월~금이 포함됨
        dataWebView1.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL014.eps?lect_dt=20240310");
        dataWebView2.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL014.eps?lect_dt=20240311");
        dataWebView3.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL014.eps?lect_dt=20240312");
        dataWebView4.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL014.eps?lect_dt=20240313");
        dataWebView5.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL014.eps?lect_dt=20240314");
        dataWebView6.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL014.eps?lect_dt=20240315");
        dataWebView7.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL014.eps?lect_dt=20240316");

        welcome.setVisibility(View.GONE);
        timetable.setBackgroundColor(Color.parseColor("#40A7B5"));
        grade.setBackgroundColor(Color.parseColor("#004E96"));
        qr.setBackgroundColor(Color.parseColor("#004E96"));
        setting.setBackgroundColor(Color.parseColor("#004E96"));

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment, new fragment_timetable(this));
        transaction.commit();
    }

    public Set<Schedule> getScheduleSet() {
        return scheduleSet;
    }

    private int dayToInt(String day){
        switch(day){
            case "월":
                return 0;
            case "화":
                return 1;
            case "수":
                return 2;
            case "목":
                return 3;
            case "금":
                return 4;
            case "토":
                return 5;
            case "일":
                return 6;
        }
        return 7;
    }
}
