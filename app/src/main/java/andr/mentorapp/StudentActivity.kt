package andr.mentorapp

import andr.mentorapp.GetHelpController.checkedInTutors
import andr.mentorapp.GetHelpController.finishSession
import andr.mentorapp.GetHelpController.leaveQueue
import andr.mentorapp.GetHelpController.studentQueue
import andr.mentorapp.GetHelpController.tutorSessions
import andr.mentorapp.Database.DatabaseManager
import andr.mentorapp.Database.StudentUser
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_student.*
import android.view.View

/**
 * Description of class
 *
 * @author Nymisha
 * @date 10/29/19 last edited
 */

class StudentActivity : AppCompatActivity() {

    /**
     * On start of the student screen, the student can be in 3 states:
     * 1. Signed in
     * 2. Getting Help by a Tutor
     * 3. Waiting in the Queue to Get Help
     *
     * By default, show the signed in page,
     *      which displays the get help button
     * Then check if the student is in one of the tutor session pairs
     *      which if true, display their matched tutor name and the done button instead
     * Then check if the student is in the queue to be helped
     *      which if true, display their queue position and the leave queue button instead
     *
     * @param savedInstanceState   Bundle? data brought from previous request
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
      
        // add sign out fragment to view
        if (savedInstanceState == null) {
            addFragment(activity_student_parent.id, SignOutFragment.newInstance())
        }

        var thisStudentID = intent.getStringExtra("id")

        if (checkedInTutors.isEmpty()) {
            studentMessage.setText("The CSMC is closed (no checked in tutors), please come back when we are open again")
            return
        }

        studentMessage.setText("Welcome to the Student page " + intent.getStringExtra("name") + "!")
        studentGetHelpButton.setVisibility(View.VISIBLE)

        var studentUser = DatabaseManager.getUserById(thisStudentID) as StudentUser
        for (session in tutorSessions) {

            if (session.second.userId == studentUser.userId) {
                studentMessage.setText("You're getting help from " + session.first.userName + " in " + session.third.courseName)
                studentGetHelpButton.setVisibility(View.GONE)
                studentDone.setVisibility(View.VISIBLE)
                break
            }
        }
        var queuePosition = 1
        for (waitingStudent in studentQueue) {
            if (waitingStudent.first.userId == studentUser.userId) {
                studentMessage.setText("You're in the queue to be helped, position " + queuePosition + " for " + waitingStudent.second.courseName)
                studentGetHelpButton.setVisibility(View.GONE)
                studentLeaveQueue.setVisibility(View.VISIBLE)
                break
            } else {
                queuePosition++
            }

        }


        studentGetHelpButton.setOnClickListener {
            selectCourse(thisStudentID)
        }

        studentDone.setOnClickListener {
            this.finishStudentSession(studentUser);
        }

        studentLeaveQueue.setOnClickListener {
            this.leaveFromQueue(studentUser);
        }

    }


    /**
     * This is the function called when the "Get Help" button is clicked
     *
     * starts the CourseListActivity class to show the list of courses the student can select
     *
     * @return void
     */
    fun selectCourse(studentId: String) {

        intent.setClass(this, CourseListActivity::class.java)
        intent.putExtra("studentId", studentId)
        startActivity(intent)

    }


    /**
     * This is the function called when the "Leave Queue" button is clicked
     *
     * Calls the leaveQueue function from GetHelpController to edit the queue
     *      if it receives true, the removal was successful and we change back to the home view
     *      if it receives false, the removal was not successful, display an error message
     *
     * @param studentUser    the object of the student using this page
     * @return void
     */
    fun leaveFromQueue(studentUser: StudentUser) {

        if (leaveQueue(studentUser)) {
            studentLeaveQueue.setVisibility(View.GONE)
            studentGetHelpButton.setVisibility(View.VISIBLE)
            studentMessage.setText("Welcome to the Student page " + studentUser.userName + "!")
        }
        else {
            studentMessage.setText("Sorry, error in leaving queue")
        }
    }


    /**
    * This is the function called when the "Done" button is clicked
    *
    * Calls the finishSession function from GetHelpController to end the session
    * and displays the home page view again
    *
    * @param studentUser    the object of the student using this page
    * @return void
    */
    fun finishStudentSession(studentUser: StudentUser) {

        for ((tutor, student) in tutorSessions) {
            if (student.userId == studentUser.userId) {

                finishSession(tutor)
                studentDone.setVisibility(View.GONE)
                studentGetHelpButton.setVisibility(View.VISIBLE)
                studentMessage.setText("Welcome to the Student page " + studentUser.userName + "!")

                break
            }
        }
    }
}
