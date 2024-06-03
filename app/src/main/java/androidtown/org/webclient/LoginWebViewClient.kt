package androidtown.org.webclient

import android.content.Context
import android.content.Intent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidtown.org.activities.MainActivity
import androidx.core.view.isVisible

class LoginWebViewClient(private val context: Context) : WebViewClient() {

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)

        if (url == "https://portal.gachon.ac.kr/p/S00/") {
            view.isVisible = false
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
