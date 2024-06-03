package androidtown.org.listener

import android.graphics.Color
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.LinearLayout
import androidtown.org.R
import androidtown.org.data.type.DataType
import androidtown.org.fragments.fragment_timetable
import androidx.fragment.app.FragmentManager
import com.github.tlaabs.timetableview.Schedule
import com.github.tlaabs.timetableview.Time
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import java.util.function.Consumer

class TimeTableButtonListener(private val welcome: LinearLayout, private val timetable: Button, private val grade: Button, private val qr: Button, private val setting: Button, private val fragmentManager: FragmentManager,
                              private val dataWebView1: WebView, private val dataWebView2: WebView, private val dataWebView3: WebView, private val dataWebView4: WebView, private val dataWebView5: WebView, private val dataWebView6: WebView, private val dataWebView7: WebView) : View.OnClickListener, WebDataListener {
    private val scheduleSet: MutableSet<Schedule> = HashSet()


    override fun receive(data: String?, type: DataType?) {
        if (type != DataType.SCHEDULE) return
        //Data parsing
        val json = JsonParser.parseString(data).asJsonObject
        json["time_table_list"]
                .asJsonArray
                .forEach(
                        Consumer { element: JsonElement ->
                            val `object` = element.asJsonObject
                            val schedule = Schedule()
                            schedule.classTitle = `object`["subject_nm_kor"].asString // sets subject
                            schedule.day = dayToInt(`object`["class_day"].asString)
                            schedule.classPlace = `object`["loc_nm"].asString // sets place
                            val start = `object`["start_time"].asString
                            val end = `object`["end_time"].asString
                            var h = start.substring(0, 2).toInt()
                            var m = start.substring(2).toInt()
                            schedule.startTime = Time(h, m) // sets the beginning of class time (hour,minute)
                            h = end.substring(0, 2).toInt()
                            m = end.substring(2).toInt()
                            schedule.endTime = Time(h, m) // sets the end of class time (hour,minute)
                            scheduleSet.add(schedule)
                        }
                )
    }

    override fun onClick(view: View) {
        //7일 중에는 무조건 월~금이 포함됨
        dataWebView1.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL014.eps?lect_dt=20240310")
        dataWebView2.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL014.eps?lect_dt=20240311")
        dataWebView3.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL014.eps?lect_dt=20240312")
        dataWebView4.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL014.eps?lect_dt=20240313")
        dataWebView5.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL014.eps?lect_dt=20240314")
        dataWebView6.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL014.eps?lect_dt=20240315")
        dataWebView7.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL014.eps?lect_dt=20240316")

        welcome.visibility = View.GONE
        timetable.setBackgroundColor(Color.parseColor("#40A7B5"))
        grade.setBackgroundColor(Color.parseColor("#004E96"))
        qr.setBackgroundColor(Color.parseColor("#004E96"))
        setting.setBackgroundColor(Color.parseColor("#004E96"))

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, fragment_timetable(this))
        transaction.commit()
    }

    fun getScheduleSet(): Set<Schedule> {
        return scheduleSet
    }

    private fun dayToInt(day: String): Int {
        when (day) {
            "월" -> return 0
            "화" -> return 1
            "수" -> return 2
            "목" -> return 3
            "금" -> return 4
            "토" -> return 5
            "일" -> return 6
        }
        return 7
    }
}
