package andr.mentorapp

import android.os.Bundle
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tutor_schedule.*

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
    }

    /*
     * Helper function to display schedule of the specified Tutor to the User
     */
    fun displaySchedule() {
        val context = this
        val db = MentorAppDatabase.invoke(context)

        val shifts =
            db.tutorScheduleDao().findTutorSchedulesByIdFromdDB(intent.getStringExtra("tutorId"))

        // add a row informing the Day and Start/ End Time for each Tutor's shift
        for (shift in shifts) {
            val row = TableRow(context)
            val params = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT)
            val day = TextView(context)
            val startShiftView = TextView(context)
            val endShiftView = TextView(context)

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
}
