package androidtown.org.listener

import android.graphics.Color
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.LinearLayout
import androidtown.org.R
import androidtown.org.data.Student
import androidtown.org.data.type.DataType
import androidtown.org.fragments.FragmentQR
import androidx.fragment.app.FragmentManager
import com.google.gson.JsonParser

class QRButtonListener(private val welcome: LinearLayout, private val timetable: Button, private val grade: Button, private val qr: Button, private val setting: Button, private val fragmentManager: FragmentManager, private val dataWebView1: WebView, private val dataWebView2: WebView) : View.OnClickListener, WebDataListener {
    val student: Student = Student()


    override fun receive(data: String?, type: DataType?) {
        when (type) {
            DataType.DEPARTMENT -> {
                val json = JsonParser.parseString(data).asJsonObject
                val `object` = json["eduYear_list"]
                        .asJsonArray[0]
                        .asJsonObject
                val department = `object`["kwuhs.f_com001_nm('',a.cls_maj_cd)"].asString
                student.department = department
            }

            DataType.NAME -> {
                val json = JsonParser.parseString(data).asJsonObject
                val `object` = json["scholarship_list"]
                        .asJsonArray[0]
                        .asJsonObject
                val name = `object`["std_nm"].asString
                val studentNumber = `object`["std_no"].asString
                student.name = name
                student.studentNumber = studentNumber
            }

            else -> {}
        }
    }

    override fun onClick(view: View) {
        dataWebView1.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL024.eps?type=year_hakginm")
        //std_nm 이름
        //std_no 학번
        dataWebView2.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL011.eps?grade=1")

        //kwuhs.f_com001_nm('',a.cls_maj_cd) -> 학과
        welcome.visibility = View.GONE
        timetable.setBackgroundColor(Color.parseColor("#004E96"))
        grade.setBackgroundColor(Color.parseColor("#004E96"))
        qr.setBackgroundColor(Color.parseColor("#40A7B5"))
        setting.setBackgroundColor(Color.parseColor("#004E96"))

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, FragmentQR(this))
        transaction.commit()
    }
}
