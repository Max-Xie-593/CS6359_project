package andr.mentorapp

import andr.mentorapp.Database.DatabaseManager
import android.os.Bundle
import android.widget.Button
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tutor_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * This class handles the page listing the Tutors in the system
 * in order to allow Admins to select which Tutor they wish to view the schedule of
 *
 * @author Courtney Erbes
 * @date 10/19/19
 */
class TutorListActivity : AppCompatActivity() {
    /**
     *  When the Activity is created, set title and display list of Tutors
     *
     *  @param savedInstanceState data most recently provided
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_list)

        tutorListMessage.text = "List of Tutors:"

        this.displayList()
    }

    /**
     * Helper function to display list of Tutors to the User
     */
    fun displayList() {
        val tutors = DatabaseManager.getAllTutors()

        // add a row containing a button for each Tutor
        for (tutor in tutors) {
            val row = TableRow(this)
            val params = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT)
            val name = Button(this)

            row.layoutParams = params
            name.text = tutor.userName
            name.setOnClickListener {
                intent.setClass(this, TutorScheduleActivity::class.java)
                intent.putExtra("tutorId", tutor.userId)
                intent.putExtra("tutorName", tutor.userName)
                intent.putExtra("adminStatus", true)
                startActivity(intent)
            }

            row.addView(name, 0)
            tutorListTable.addView(row)
        }
    }
}
