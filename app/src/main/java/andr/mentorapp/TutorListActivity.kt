package andr.mentorapp

import android.os.Bundle
import android.widget.Button
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tutor_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/*
 * This class handles the page listing the Tutors in the system
 * in order to allow Admins to select which Tutor they wish to view the schedule of
 */
class TutorListActivity : AppCompatActivity() {

    // When the Activity is created, set title and display list of Tutors
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_list)

        tutorListMessage.text = "List of Tutors:"

        this.displayList()
    }

    // helper function to display list of Tutors to the User
    fun displayList() {
        val context = this
        val db = MentorAppDatabase.invoke(context)

        val tutors = db.userDao().getAllTutors()

        // add a row containing a button for each Tutor
        for (tutor in tutors) {
            val row = TableRow(context)
            val params = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT)
            val name = Button(context)

            row.layoutParams = params
            name.text = tutor.userName
            name.setOnClickListener {
                intent.setClass(context, TutorScheduleActivity::class.java)
                intent.putExtra("tutorId", tutor.userId)
                intent.putExtra("tutorName", tutor.userName)
                startActivity(intent)
            }

            row.addView(name, 0)
            tutorListTable.addView(row)
        }
    }

}