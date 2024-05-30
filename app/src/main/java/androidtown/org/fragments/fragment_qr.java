package androidtown.org.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidtown.org.R;


public class fragment_qr extends Fragment {

    private TextView tvCountdown;
    private CountDownTimer countDownTimer;
    private static final long START_TIME_IN_MILLIS = 300000; // 5분 타이머

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_qr() {
        // Required empty public constructor
    }
    public static fragment_qr newInstance(String param1, String param2) {
        fragment_qr fragment = new fragment_qr();
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr, container, false);

        tvCountdown = view.findViewById(R.id.tvCountdown);
        startTimer();

        Button refreshButton = view.findViewById(R.id.refresh_btn);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshFragment();
            }
        });

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
    private void refreshFragment(){
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (ft != null) {
            ft.detach(this).attach(this).commit();
        }
    }
}