package androidtown.org;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;
//import androidx.navigation.ui.NavigationUI;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button timetable=(Button) findViewById(R.id.timetable_btn);
        final Button grade=(Button) findViewById(R.id.grade_btn);
        final Button qr=(Button) findViewById(R.id.qr_btn);
        final Button setting=(Button) findViewById(R.id.setting_btn);
        final LinearLayout welcome=(LinearLayout) findViewById(R.id.welcome);

        welcome.setVisibility(View.GONE);
        timetable.setBackgroundColor(Color.parseColor("#40A7B5"));
        grade.setBackgroundColor(Color.parseColor("#004E96"));
        qr.setBackgroundColor(Color.parseColor("#004E96"));
        setting.setBackgroundColor(Color.parseColor("#004E96"));
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment,new fragment_timetable());
        fragmentTransaction.commit();

        timetable.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                welcome.setVisibility(View.GONE);
                timetable.setBackgroundColor(Color.parseColor("#40A7B5"));
                grade.setBackgroundColor(Color.parseColor("#004E96"));
                qr.setBackgroundColor(Color.parseColor("#004E96"));
                setting.setBackgroundColor(Color.parseColor("#004E96"));
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,new fragment_timetable());
                fragmentTransaction.commit();

            }
        });

        grade.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                welcome.setVisibility(View.GONE);
                timetable.setBackgroundColor(Color.parseColor("#004E96"));
                grade.setBackgroundColor(Color.parseColor("#40A7B5"));
                qr.setBackgroundColor(Color.parseColor("#004E96"));
                setting.setBackgroundColor(Color.parseColor("#004E96"));
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,new fragment_grade());
                fragmentTransaction.commit();

            }
        });

        qr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                welcome.setVisibility(View.GONE);
                timetable.setBackgroundColor(Color.parseColor("#004E96"));
                grade.setBackgroundColor(Color.parseColor("#004E96"));
                qr.setBackgroundColor(Color.parseColor("#40A7B5"));
                setting.setBackgroundColor(Color.parseColor("#004E96"));
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,new fragment_qr());
                fragmentTransaction.commit();

            }
        });

        setting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                welcome.setVisibility(View.GONE);
                timetable.setBackgroundColor(Color.parseColor("#004E96"));
                grade.setBackgroundColor(Color.parseColor("#004E96"));
                qr.setBackgroundColor(Color.parseColor("#004E96"));
                setting.setBackgroundColor(Color.parseColor("#40A7B5"));
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,new fragment_setting());
                fragmentTransaction.commit();

            }
        });
    }
}