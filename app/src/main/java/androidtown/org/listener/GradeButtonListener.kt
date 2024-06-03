package androidtown.org.listener;

import android.graphics.Color;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

import androidtown.org.R;
import androidtown.org.data.Grade;
import androidtown.org.data.Graduate;
import androidtown.org.data.type.DataType;
import androidtown.org.fragments.fragment_grade;

public class GradeButtonListener implements View.OnClickListener, WebDataListener {

    private final LinearLayout welcome;
    private final Button timetable;
    private final Button grade;
    private final Button qr;
    private final Button setting;
    private final FragmentManager fragmentManager;
    private final WebView dataWebView;
    private final WebView subWebView;

    private final Map<String, Grade> gradeMap = new HashMap<>();
    private final Graduate graduate = new Graduate();

    public GradeButtonListener(LinearLayout welcome, Button timetable, Button grade, Button qr, Button setting, FragmentManager fragmentManager, WebView dataWebView, WebView subWebView) {
        this.welcome = welcome;
        this.timetable = timetable;
        this.grade = grade;
        this.qr = qr;
        this.setting = setting;
        this.fragmentManager = fragmentManager;
        this.dataWebView = dataWebView;
        this.subWebView = subWebView;
    }


    @Override
    public void receive(String data, DataType type) {
        switch (type) {
            case GRADE: {
                //Data parsing
                JsonObject json = JsonParser.parseString(data).getAsJsonObject();
                json.get("grade_list")
                        .getAsJsonArray()
                        .forEach(
                                element -> {
                                    JsonObject object = element.getAsJsonObject();
                                    Grade grade = new Grade(
                                            object.get("subject_nm").getAsString(),
                                            object.get("get_rank").getAsString(),
                                            object.get("nvl(a.score,0)").getAsString(),
                                            object.get("year").getAsString(),
                                            object.get("hakgi").getAsString(),
                                            object.get("isunm").getAsString(),
                                            object.get("credit").getAsString()
                                    );
                                    gradeMap.put(
                                            object.get("a.subject_cd||a.class_num").getAsString(),
                                            grade);
                                }
                        );
                //UI update
                break;
            }
            case GRADUATE: {
                JsonObject json = JsonParser.parseString(data).getAsJsonObject();
                JsonObject object = json.get("graduatedIsuList").getAsJsonArray().get(2).getAsJsonObject();
                graduate.setJol_credit(object.get("jol_credit").getAsInt());
                graduate.setMajbas_credit(object.get("majbas_credit").getAsInt());
                graduate.setMajsel_credit(object.get("majsel_credit").getAsInt());
                graduate.setCuless_credit(object.get("culess_credit").getAsInt());
                graduate.setComb_cul_tot_credit(object.get("comb_cul_tot_credit").getAsInt());
                graduate.setCul1_credit(object.get("cul1_credit").getAsInt());
                graduate.setCul2_credit(object.get("cul2_credit").getAsInt());
                graduate.setCul3_credit(object.get("cul3_credit").getAsInt());
                graduate.setCul4_credit(object.get("cul4_credit").getAsInt());
                graduate.setCul5_credit(object.get("cul5_credit").getAsInt());
                graduate.setCul6_credit(object.get("cul6_credit").getAsInt());
                graduate.setCulbranch_credit(object.get("culbranch_credit").getAsInt());
                break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        dataWebView.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL008.eps?type=grade&selectedYear=2023&selectedSmtRcd=2학기");
        subWebView.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL031.eps");

        welcome.setVisibility(View.GONE);
        timetable.setBackgroundColor(Color.parseColor("#004E96"));
        grade.setBackgroundColor(Color.parseColor("#40A7B5"));
        qr.setBackgroundColor(Color.parseColor("#004E96"));
        setting.setBackgroundColor(Color.parseColor("#004E96"));

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment, new fragment_grade(this));
        transaction.commit();
    }

    public Graduate getGraduate() {
        return graduate;
    }

    public Map<String, Grade> getGradeMap() {
        return gradeMap;
    }
}
