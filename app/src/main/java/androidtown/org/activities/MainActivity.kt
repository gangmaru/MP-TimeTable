package androidtown.org.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Button
import android.widget.LinearLayout
import androidtown.org.R
import androidtown.org.fragments.fragment_timetable
import androidtown.org.listener.GradeButtonListener
import androidtown.org.listener.QRButtonListener
import androidtown.org.listener.SettingButtonListener
import androidtown.org.listener.TimeTableButtonListener
import androidtown.org.listener.WebDataListener
import androidtown.org.webclient.DataWebViewClient
import androidx.appcompat.app.AppCompatActivity
import java.util.Arrays
import java.util.function.Consumer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Components initialize
        val timetable = findViewById<Button>(R.id.timetable_btn)
        val grade = findViewById<Button>(R.id.grade_btn)
        val qr = findViewById<Button>(R.id.qr_btn)
        val setting = findViewById<Button>(R.id.setting_btn)
        val welcome = findViewById<LinearLayout>(R.id.welcome)

        val dataWebView1 = findViewById<WebView>(R.id.dataWebView1)
        val dataWebView2 = findViewById<WebView>(R.id.dataWebView2)
        val dataWebView3 = findViewById<WebView>(R.id.dataWebView3)
        val dataWebView4 = findViewById<WebView>(R.id.dataWebView4)
        val dataWebView5 = findViewById<WebView>(R.id.dataWebView5)
        val dataWebView6 = findViewById<WebView>(R.id.dataWebView6)
        val dataWebView7 = findViewById<WebView>(R.id.dataWebView7)

        //Button listeners initialize
        val timeTableButtonListener = TimeTableButtonListener(welcome, timetable, grade, qr, setting, supportFragmentManager,
                dataWebView1, dataWebView2, dataWebView3, dataWebView4, dataWebView5, dataWebView6, dataWebView7)
        val gradeButtonListener = GradeButtonListener(welcome, timetable, grade, qr, setting, supportFragmentManager, dataWebView1, dataWebView2)
        val qrButtonListener = QRButtonListener(welcome, timetable, grade, qr, setting, supportFragmentManager, dataWebView1, dataWebView2)
        val settingButtonListener = SettingButtonListener(welcome, timetable, grade, qr, setting, supportFragmentManager, dataWebView1)

        //Init fragment
        welcome.visibility = View.GONE
        timetable.setBackgroundColor(Color.parseColor("#40A7B5"))
        grade.setBackgroundColor(Color.parseColor("#004E96"))
        qr.setBackgroundColor(Color.parseColor("#004E96"))
        setting.setBackgroundColor(Color.parseColor("#004E96"))
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, fragment_timetable(timeTableButtonListener))
        transaction.commit()

        //DataWebView initialize
        webViewInit(
                Arrays.asList(dataWebView1, dataWebView2, dataWebView3, dataWebView4, dataWebView5, dataWebView6, dataWebView7),
                Arrays.asList<WebDataListener>(timeTableButtonListener, gradeButtonListener, qrButtonListener))

        //Button listeners add
        timetable.setOnClickListener(timeTableButtonListener)
        grade.setOnClickListener(gradeButtonListener)
        qr.setOnClickListener(qrButtonListener)
        setting.setOnClickListener(settingButtonListener)
    }

    private fun webViewInit(webViewList: List<WebView>, listenerList: List<WebDataListener>) {
        webViewList.forEach(Consumer { view: WebView ->
            val settings = view.settings
            settings.javaScriptEnabled = true
            settings.setSupportMultipleWindows(true)
            settings.javaScriptCanOpenWindowsAutomatically = true

            val webViewClient = DataWebViewClient(listenerList)
            view.webViewClient = webViewClient
            view.webChromeClient = WebChromeClient()
            view.addJavascriptInterface(webViewClient, "Android")
        })
    }
}