package androidtown.org.listener

import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidtown.org.R
import androidtown.org.data.type.DataType
import androidtown.org.fragments.FragmentSetting
import androidx.fragment.app.FragmentManager

class SettingButtonListener(private val welcome: LinearLayout, private val timetable: Button, private val grade: Button, private val qr: Button, private val setting: Button, private val fragmentManager: FragmentManager) : View.OnClickListener, WebDataListener {
    override fun receive(data: String?, type: DataType?) {
    }

    override fun onClick(view: View) {
        welcome.visibility = View.GONE
        timetable.setBackgroundColor(Color.parseColor("#004E96"))
        grade.setBackgroundColor(Color.parseColor("#004E96"))
        qr.setBackgroundColor(Color.parseColor("#004E96"))
        setting.setBackgroundColor(Color.parseColor("#40A7B5"))

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, FragmentSetting())
        transaction.commit()
    }
}
