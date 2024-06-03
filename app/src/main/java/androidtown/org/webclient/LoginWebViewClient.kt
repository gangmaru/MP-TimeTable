package androidtown.org.webclient

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidtown.org.activities.MainActivity

class LoginWebViewClient(private val context: Context) : WebViewClient() {
    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)

        if (url == "https://portal.gachon.ac.kr/p/S00/") {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
        super.onPageStarted(view, url, favicon)

        if (url == "https://portal.gachon.ac.kr/p/S00/") view.visibility = View.INVISIBLE
    }
}
