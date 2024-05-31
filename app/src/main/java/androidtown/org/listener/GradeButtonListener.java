package androidtown.org.listener;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidtown.org.R;
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

    public GradeButtonListener(LinearLayout welcome, Button timetable, Button grade, Button qr, Button setting, FragmentManager fragmentManager, WebView dataWebView) {
        this.welcome = welcome;
        this.timetable = timetable;
        this.grade = grade;
        this.qr = qr;
        this.setting = setting;
        this.fragmentManager = fragmentManager;
        this.dataWebView = dataWebView;
    }


    @Override
    public void receive(String data, DataType type) {
        if (type != DataType.GRADE) return;
        Log.d("test", "data type : " + type + " , data : " + data);
    }

    @Override
    public void onClick(View view) {
        dataWebView.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL008.eps?type=grade&selectedYear=2023&selectedSmtRcd=2학기");

        welcome.setVisibility(View.GONE);
        timetable.setBackgroundColor(Color.parseColor("#004E96"));
        grade.setBackgroundColor(Color.parseColor("#40A7B5"));
        qr.setBackgroundColor(Color.parseColor("#004E96"));
        setting.setBackgroundColor(Color.parseColor("#004E96"));

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment, new fragment_grade());
        transaction.commit();
    }
}
