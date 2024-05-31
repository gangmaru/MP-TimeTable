package androidtown.org.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Arrays;
import java.util.List;

import androidtown.org.R;
import androidtown.org.fragments.fragment_timetable;
import androidtown.org.listener.GradeButtonListener;
import androidtown.org.listener.QRButtonListener;
import androidtown.org.listener.SettingButtonListener;
import androidtown.org.listener.TimeTableButtonListener;
import androidtown.org.listener.WebDataListener;
import androidtown.org.webclient.DataWebViewClient;
import androidtown.org.webclient.LoginWebViewClient;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Components initialize
        final Button timetable = findViewById(R.id.timetable_btn);
        final Button grade = findViewById(R.id.grade_btn);
        final Button qr = findViewById(R.id.qr_btn);
        final Button setting = findViewById(R.id.setting_btn);
        final LinearLayout welcome = findViewById(R.id.welcome);

        //DataWebView initialize
        final WebView dataWebView = findViewById(R.id.dataWebView);

        WebSettings settings = dataWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        dataWebView.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL035.eps?type=grade&selectedYear=2024&selectedTermCd=10");

        //Init fragment
        welcome.setVisibility(View.GONE);
        timetable.setBackgroundColor(Color.parseColor("#40A7B5"));
        grade.setBackgroundColor(Color.parseColor("#004E96"));
        qr.setBackgroundColor(Color.parseColor("#004E96"));
        setting.setBackgroundColor(Color.parseColor("#004E96"));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment, new fragment_timetable());
        transaction.commit();

        //Button listeners initialize
        final TimeTableButtonListener timeTableButtonListener
                = new TimeTableButtonListener(welcome, timetable, grade, qr, setting, getSupportFragmentManager(), dataWebView);
        final GradeButtonListener gradeButtonListener
                = new GradeButtonListener(welcome, timetable, grade, qr, setting, getSupportFragmentManager(), dataWebView);
        final QRButtonListener qrButtonListener
                = new QRButtonListener(welcome, timetable, grade, qr, setting, getSupportFragmentManager(), dataWebView);
        final SettingButtonListener settingButtonListener
                = new SettingButtonListener(welcome, timetable, grade, qr, setting, getSupportFragmentManager(), dataWebView);

        //Button listeners add
        timetable.setOnClickListener(timeTableButtonListener);
        grade.setOnClickListener(gradeButtonListener);
        qr.setOnClickListener(qrButtonListener);
        setting.setOnClickListener(settingButtonListener);


        final List<WebDataListener> listenerList = Arrays.asList(
                timeTableButtonListener, gradeButtonListener//, qrButtonListener, settingButtonListener
        );

        DataWebViewClient webViewClient = new DataWebViewClient(listenerList);
        dataWebView.setWebViewClient(webViewClient);
        dataWebView.setWebChromeClient(new WebChromeClient());

        dataWebView.addJavascriptInterface(webViewClient, "Android");
    }

}