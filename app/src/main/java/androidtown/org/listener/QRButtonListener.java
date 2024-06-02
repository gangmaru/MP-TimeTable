package androidtown.org.listener;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidtown.org.R;
import androidtown.org.data.Student;
import androidtown.org.data.type.DataType;
import androidtown.org.fragments.fragment_qr;

public class QRButtonListener implements View.OnClickListener, WebDataListener {

    private final LinearLayout welcome;
    private final Button timetable;
    private final Button grade;
    private final Button qr;
    private final Button setting;
    private final FragmentManager fragmentManager;
    private final WebView dataWebView1;
    private final WebView dataWebView2;
    private final Student student = new Student();

    public QRButtonListener(LinearLayout welcome, Button timetable, Button grade, Button qr, Button setting, FragmentManager fragmentManager, WebView dataWebView1, WebView dataWebView2) {
        this.welcome = welcome;
        this.timetable = timetable;
        this.grade = grade;
        this.qr = qr;
        this.setting = setting;
        this.fragmentManager = fragmentManager;
        this.dataWebView1 = dataWebView1;
        this.dataWebView2 = dataWebView2;
    }


    @Override
    public void receive(String data, DataType type) {
        switch (type) {
            case DEPARTMENT: {
                JsonObject json = JsonParser.parseString(data).getAsJsonObject();
                JsonObject object = json.get("eduYear_list")
                        .getAsJsonArray().get(0)
                        .getAsJsonObject();
                String department = object.get("kwuhs.f_com001_nm('',a.cls_maj_cd)").getAsString();
                student.setDepartment(department);
                break;
            }
            case NAME: {
                JsonObject json = JsonParser.parseString(data).getAsJsonObject();
                JsonObject object = json.get("scholarship_list")
                        .getAsJsonArray().get(0)
                        .getAsJsonObject();
                String name = object.get("std_nm").getAsString();
                String studentNumber = object.get("std_no").getAsString();
                student.setName(name);
                student.setStudentNumber(studentNumber);
                break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        dataWebView1.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL024.eps?type=year_hakginm");
        //std_nm 이름
        //std_no 학번
        dataWebView2.loadUrl("https://portal.gachon.ac.kr/gc/portlet/PTL011.eps?grade=1");
        //kwuhs.f_com001_nm('',a.cls_maj_cd) -> 학과

        welcome.setVisibility(View.GONE);
        timetable.setBackgroundColor(Color.parseColor("#004E96"));
        grade.setBackgroundColor(Color.parseColor("#004E96"));
        qr.setBackgroundColor(Color.parseColor("#40A7B5"));
        setting.setBackgroundColor(Color.parseColor("#004E96"));

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment, new fragment_qr(this));
        transaction.commit();
    }

    public Student getStudent() {
        return student;
    }
}
