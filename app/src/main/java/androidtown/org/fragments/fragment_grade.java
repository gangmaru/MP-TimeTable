package androidtown.org.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;

import androidtown.org.R;
import androidtown.org.data.Graduate;
import androidtown.org.listener.GradeButtonListener;

public class fragment_grade extends Fragment {
    PieChart pie;
    private ArrayList<PieEntry> entries;
    private PieDataSet set;
    private PieData data;
    private final GradeButtonListener listener;

    public fragment_grade(GradeButtonListener gradeButtonListener) {
        this.listener = gradeButtonListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grade, container, false);

        pie = view.findViewById(R.id.pie_chart);
        new Handler().postDelayed(() -> {
            setData();
            setUpData();
        }, 300);

        return view;
    }

    public void setData() {
        Graduate graduate = listener.getGraduate();
        entries = new ArrayList<>();
        entries.add(new PieEntry(graduate.getMajbas_credit(), "전공필수"));
        entries.add(new PieEntry(graduate.getMajsel_credit(), "전공선택"));
        entries.add(new PieEntry(graduate.getCuless_credit(), "교양필수"));
        entries.add(new PieEntry(graduate.getComb_cul_tot_credit(), "취득영역 소계"));
        entries.add(new PieEntry(graduate.getCulbranch_credit(), "계열 교양"));

        set = new PieDataSet(entries, "");
        set.setColors(ColorTemplate.JOYFUL_COLORS);
        set.setSelectionShift(3f);

        data = new PieData(set);
        data.setValueTextSize(12f);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%d", (int) value);
            }
        });
    }

    public void setUpData() {
        pie.setData(data);
        pie.setEntryLabelColor(Color.BLACK);
        pie.getDescription().setEnabled(false);
        //pie.setUsePercentValues(true);
        pie.animateY(2000, Easing.EaseInOutQuad);

        Legend legend = pie.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        pie.setCenterText("취득학점\n" + new DecimalFormat("###").format(getSum()));
        pie.setCenterTextSize(30f);

        pie.invalidate();
    }

    private int getSum() {
        int sum = 0;
        for (int i = 0; i < entries.size(); i++) {
            sum += entries.get(i).getValue();
        }
        return sum;
    }
}