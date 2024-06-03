package androidtown.org.listener

import android.graphics.Color
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.LinearLayout
import androidtown.org.R
import androidtown.org.data.Grade
import androidtown.org.data.Graduate
import androidtown.org.data.type.DataType
import androidtown.org.fragments.FragmentGrade
import androidx.fragment.app.FragmentManager
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import java.util.function.Consumer

class GradeButtonListener(private val welcome: LinearLayout, private val timetable: Button, private val grade: Button, private val qr: Button, private val setting: Button, private val fragmentManager: FragmentManager, private val dataWebView: WebView, private val subWebView: WebView) : View.OnClickListener, WebDataListener {
    private val gradeMap: MutableMap<String, Grade> = HashMap()
    val graduate: Graduate = Graduate()


    override fun receive(data: String?, type: DataType?) {
        when (type) {
            DataType.GRADE -> {
                //Data parsing
                val json = JsonParser.parseString(data).asJsonObject
                json["grade_list"]
                        .asJsonArray
                        .forEach(
                                Consumer { element: JsonElement ->
                                    val `object` = element.asJsonObject
                                    val grade = Grade(
                                            `object`["subject_nm"].asString,
                                            `object`["get_rank"].asString,
                                            `object`["nvl(a.score,0)"].asString,
                                            `object`["year"].asString,
                                            `object`["hakgi"].asString,
                                            `object`["isunm"].asString,
                                            `object`["credit"].asString
                                    )
                                    gradeMap[`object`["a.subject_cd||a.class_num"].asString] = grade
                                }
                        )
            }

            DataType.GRADUATE -> {
                val json = JsonParser.parseString(data).asJsonObject
                val `object` = json["graduatedIsuList"].asJsonArray[2].asJsonObject
                graduate.jol_credit = `object`["jol_credit"].asInt
                graduate.majbas_credit = `object`["majbas_credit"].asInt
                graduate.majsel_credit = `object`["majsel_credit"].asInt
                graduate.culess_credit = `object`["culess_credit"].asInt
                graduate.comb_cul_tot_credit = `object`["comb_cul_tot_credit"].asInt
                graduate.cul1_credit = `object`["cul1_credit"].asInt
                graduate.cul2_credit = `object`["cul2_credit"].asInt
                graduate.cul3_credit = `object`["cul3_credit"].asInt
                graduate.cul4_credit = `object`["cul4_credit"].asInt
                graduate.cul5_credit = `object`["cul5_credit"].asInt
                graduate.cul6_credit = `object`["cul6_credit"].asInt
                graduate.culbranch_credit = `object`["culbranch_credit"].asInt
            }

            else -> {}
        }
    }

    override fun onClick(view: View) {
        dataWebView.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL008.eps?type=grade&selectedYear=2023&selectedSmtRcd=2학기")
        subWebView.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL031.eps")

        welcome.visibility = View.GONE
        timetable.setBackgroundColor(Color.parseColor("#004E96"))
        grade.setBackgroundColor(Color.parseColor("#40A7B5"))
        qr.setBackgroundColor(Color.parseColor("#004E96"))
        setting.setBackgroundColor(Color.parseColor("#004E96"))

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, FragmentGrade(this))
        transaction.commit()
    }
}
