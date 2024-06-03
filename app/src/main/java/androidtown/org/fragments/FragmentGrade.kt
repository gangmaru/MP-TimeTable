package androidtown.org.fragments

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidtown.org.R
import androidtown.org.listener.GradeButtonListener
import androidx.core.view.allViews
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

class FragmentGrade(private val listener: GradeButtonListener) : Fragment() {
    private lateinit var pie: PieChart
    private lateinit var entries: ArrayList<PieEntry>
    private lateinit var set: PieDataSet
    private lateinit var data: PieData
    private lateinit var gradeTable: TableLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_grade, container, false)

        pie = view.findViewById(R.id.pie_chart)
        gradeTable = view.findViewById(R.id.gradeTable)
        Handler().postDelayed({
            setData()
            setUpData()
            updateGradeTable()
        }, 500)

        return view
    }

    private fun updateGradeTable() {
        listener.gradeMap.entries.forEachIndexed { index, (k, v) ->
            val row = TableRow(context).apply {
                id = k.toInt() + index
                background = Drawable.createFromPath("@drawable/border")
            }

            val subject = TextView(context).apply {
                text = v.subjectName
                width = 200
                gravity = Gravity.CENTER
                textSize = 17f
            }

            val isuName = TextView(context).apply {
                text = v.isuName
                width = 85
                gravity = Gravity.CENTER
                textSize = 17f
            }

            val credit = TextView(context).apply {
                text = v.credit
                width = 85
                gravity = Gravity.CENTER
                textSize = 17f
            }

            val grade = TextView(context).apply {
                text = v.rank
                width = 85
                gravity = Gravity.CENTER
                textSize = 17f
            }

            row.addView(subject)
            row.addView(isuName)
            row.addView(credit)
            row.addView(grade)

            gradeTable.addView(row)
        }
    }

    private fun setData() {
        val graduate = listener.graduate
        entries = ArrayList()
        entries.add(PieEntry(graduate.majbas_credit.toFloat(), "전공필수"))
        entries.add(PieEntry(graduate.majsel_credit.toFloat(), "전공선택"))
        entries.add(PieEntry(graduate.culess_credit.toFloat(), "교양필수"))
        entries.add(PieEntry(graduate.comb_cul_tot_credit.toFloat(), "취득영역 소계"))
        entries.add(PieEntry(graduate.culbranch_credit.toFloat(), "계열 교양"))

        set = PieDataSet(entries, "")
        set.setColors(*ColorTemplate.JOYFUL_COLORS)
        set.selectionShift = 3f

        data = PieData(set)
        data.setValueTextSize(12f)
        data.setValueFormatter(object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return String.format("%d", value.toInt())
            }
        })
    }

    private fun setUpData() {
        pie.data = data
        pie.setEntryLabelColor(Color.BLACK)
        pie.description.isEnabled = false
        //pie.setUsePercentValues(true);
        pie.animateY(2000, Easing.EaseInOutQuad)

        val legend = pie.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER

        pie.centerText = """
             취득학점
             ${DecimalFormat("###").format(sum.toLong())}
             """.trimIndent()
        pie.setCenterTextSize(30f)

        pie.invalidate()
    }

    private val sum: Int
        get() {
            var sum = 0
            for (i in entries.indices) {
                sum = (sum + entries[i].value).toInt()
            }
            return sum
        }
}