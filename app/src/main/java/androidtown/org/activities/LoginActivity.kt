package androidtown.org.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidtown.org.R;
import androidtown.org.webclient.LoginWebViewClient;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        WebView loginWebView = findViewById(R.id.loginWebView);
        WebSettings settings = loginWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        loginWebView.setWebViewClient(new LoginWebViewClient(this));
        loginWebView.setWebChromeClient(new WebChromeClient());

        //학사 행정 포털 로그인 -> 로그인 과정 위탁
        loginWebView.loadUrl("https://sso.gachon.ac.kr/svc/tk/Auth.do?ac=Y&ifa=N&id=portal&");
    }
}