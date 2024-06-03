package androidtown.org.activities

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidtown.org.R
import androidtown.org.fragments.FragmentTimeTable
import androidx.appcompat.app.AppCompatActivity
import com.github.tlaabs.timetableview.Schedule
import com.github.tlaabs.timetableview.Time

class EditActivity : AppCompatActivity(), View.OnClickListener {
    private var context: Context? = null

    private var deleteBtn: Button? = null
    private var submitBtn: Button? = null
    private var subjectEdit: EditText? = null
    private var classroomEdit: EditText? = null
    private var professorEdit: EditText? = null
    private var daySpinner: Spinner? = null
    private var startTv: TextView? = null
    private var endTv: TextView? = null

    //request mode
    private var mode = 0

    private var schedule: Schedule? = null
    private var editIdx = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        init()
    }

    private fun init() {
        this.context = this
        deleteBtn = findViewById(R.id.delete_btn)
        submitBtn = findViewById(R.id.submit_btn)
        subjectEdit = findViewById(R.id.subject_edit)
        classroomEdit = findViewById(R.id.classroom_edit)
        professorEdit = findViewById(R.id.professor_edit)
        daySpinner = findViewById(R.id.day_spinner)
        startTv = findViewById(R.id.start_time)
        endTv = findViewById(R.id.end_time)

        //set the default time
        schedule = Schedule()
        schedule!!.startTime = Time(10, 0)
        schedule!!.endTime = Time(13, 30)

        checkMode()
        initView()
    }

    /** check whether the mode is ADD or EDIT  */
    private fun checkMode() {
        val i = intent
        mode = i.getIntExtra("mode", FragmentTimeTable.REQUEST_ADD)

        if (mode == FragmentTimeTable.REQUEST_EDIT) {
            loadScheduleData()
            deleteBtn!!.visibility = View.VISIBLE
        }
    }

    private fun initView() {
        submitBtn!!.setOnClickListener(this)
        deleteBtn!!.setOnClickListener(this)

        daySpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                //position 0: Mon, 1: Tue, 2: Wed, 3: Thu, 4: Fri
                schedule!!.day = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        startTv!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val dialog = TimePickerDialog(context, listener, schedule!!.startTime.hour, schedule!!.startTime.minute, false)
                dialog.show()
            }

            private val listener = OnTimeSetListener { _, hourOfDay, minute ->
                startTv!!.text = "$hourOfDay:$minute"
                schedule!!.startTime.hour = hourOfDay
                schedule!!.startTime.minute = minute
            }
        })
        endTv!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val dialog = TimePickerDialog(context, listener, schedule!!.endTime.hour, schedule!!.endTime.minute, false)
                dialog.show()
            }

            private val listener = OnTimeSetListener { _, hourOfDay, minute ->
                endTv!!.text = "$hourOfDay:$minute"
                schedule!!.endTime.hour = hourOfDay
                schedule!!.endTime.minute = minute
            }
        })
    }

    override fun onClick(v: View) {
        if (v.id == R.id.submit_btn) {
            if (mode == FragmentTimeTable.REQUEST_ADD) {
                inputDataProcessing()
                val i = Intent()
                val schedules = ArrayList<Schedule?>()
                schedules.add(schedule)
                i.putExtra("schedules", schedules)
                setResult(RESULT_OK_ADD, i)
                finish()
            } else if (mode == FragmentTimeTable.REQUEST_EDIT) {
                inputDataProcessing()
                val i = Intent()
                val schedules = ArrayList<Schedule?>()
                schedules.add(schedule)
                i.putExtra("idx", editIdx)
                i.putExtra("schedules", schedules)
                setResult(RESULT_OK_EDIT, i)
                finish()
            }
        } else if (v.id == R.id.delete_btn) {
            val i = Intent()
            i.putExtra("idx", editIdx)
            setResult(RESULT_OK_DELETE, i)
            finish()
        }
    }


    private fun loadScheduleData() {
        val i = intent
        editIdx = i.getIntExtra("idx", -1)
        val schedules = i.getSerializableExtra("schedules") as ArrayList<Schedule>?
        schedule = schedules!![0]
        subjectEdit!!.setText(schedule!!.classTitle)
        classroomEdit!!.setText(schedule!!.classPlace)
        professorEdit!!.setText(schedule!!.professorName)
        daySpinner!!.setSelection(schedule!!.day)
    }

    private fun inputDataProcessing() {
        schedule!!.classTitle = subjectEdit!!.text.toString()
        schedule!!.classPlace = classroomEdit!!.text.toString()
        schedule!!.professorName = professorEdit!!.text.toString()
    }

    companion object {
        const val RESULT_OK_ADD: Int = 1
        const val RESULT_OK_EDIT: Int = 2
        const val RESULT_OK_DELETE: Int = 3
    }
}
