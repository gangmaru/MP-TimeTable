package androidtown.org.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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

        final WebView dataWebView1 = findViewById(R.id.dataWebView1);
        final WebView dataWebView2 = findViewById(R.id.dataWebView2);
        final WebView dataWebView3 = findViewById(R.id.dataWebView3);
        final WebView dataWebView4 = findViewById(R.id.dataWebView4);
        final WebView dataWebView5 = findViewById(R.id.dataWebView5);
        final WebView dataWebView6 = findViewById(R.id.dataWebView6);
        final WebView dataWebView7 = findViewById(R.id.dataWebView7);

        //Button listeners initialize
        final TimeTableButtonListener timeTableButtonListener
                = new TimeTableButtonListener(welcome, timetable, grade, qr, setting, getSupportFragmentManager(),
                dataWebView1, dataWebView2, dataWebView3, dataWebView4, dataWebView5, dataWebView6, dataWebView7);
        final GradeButtonListener gradeButtonListener
                = new GradeButtonListener(welcome, timetable, grade, qr, setting, getSupportFragmentManager(), dataWebView1, dataWebView2);
        final QRButtonListener qrButtonListener
                = new QRButtonListener(welcome, timetable, grade, qr, setting, getSupportFragmentManager(), dataWebView1, dataWebView2);
        final SettingButtonListener settingButtonListener
                = new SettingButtonListener(welcome, timetable, grade, qr, setting, getSupportFragmentManager(), dataWebView1);

        //Init fragment
        welcome.setVisibility(View.GONE);
        timetable.setBackgroundColor(Color.parseColor("#40A7B5"));
        grade.setBackgroundColor(Color.parseColor("#004E96"));
        qr.setBackgroundColor(Color.parseColor("#004E96"));
        setting.setBackgroundColor(Color.parseColor("#004E96"));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment, new fragment_timetable(timeTableButtonListener));
        transaction.commit();

        //DataWebView initialize
        webViewInit(
                Arrays.asList(dataWebView1, dataWebView2, dataWebView3, dataWebView4, dataWebView5, dataWebView6, dataWebView7),
                Arrays.asList(timeTableButtonListener, gradeButtonListener, qrButtonListener));

        //Button listeners add
        timetable.setOnClickListener(timeTableButtonListener);
        grade.setOnClickListener(gradeButtonListener);
        qr.setOnClickListener(qrButtonListener);
        setting.setOnClickListener(settingButtonListener);
    }

    private void webViewInit(List<WebView> webViewList, List<WebDataListener> listenerList) {
        webViewList.forEach(view -> {
            WebSettings settings = view.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setSupportMultipleWindows(true);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);

            DataWebViewClient webViewClient = new DataWebViewClient(listenerList);
            view.setWebViewClient(webViewClient);
            view.setWebChromeClient(new WebChromeClient());

            view.addJavascriptInterface(webViewClient, "Android");
        });
    }

}