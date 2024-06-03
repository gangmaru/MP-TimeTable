package androidtown.org.webclient;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;

import androidtown.org.data.type.DataType;
import androidtown.org.listener.WebDataListener;

/*
This class isn't thread-safe.
DO NOT use in async environment.
*/
public class DataWebViewClient extends WebViewClient {

    private final DataType[] types = DataType.values();
    private final List<WebDataListener> listenerList;
    private DataType loadedType = null;

    public DataWebViewClient(List<WebDataListener> listenerList) {
        this.listenerList = listenerList;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        loadedType = null;
        for (DataType type : types) {
            if (url.contains(type.getUrlNum())) {
                loadedType = type;
                break;
            }
        }
        if (loadedType != null)
            view.loadUrl("javascript:window.Android.getHTML(document.getElementsByTagName('body')[0].innerHTML);");
    }

    @JavascriptInterface
    public void getHTML(String html) {
        listenerList.forEach(listener -> listener.receive(html, loadedType));
    }

}
