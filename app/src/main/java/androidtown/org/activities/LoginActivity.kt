package androidtown.org.activities

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidtown.org.R
import androidtown.org.webclient.LoginWebViewClient
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginWebView = findViewById<WebView>(R.id.loginWebView)
        val settings = loginWebView.settings
        settings.javaScriptEnabled = true
        settings.setSupportMultipleWindows(true)
        settings.javaScriptCanOpenWindowsAutomatically = true

        loginWebView.webViewClient = LoginWebViewClient(this)
        loginWebView.webChromeClient = WebChromeClient()

        //학사 행정 포털 로그인 -> 로그인 과정 위탁
        loginWebView.loadUrl("https://sso.gachon.ac.kr/svc/tk/Auth.do?ac=Y&ifa=N&id=portal&")
    }
}