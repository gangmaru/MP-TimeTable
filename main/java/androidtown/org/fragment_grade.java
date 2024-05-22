package androidtown.org;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_grade#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_grade extends Fragment {
    PieChart pie;
    private ArrayList<PieEntry>entries;
    private PieDataSet set;
    private PieData data;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_grade() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_grade.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_grade newInstance(String param1, String param2) {
        fragment_grade fragment = new fragment_grade();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grade, container, false);

        pie=view.findViewById(R.id.pie_chart);
        setData();
        setUpData();


        return view;
    }
    public void setData(){
        entries=new ArrayList<>();
        entries.add(new PieEntry(34,"전공필수"));
        entries.add(new PieEntry(19,"전공선택"));
        entries.add(new PieEntry(18,"교양선택"));
        entries.add(new PieEntry(13,"교양필수"));
        entries.add(new PieEntry(3,"교양계열"));

        set=new PieDataSet(entries,"");
        set.setColors(ColorTemplate.JOYFUL_COLORS);
        set.setSelectionShift(3f);
        //set.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        //set.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        //set.setUsingSliceColorAsValueLineColor(true);

        data=new PieData(set);
        data.setValueTextSize(12f);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%d", (int) value);
            }
        });
    }
    public void setUpData(){
        pie.setData(data);
        pie.setEntryLabelColor(Color.BLACK);
        pie.getDescription().setEnabled(false);
        pie.setUsePercentValues(true);
        pie.animateY(2000, Easing.EaseInOutQuad);

        Legend legend=pie.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        pie.setCenterText("취득학점\n"+new DecimalFormat("###").format(getSum()));
        pie.setCenterTextSize(30f);

        pie.invalidate();
    }
    public int getSum(){
        int sum=0;
        for(int i=0;i<entries.size();i++){
            sum+=entries.get(i).getValue();
        }
        return sum;
    }
}