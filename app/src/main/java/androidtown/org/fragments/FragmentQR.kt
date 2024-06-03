package androidtown.org.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidtown.org.R
import androidtown.org.listener.QRButtonListener
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.text.SimpleDateFormat
import java.util.Date

class FragmentQR(private val listener: QRButtonListener) : Fragment() {
    private lateinit var tvCountdown: TextView
    private lateinit var qrCode: ImageView
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_qr, container, false)

        tvCountdown = view.findViewById(R.id.tvCountdown)
        qrCode = view.findViewById(R.id.qrCode)
        val department = view.findViewById<TextView>(R.id.department_input)
        val name = view.findViewById<TextView>(R.id.name_input)
        val stdNo = view.findViewById<TextView>(R.id.studentId_input)

        Handler().postDelayed({
            val student = listener.student
            department.text = student.department
            name.text = student.name
            stdNo.text = student.studentNumber
            qrCode.setImageBitmap(createQRCode())
        }, 500)
        startTimer()

        val refreshButton = view.findViewById<Button>(R.id.refresh_btn)
        refreshButton.setOnClickListener { refreshFragment() }

        return view
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(START_TIME_IN_MILLIS, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000).toInt() / 60
                val seconds = (millisUntilFinished / 1000).toInt() % 60
                val timeLeftFormatted = String.format("%02d:%02d", minutes, seconds)
                tvCountdown.text = timeLeftFormatted
            }

            override fun onFinish() {
                tvCountdown.text = "00:00"
            }
        }.start()
    }

    private fun refreshFragment() {
        countDownTimer.cancel()
        qrCode.setImageBitmap(createQRCode())
        startTimer()
        val ft = requireFragmentManager().beginTransaction()
        ft.detach(this).attach(this).commit()
    }

    private fun createQRCode(): Bitmap? {
        val dateFormat = SimpleDateFormat("yyyyMMddHHmmss")
        try {
            val student = listener.student
            val barcodeEncoder = BarcodeEncoder()
            return barcodeEncoder.encodeBitmap(
                    "m" + student.studentNumber + dateFormat.format(Date()),
                    BarcodeFormat.QR_CODE, 200, 200)
        } catch (ignored: Exception) {
        }
        return null
    }

    companion object {
        private const val START_TIME_IN_MILLIS: Long = 300000 // 5분 타이머
    }
}