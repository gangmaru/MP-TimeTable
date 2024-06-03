package androidtown.org;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_timetable#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_timetable extends Fragment implements View.OnClickListener {

    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_EDIT = 2;

    private Context context;

    private Button addBtn;
    private Button clearBtn;
    private Button saveBtn;
    private Button loadBtn;
    private TimetableView timetable;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_timetable() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_timetable.
     */
    public static fragment_timetable newInstance(String param1, String param2) {
        fragment_timetable fragment = new fragment_timetable();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        this.context = getActivity();

        addBtn = view.findViewById(R.id.add_btn);
        clearBtn = view.findViewById(R.id.clear_btn);
        saveBtn = view.findViewById(R.id.save_btn);
        loadBtn = view.findViewById(R.id.load_btn);
        timetable = view.findViewById(R.id.timetable);

        addBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        loadBtn.setOnClickListener(this);

        initView();
    }

    private void initView() {
        timetable.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
            @Override
            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
                Intent i = new Intent(context, EditActivity.class);
                i.putExtra("mode", REQUEST_EDIT);
                i.putExtra("idx", idx);
                i.putExtra("schedules", schedules);
                startActivityForResult(i, REQUEST_EDIT);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_btn) {
            Intent i = new Intent(context, EditActivity.class);
            i.putExtra("mode", REQUEST_ADD);
            startActivityForResult(i, REQUEST_ADD);
        } else if (v.getId() == R.id.clear_btn) {
            timetable.removeAll();
        } else if (v.getId() == R.id.save_btn) {
            saveByPreference(timetable.createSaveData());
        } else if (v.getId() == R.id.load_btn) {
            loadSavedData();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ADD:
                if (resultCode == EditActivity.RESULT_OK_ADD) {
                    ArrayList<Schedule> item = (ArrayList<Schedule>) data.getSerializableExtra("schedules");
                    timetable.add(item);
                }
                break;
            case REQUEST_EDIT:
                if (resultCode == EditActivity.RESULT_OK_EDIT) {
                    int idx = data.getIntExtra("idx", -1);
                    ArrayList<Schedule> item = (ArrayList<Schedule>) data.getSerializableExtra("schedules");
                    timetable.edit(idx, item);
                } else if (resultCode == EditActivity.RESULT_OK_DELETE) {
                    int idx = data.getIntExtra("idx", -1);
                    timetable.remove(idx);
                }
                break;
        }
    }

    private void saveByPreference(String data) {
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString("timetable_demo", data);
        editor.commit();
        Toast.makeText(context, "saved!", Toast.LENGTH_SHORT).show();
    }

    private void loadSavedData() {
        timetable.removeAll();
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(context);
        String savedData = mPref.getString("timetable_demo", "");
        if (savedData == null || savedData.equals("")) return;
        timetable.load(savedData);
        Toast.makeText(context, "loaded!", Toast.LENGTH_SHORT).show();
    }
    private void checkContinueCourse(){
        ArrayList<Schedule> tempSchedule = timetable.getAllSchedulesInStickers();
        sortSchedules(tempSchedule);
        boolean checkValid = false;
        String startPlace, endPlace, sDay, result;
        result = "";
        int day;
        for (int i = 0; i < tempSchedule.size(); i++){
            for (int j = i; j < tempSchedule.size(); j++){
                day = tempSchedule.get(i).getDay();
                switch (day){
                    case 0:
                        sDay = "월요일";
                        break;
                    case 1:
                        sDay = "화요일";
                        break;
                    case 2:
                        sDay = "수요일";
                        break;
                    case 3:
                        sDay = "목요일";
                        break;
                    case 4:
                        sDay = "금요일";
                        break;
                    default:
                        sDay = "";
                        break;

                }
                if (day == tempSchedule.get(j).getDay()){
                    // 먼저 듣는 강의를 먼저 등록한 경우
                    // i: 먼저 듣는 강의, j: i 다음 들을 강의
                    if (checkTime(tempSchedule.get(i).getEndTime(), tempSchedule.get(j).getStartTime())){
                        // 강의 장소에서 "-"을 기준으로 건물 이름만 따오기
                        try{
                            startPlace = tempSchedule.get(i).getClassPlace().substring(0, tempSchedule.get(i).getClassPlace().indexOf("-"));
                        }
                        //"화상강의강의실"의 경우 "-"가 없기에 예외 처리
                        catch (Exception e){
                            startPlace = tempSchedule.get(i).getClassPlace();
                        }

                        // 강의 장소에서 "-"을 기준으로 건물 이름만 따오기
                        try{
                            endPlace = tempSchedule.get(j).getClassPlace().substring(0, tempSchedule.get(j).getClassPlace().indexOf("-"));
                        }
                        //"화상강의강의실"의 경우 "-"가 없기에 예외 처리
                        catch (Exception e){
                            endPlace = tempSchedule.get(j).getClassPlace();
                        }
                        if (!checkPlaceAvailable(startPlace, endPlace)){
                            result = result + sDay + "에 듣는 " + tempSchedule.get(i).getClassTitle() + "과(와) "+ tempSchedule.get(j).getClassTitle()
                                    + "은(는) 연강이 불가능합니다.\n";
                        }
                    }
                }
            }
        }
        if (result.isEmpty())
            Toast.makeText(context, "연강하는데에 지장이 가는 강의가 없습니다.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
    private boolean checkTime(Time a, Time b){
        Integer x1 = a.getHour();
        Integer x2 = a.getMinute();
        int y1 = b.getHour();
        int y2 = b.getMinute();
        if (x1.equals(y1 - 1)){
            if (x2.equals(y2 + 50))
                return true;
        }
        return false;
    }
    private boolean checkPlaceAvailable(String start, String end){
        int istart, iend;
        // placeTable[출발 장소][도착 장소] 연강 가능 여부 (카카오맵 기준 도보 8분 이상일 경우 false 처리했습니다.)
        boolean[][] placeTable = {{true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
                {true, true, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {true, false, true, false, true, true, true, true, true, true, true, true, true, true, true},
                {true, false, false, true, true, true, false, true, true, true, true, true, true, true, true},
                {true, false, true, true, true, true, false, true, false, true, false, true, true, true, false},
                {true, false, true, true, true, true, true, true, false, true, false, true, true, true, true},
                {true, false, true, false, false, true, true, true, true, true, true, true, false, false, true},
                {true, false, true, true, true, true, true, true, true, true, true, true, true, true, true},
                {true, false, true, true, false, false, true, true, true, true, true, false, false, false, true},
                {true, false, true, true, true, true, true, true, true, true, true, true, true, true, true},
                {true, false, true, true, false, false, true, true, true, true, true, false, false, false, true},
                {true, false, true, true, true, true, true, true, false, true, false, true, true, true, false},
                {true, false, true, true, true, true, false, true, false, true, false, true, true, true, false},
                {true, false, true, true, true, true, false, true, false, true, false, true, true, true, false},
                {true, false, true, true, false, true, true, true, true, true, true, false, false, false, true}};
        switch (start) {
            case "화상강의강의실(가상)":
                istart = 0;
                break;
            case "AI관":
                istart = 1;
                break;
            case "반도체대학":
                istart = 2;
                break;
            case "교육대학원":
                istart = 3;
                break;
            case "대학원":
                istart = 4;
                break;
            case "글로벌센터":
                istart = 5;
                break;
            case "비전타워":
                istart = 6;
                break;
            case "가천관":
                istart = 7;
                break;
            case "산학협력관":
                istart = 8;
                break;
            case "공과대학1":
                istart = 9;
                break;
            case "공과대학2":
                istart = 10;
                break;
            case "예술대학1":
                istart = 11;
                break;
            case "예술대학2":
                istart = 12;
                break;
            case "바이오나노대학":
                istart = 13;
                break;
            case "한의과대학":
                istart = 14;
                break;
            default:
                return true;
        }
        switch (end) {
            case "화상강의강의실(가상)":
                iend = 0;
                break;
            case "AI관":
                iend = 1;
                break;
            case "반도체대학":
                iend = 2;
                break;
            case "교육대학원":
                iend = 3;
                break;
            case "대학원":
                iend = 4;
                break;
            case "글로벌센터":
                iend = 5;
                break;
            case "비전타워":
                iend = 6;
                break;
            case "가천관":
                iend = 7;
                break;
            case "산학협력관":
                iend = 8;
                break;
            case "공과대학1":
                iend = 9;
                break;
            case "공과대학2":
                iend = 10;
                break;
            case "예술대학1":
                iend = 11;
                break;
            case "예술대학2":
                iend = 12;
                break;
            case "바이오나노대학":
                iend = 13;
                break;
            case "한의과대학":
                iend = 14;
                break;
            default:
                return true;
        }
        return placeTable[istart][iend];
    }
    private void sortSchedules(ArrayList<Schedule> schedules) {
        Collections.sort(schedules, new Comparator<Schedule>() {
            @Override
            public int compare(Schedule s1, Schedule s2) {
                int dayComparison = Integer.compare(s1.getDay(), s2.getDay());
                if (dayComparison != 0) {
                    return dayComparison;
                }

                Time startTime1 = s1.getStartTime();
                Time startTime2 = s2.getStartTime();

                int hourComparison = Integer.compare(startTime1.getHour(), startTime2.getHour());
                if (hourComparison != 0) {
                    return hourComparison;
                }

                return Integer.compare(startTime1.getMinute(), startTime2.getMinute());
            }
        });
    }
}
