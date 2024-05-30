package androidtown.org.listener;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidtown.org.R;
import androidtown.org.data.type.DataType;
import androidtown.org.fragments.fragment_qr;
import androidtown.org.fragments.fragment_timetable;

public class QRButtonListener implements View.OnClickListener,WebDataListener{

    private final LinearLayout welcome;
    private final Button timetable;
    private final Button grade;
    private final Button qr;
    private final Button setting;
    private final FragmentManager fragmentManager;

    public QRButtonListener(LinearLayout welcome, Button timetable, Button grade, Button qr, Button setting, FragmentManager fragmentManager) {
        this.welcome = welcome;
        this.timetable = timetable;
        this.grade = grade;
        this.qr = qr;
        this.setting = setting;
        this.fragmentManager = fragmentManager;
    }


    @Override
    public void receive(String data, DataType type) {

    }

    @Override
    public void onClick(View view) {
        welcome.setVisibility(View.GONE);
        timetable.setBackgroundColor(Color.parseColor("#004E96"));
        grade.setBackgroundColor(Color.parseColor("#004E96"));
        qr.setBackgroundColor(Color.parseColor("#40A7B5"));
        setting.setBackgroundColor(Color.parseColor("#004E96"));

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment, new fragment_qr());
        transaction.commit();
    }
}
