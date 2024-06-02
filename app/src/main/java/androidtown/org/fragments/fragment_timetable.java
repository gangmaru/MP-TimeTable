package androidtown.org.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.TimetableView;

import java.util.ArrayList;

import androidtown.org.R;
import androidtown.org.activities.EditActivity;
import androidtown.org.listener.TimeTableButtonListener;

public class fragment_timetable extends Fragment implements View.OnClickListener {

    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_EDIT = 2;

    private Context context;

    private TimetableView timetable;
    private final TimeTableButtonListener listener;

    public fragment_timetable(TimeTableButtonListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        Button addBtn = view.findViewById(R.id.add_btn);
        Button clearBtn = view.findViewById(R.id.clear_btn);
        Button saveBtn = view.findViewById(R.id.save_btn);
        Button loadBtn = view.findViewById(R.id.load_btn);
        timetable = view.findViewById(R.id.timetable);

        addBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        loadBtn.setOnClickListener(this);

        new Handler().postDelayed(() -> {
            ArrayList<Schedule> schedules = new ArrayList<>(listener.getScheduleSet());
            timetable.add(schedules);


        }, 300);

        initView();
    }

    private void initView() {
        timetable.setOnStickerSelectEventListener((idx, schedules) -> {
            Intent i = new Intent(context, EditActivity.class);
            i.putExtra("mode", REQUEST_EDIT);
            i.putExtra("idx", idx);
            i.putExtra("schedules", schedules);
            startActivityForResult(i, REQUEST_EDIT);
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
}
