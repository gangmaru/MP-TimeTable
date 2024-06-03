package androidtown.org.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidtown.org.R
import androidtown.org.activities.LoginActivity
import androidx.fragment.app.Fragment

class FragmentSetting : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            Toast.makeText(activity, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.putExtra("logout", true)
            startActivity(intent)
        }

        return view
    }
}