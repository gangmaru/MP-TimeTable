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
import androidtown.org.data.type.DataType;
import androidtown.org.fragments.fragment_qr;

public class QRButtonListener implements View.OnClickListener, WebDataListener {

    private final LinearLayout welcome;
    private final Button timetable;
    private final Button grade;
    private final Button qr;
    private final Button setting;
    private final ImageView qrImageView;
    private final FragmentManager fragmentManager;
    private final WebView dataWebView;

    public QRButtonListener(LinearLayout welcome, Button timetable, Button grade, Button qr, Button setting, ImageView qrImageView, FragmentManager fragmentManager, WebView dataWebView) {
        this.welcome = welcome;
        this.timetable = timetable;
        this.grade = grade;
        this.qr = qr;
        this.setting = setting;
        this.qrImageView = qrImageView;
        this.fragmentManager = fragmentManager;
        this.dataWebView = dataWebView;
    }


    @Override
    public void receive(String data, DataType type) {
        if (type != DataType.QR) return;
        JsonObject json = JsonParser.parseString(data).getAsJsonObject();
        JsonObject object = json.get("userInfoList")
                .getAsJsonArray().get(0)
                .getAsJsonObject();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(
                    "m" + object.get("member_no").getAsString() + dateFormat.format(new Date()),
                    BarcodeFormat.QR_CODE, 200, 200);
            qrImageView.setImageBitmap(bitmap);
            //TODO 안뜸 데이터는 완료
        } catch (Exception ignored) {

        }
    }

    @Override
    public void onClick(View view) {
        dataWebView.loadUrl("https://portal.gachon.ac.kr/gc/common/UserInfoChange.eps");

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
