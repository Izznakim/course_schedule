package com.dicoding.courseschedule.ui.add

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.TimePickerFragment
import com.dicoding.courseschedule.util.timeFormatter
import java.text.SimpleDateFormat
import java.time.LocalDate

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var addCourseViewModel: AddCourseViewModel
    private lateinit var edCourseName: EditText
    private lateinit var spDay: Spinner
    private lateinit var ibStartTime: ImageButton
    private lateinit var tvStartTime: TextView
    private lateinit var ibEndTime: ImageButton
    private lateinit var tvEndTime: TextView
    private lateinit var edLecturer: EditText
    private lateinit var edNote: EditText

    private val START = "startTimePicker"
    private val END = "endTimePicker"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        val factory = AddCourseViewModelFactory.createFactory(this)
        addCourseViewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)

        setupView()
        ibStartTime.setOnClickListener {
            val timePickerFragment = TimePickerFragment()
            timePickerFragment.show(supportFragmentManager, START)
        }

        ibEndTime.setOnClickListener {
            TimePickerFragment().show(supportFragmentManager, END)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                addCourseViewModel.insertCourse(
                    edCourseName.text.toString(),
                    spDay.selectedItemPosition,
                    tvStartTime.text.toString(),
                    tvEndTime.text.toString(),
                    edLecturer.text.toString(),
                    edNote.text.toString()
                )
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupView() {
        edCourseName = findViewById(R.id.ed_course_name)
        spDay = findViewById(R.id.sp_day)
        ibStartTime = findViewById(R.id.ib_start_time)
        tvStartTime = findViewById(R.id.tv_start_time)
        ibEndTime = findViewById(R.id.ib_end_time)
        tvEndTime = findViewById(R.id.tv_end_time)
        edLecturer = findViewById(R.id.ed_lecturer)
        edNote = findViewById(R.id.ed_note)
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        if (tag == START) {
            tvStartTime.text = "$hour:$minute".timeFormatter()
        } else {
            tvEndTime.text = "$hour:$minute".timeFormatter()
        }
    }
}