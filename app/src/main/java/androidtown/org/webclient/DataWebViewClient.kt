package androidtown.org.webclient

import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidtown.org.data.type.DataType
import androidtown.org.listener.WebDataListener
import java.util.function.Consumer

/*
This class isn't thread-safe.
DO NOT use in async environment.
*/
class DataWebViewClient(private val listenerList: List<WebDataListener>) : WebViewClient() {
    private val types = DataType.entries.toTypedArray()
    private var loadedType: DataType? = null

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)

        loadedType = null
        for (type in types) {
            if (url.contains(type.urlNum)) {
                loadedType = type
                break
            }
        }
        if (loadedType != null) view.loadUrl("javascript:window.Android.getHTML(document.getElementsByTagName('body')[0].innerHTML);")
    }

    @JavascriptInterface
    fun getHTML(html: String?) {
        listenerList.forEach(Consumer { listener: WebDataListener -> listener.receive(html, loadedType) })
    }
}
