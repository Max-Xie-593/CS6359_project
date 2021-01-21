package andr.mentorapp

import andr.mentorapp.Database.DatabaseManager
import andr.mentorapp.Database.Schedule
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tutor_schedule.*

const val GET_NEW_SCHEDULE_RESULT : Int = 0

/**
 * This class handles displaying the schedule for the specified Tutor
 *
 * @author Courtney Erbes
 * @date 10/18/19
 */
class TutorScheduleActivity : AppCompatActivity() {
    /**
     *  When the Activity is created, set title and display schedule of Tutor
     *
     *  @param savedInstanceState data most recently provided
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_schedule)

        tutorScheduleMessage.text = "Schedule for " + intent.getStringExtra("tutorName") + ":"

        this.displaySchedule()

        if (intent.getBooleanExtra("adminStatus", false)) {
            this.setAdminView()
        }
    }

    /**
     * Helper function to display schedule of the specified Tutor to the User
     */
    fun displaySchedule() {
        val context = this

        val shifts =
            DatabaseManager.getSchedulesByTutorId(intent.getStringExtra("tutorId"))

        // add header to schedule table
        var row = TableRow(context)
        var params = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT)
        var day = TextView(context)
        var startShiftView = TextView(context)
        var endShiftView = TextView(context)

        row.layoutParams = params
        day.text = "Day"
        startShiftView.text = "Start Time"
        endShiftView.text = "End Time"

        row.addView(day, 0)
        row.addView(startShiftView, 1)
        row.addView(endShiftView, 2)
        scheduleTable.addView(row)

        // add a row informing the Day and Start/ End Time for each Tutor's shift
        for (shift in shifts) {
            row = TableRow(context)
            day = TextView(context)
            startShiftView = TextView(context)
            endShiftView = TextView(context)

            row.layoutParams = params
            day.text = shift.day
            startShiftView.text = shift.shiftStart
            endShiftView.text = shift.shiftEnd

            row.addView(day, 0)
            row.addView(startShiftView, 1)
            row.addView(endShiftView, 2)
            scheduleTable.addView(row)
        }
    }

    /**
     * Helper function to set buttons for Admins to delete tutor schedules and add new schedules
     */
    fun setAdminView() {
        add_schedule_button.visibility = View.VISIBLE

        add_schedule_button.setOnClickListener {
            intent.setClass(this, AddTutorScheduleActivity::class.java)
            startActivityForResult(intent, GET_NEW_SCHEDULE_RESULT)
        }

        // add delete buttons for each schedule
        for (i in 1 until scheduleTable.childCount) {
            var child = scheduleTable.getChildAt(i)

            if (child is TableRow) {
                val button = Button(this)
                button.text = "Delete"

                button.setOnClickListener {
                    val day = child.getChildAt(0) as TextView
                    val start = child.getChildAt(1) as TextView
                    val end = child.getChildAt(2) as TextView

                    DatabaseManager.deleteTutorSchedule(
                        intent.getStringExtra("tutorId"),
                        day.text as String,
                        start.text as String,
                        end.text as String)

                    scheduleTable.removeView(child)
                }

                child.addView(button)
            }
        }
    }

    /**
     *  function that is called from any results from the function [startActivityForResult] in this class
     *  and adds a new schedule to the db
     *
     *  @param requestCode code from the param in initialization of [startActivityForResult]
     *  @param resultCode code that is return from the finished execution of [startActivityForResult]
     *  @param data information received from the finished execution of [startActivityForResult]
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == GET_NEW_SCHEDULE_RESULT && resultCode == Activity.RESULT_OK && data != null){ // from the Add Button
            DatabaseManager.insertTutorSchedule(
                intent.getStringExtra("tutorId"),
                Schedule(
                    data.getStringExtra("day"),
                    data.getStringExtra("start"),
                    data.getStringExtra("end")))

            scheduleTable.removeAllViews()
            this.displaySchedule()
            this.setAdminView()
        }
    }
}
