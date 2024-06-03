package androidtown.org.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidtown.org.R
import androidtown.org.activities.EditActivity
import androidtown.org.listener.TimeTableButtonListener
import androidx.fragment.app.Fragment
import com.github.tlaabs.timetableview.Schedule
import com.github.tlaabs.timetableview.Time
import com.github.tlaabs.timetableview.TimetableView

class FragmentTimeTable(private val listener: TimeTableButtonListener) : Fragment(), View.OnClickListener {
    private lateinit var context: Context

    private lateinit var timetable: TimetableView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_timetable, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        this.context = activity!!

        val addBtn = view.findViewById<Button>(R.id.add_btn)
        val clearBtn = view.findViewById<Button>(R.id.clear_btn)
        val saveBtn = view.findViewById<Button>(R.id.save_btn)
        val loadBtn = view.findViewById<Button>(R.id.load_btn)
        timetable = view.findViewById(R.id.timetable)

        addBtn.setOnClickListener(this)
        clearBtn.setOnClickListener(this)
        saveBtn.setOnClickListener(this)
        loadBtn.setOnClickListener(this)

        Handler().postDelayed({
            val schedules = ArrayList(listener.scheduleSet)
            timetable.add(schedules)
            checkContinueCourse()
        }, 500)

        initView()
    }

    private fun initView() {
        timetable.setOnStickerSelectEventListener { idx: Int, schedules: ArrayList<Schedule?>? ->
            val i = Intent(context, EditActivity::class.java)
            i.putExtra("mode", REQUEST_EDIT)
            i.putExtra("idx", idx)
            i.putExtra("schedules", schedules)
            startActivityForResult(i, REQUEST_EDIT)
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.add_btn) {
            val i = Intent(context, EditActivity::class.java)
            i.putExtra("mode", REQUEST_ADD)
            startActivityForResult(i, REQUEST_ADD)
        } else if (v.id == R.id.clear_btn) {
            timetable.removeAll()
        } else if (v.id == R.id.save_btn) {
            saveByPreference(timetable.createSaveData())
        } else if (v.id == R.id.load_btn) {
            loadSavedData()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_ADD -> if (resultCode == EditActivity.RESULT_OK_ADD) {
                val item = data!!.getSerializableExtra("schedules") as ArrayList<Schedule>?
                timetable.add(item)
            }

            REQUEST_EDIT -> if (resultCode == EditActivity.RESULT_OK_EDIT) {
                val idx = data!!.getIntExtra("idx", -1)
                val item = data.getSerializableExtra("schedules") as ArrayList<Schedule>?
                timetable.edit(idx, item)
            } else if (resultCode == EditActivity.RESULT_OK_DELETE) {
                val idx = data!!.getIntExtra("idx", -1)
                timetable.remove(idx)
            }
        }
    }

    private fun saveByPreference(data: String) {
        val mPref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = mPref.edit()
        editor.putString("timetable_demo", data)
        editor.apply()
        Toast.makeText(context, "saved!", Toast.LENGTH_SHORT).show()
    }

    private fun loadSavedData() {
        timetable.removeAll()
        val mPref = PreferenceManager. getDefaultSharedPreferences(context)
        val savedData = mPref.getString("timetable_demo", "")
        if (savedData == null || savedData == "") return
        timetable.load(savedData)
        Toast.makeText(context, "loaded!", Toast.LENGTH_SHORT).show()
    }

    private fun checkContinueCourse() {
        val tempSchedule = timetable.allSchedulesInStickers
        sortSchedules(tempSchedule)
        var startPlace: String
        var endPlace: String
        var sDay: String
        val result = StringBuilder()
        var day: Int
        for (i in tempSchedule.indices) {
            for (j in i until tempSchedule.size) {
                day = tempSchedule[i].day
                sDay = when (day) {
                    0 -> "월요일"
                    1 -> "화요일"
                    2 -> "수요일"
                    3 -> "목요일"
                    4 -> "금요일"
                    else -> ""
                }
                if (day == tempSchedule[j].day) {
                    // 먼저 듣는 강의를 먼저 등록한 경우
                    // i: 먼저 듣는 강의, j: i 다음 들을 강의
                    if (checkTime(tempSchedule[i].endTime, tempSchedule[j].startTime)) {
                        // 강의 장소에서 "-"을 기준으로 건물 이름만 따오기
                        startPlace = try {
                            tempSchedule[i].classPlace.substring(0, tempSchedule[i].classPlace.indexOf("-"))
                        } //"화상강의강의실"의 경우 "-"가 없기에 예외 처리
                        catch (e: Exception) {
                            tempSchedule[i].classPlace
                        }

                        // 강의 장소에서 "-"을 기준으로 건물 이름만 따오기
                        endPlace = try {
                            tempSchedule[j].classPlace.substring(0, tempSchedule[j].classPlace.indexOf("-"))
                        } //"화상강의강의실"의 경우 "-"가 없기에 예외 처리
                        catch (e: Exception) {
                            tempSchedule[j].classPlace
                        }
                        if (!checkPlaceAvailable(startPlace, endPlace)) {
                            result.append(sDay)
                                    .append("에 듣는 ")
                                    .append(tempSchedule[i].classTitle)
                                    .append("과(와) ")
                                    .append(tempSchedule[j].classTitle)
                                    .append("은(는) 연강이 불가능합니다.\n")
                        }
                    }
                }
            }
        }
        if (result.isEmpty()) Toast.makeText(context, "연강하는데에 지장이 가는 강의가 없습니다.", Toast.LENGTH_LONG).show()
        else Toast.makeText(context, result.toString(), Toast.LENGTH_LONG).show()
    }

    private fun checkTime(a: Time, b: Time): Boolean {
        val x1 = a.hour
        val x2 = a.minute
        val y1 = b.hour
        val y2 = b.minute
        if (x1 == y1 - 1) {
            return x2 == y2 + 50
        }
        return false
    }

    private fun checkPlaceAvailable(start: String, end: String): Boolean {
        // placeTable[출발 장소][도착 장소] 연강 가능 여부 (카카오맵 기준 도보 8분 이상일 경우 false 처리했습니다.)
        val placeTable = arrayOf(booleanArrayOf(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true),
                booleanArrayOf(true, true, false, false, false, false, false, false, false, false, false, false, false, false, false),
                booleanArrayOf(true, false, true, false, true, true, true, true, true, true, true, true, true, true, true),
                booleanArrayOf(true, false, false, true, true, true, false, true, true, true, true, true, true, true, true),
                booleanArrayOf(true, false, true, true, true, true, false, true, false, true, false, true, true, true, false),
                booleanArrayOf(true, false, true, true, true, true, true, true, false, true, false, true, true, true, true),
                booleanArrayOf(true, false, true, false, false, true, true, true, true, true, true, true, false, false, true),
                booleanArrayOf(true, false, true, true, true, true, true, true, true, true, true, true, true, true, true),
                booleanArrayOf(true, false, true, true, false, false, true, true, true, true, true, false, false, false, true),
                booleanArrayOf(true, false, true, true, true, true, true, true, true, true, true, true, true, true, true),
                booleanArrayOf(true, false, true, true, false, false, true, true, true, true, true, false, false, false, true),
                booleanArrayOf(true, false, true, true, true, true, true, true, false, true, false, true, true, true, false),
                booleanArrayOf(true, false, true, true, true, true, false, true, false, true, false, true, true, true, false),
                booleanArrayOf(true, false, true, true, true, true, false, true, false, true, false, true, true, true, false),
                booleanArrayOf(true, false, true, true, false, true, true, true, true, true, true, false, false, false, true))
        val istart = when (start) {
            "화상강의강의실(가상)" -> 0
            "AI관" -> 1
            "반도체대학" -> 2
            "교육대학원" -> 3
            "대학원" -> 4
            "글로벌센터" -> 5
            "비전타워" -> 6
            "가천관" -> 7
            "산학협력관" -> 8
            "공과대학1" -> 9
            "공과대학2" -> 10
            "예술대학1" -> 11
            "예술대학2" -> 12
            "바이오나노대학" -> 13
            "한의과대학" -> 14
            else -> return true
        }
        val iend = when (end) {
            "화상강의강의실(가상)" -> 0
            "AI관" -> 1
            "반도체대학" -> 2
            "교육대학원" -> 3
            "대학원" -> 4
            "글로벌센터" -> 5
            "비전타워" -> 6
            "가천관" -> 7
            "산학협력관" -> 8
            "공과대학1" -> 9
            "공과대학2" -> 10
            "예술대학1" -> 11
            "예술대학2" -> 12
            "바이오나노대학" -> 13
            "한의과대학" -> 14
            else -> return true
        }
        return placeTable[istart][iend]
    }

    private fun sortSchedules(schedules: ArrayList<Schedule>) {
        schedules.sortWith(java.util.Comparator<Schedule> sort@{ s1: Schedule, s2: Schedule ->
            val dayComparison = s1.day.compareTo(s2.day)
            if (dayComparison != 0) {
                return@sort dayComparison
            }

            val startTime1 = s1.startTime
            val startTime2 = s2.startTime

            val hourComparison = startTime1.hour.compareTo(startTime2.hour)
            if (hourComparison != 0) {
                return@sort hourComparison
            }
            startTime1.minute.compareTo(startTime2.minute)
        })
    }

    companion object {
        const val REQUEST_ADD: Int = 1
        const val REQUEST_EDIT: Int = 2
    }
}