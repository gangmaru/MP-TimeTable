package androidtown.org.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidtown.org.R;
import androidtown.org.data.Student;
import androidtown.org.listener.QRButtonListener;


public class fragment_qr extends Fragment {

    private TextView tvCountdown;
    private ImageView qrCode;
    private CountDownTimer countDownTimer;
    private static final long START_TIME_IN_MILLIS = 300000; // 5분 타이머

    private final QRButtonListener listener;

    public fragment_qr(QRButtonListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr, container, false);

        tvCountdown = view.findViewById(R.id.tvCountdown);
        qrCode = view.findViewById(R.id.qrCode);
        TextView department = view.findViewById(R.id.department_input);
        TextView name = view.findViewById(R.id.name_input);
        TextView stdNo = view.findViewById(R.id.studentId_input);

        new Handler().postDelayed(() -> {
            Student student = listener.getStudent();
            department.setText(student.getDepartment());
            name.setText(student.getName());
            stdNo.setText(student.getStudentNumber());
            qrCode.setImageBitmap(createQRCode());
        }, 500);
        startTimer();

        Button refreshButton = view.findViewById(R.id.refresh_btn);
        refreshButton.setOnClickListener(v -> refreshFragment());

        return view;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(START_TIME_IN_MILLIS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
                tvCountdown.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                tvCountdown.setText("00:00");
            }
        }.start();
    }

    private void refreshFragment() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            qrCode.setImageBitmap(createQRCode());
            startTimer();
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    private Bitmap createQRCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            Student student = listener.getStudent();
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            return barcodeEncoder.encodeBitmap(
                    "m" + student.getStudentNumber() + dateFormat.format(new Date()),
                    BarcodeFormat.QR_CODE, 200, 200);
        } catch (Exception ignored) {
        }
        return null;
    }
}