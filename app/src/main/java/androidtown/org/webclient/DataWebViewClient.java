package androidtown.org.webclient;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;

import androidtown.org.data.type.DataType;
import androidtown.org.listener.WebDataListener;

public class DataWebViewClient extends WebViewClient {

    private final DataType[] types = DataType.values();
    private final List<WebDataListener> listenerList;

    public static final Object javaScriptInterface = new JavaScriptInterface();

    public DataWebViewClient(List<WebDataListener> listenerList) {
        this.listenerList = listenerList;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        view.loadUrl("");
    }

    static class JavaScriptInterface {

        @JavascriptInterface
        public void getHTML(String html) {

        }
    }

}
