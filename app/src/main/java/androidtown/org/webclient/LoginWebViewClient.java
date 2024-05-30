package androidtown.org.webclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidtown.org.activities.MainActivity;

public class LoginWebViewClient extends WebViewClient {

    private final Context context;

    public LoginWebViewClient(Context context) {
        this.context = context;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        if (url.equals("https://portal.gachon.ac.kr/p/S00/")) {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);

        if (url.equals("https://portal.gachon.ac.kr/p/S00/"))
            view.setVisibility(View.INVISIBLE);
    }
}
