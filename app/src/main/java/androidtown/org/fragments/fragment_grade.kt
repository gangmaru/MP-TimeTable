package androidtown.org.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidtown.org.R
import androidtown.org.listener.GradeButtonListener
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.DecimalFormat

class fragment_grade(private val listener: GradeButtonListener) : Fragment() {
    var pie: PieChart? = null
    private var entries: ArrayList<PieEntry>? = null
    private var set: PieDataSet? = null
    private var data: PieData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_grade, container, false)

        pie = view.findViewById(R.id.pie_chart)
        Handler().postDelayed({
            setData()
            setUpData()
        }, 500)

        return view
    }

    fun setData() {
        val graduate = listener.graduate
        entries = ArrayList()
        entries!!.add(PieEntry(graduate.majbas_credit.toFloat(), "전공필수"))
        entries!!.add(PieEntry(graduate.majsel_credit.toFloat(), "전공선택"))
        entries!!.add(PieEntry(graduate.culess_credit.toFloat(), "교양필수"))
        entries!!.add(PieEntry(graduate.comb_cul_tot_credit.toFloat(), "취득영역 소계"))
        entries!!.add(PieEntry(graduate.culbranch_credit.toFloat(), "계열 교양"))

        set = PieDataSet(entries, "")
        set!!.setColors(*ColorTemplate.JOYFUL_COLORS)
        set!!.selectionShift = 3f

        data = PieData(set)
        data!!.setValueTextSize(12f)
        data!!.setValueFormatter(object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return String.format("%d", value.toInt())
            }
        })
    }

    fun setUpData() {
        pie!!.data = data
        pie!!.setEntryLabelColor(Color.BLACK)
        pie!!.description.isEnabled = false
        //pie.setUsePercentValues(true);
        pie!!.animateY(2000, Easing.EaseInOutQuad)

        val legend = pie!!.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER

        pie!!.centerText = """
             취득학점
             ${DecimalFormat("###").format(sum.toLong())}
             """.trimIndent()
        pie!!.setCenterTextSize(30f)

        pie!!.invalidate()
    }

    private val sum: Int
        get() {
            var sum = 0
            for (i in entries!!.indices) {
                sum = (sum + entries!![i].value).toInt()
            }
            return sum
        }
}