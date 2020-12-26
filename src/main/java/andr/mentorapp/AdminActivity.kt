package andr.mentorapp

import andr.mentorapp.Database.ADMIN_LEVEL
import andr.mentorapp.Database.DatabaseManager
import andr.mentorapp.Database.TUTOR_LEVEL
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_admin.*

/**
 *  Admin Home Page
 *
 *  This class creates the Admin home screen after signing in as an admin to the app
 *
 *  @author Max Xie, Mugdha Gupta
 *  @date 10/26/2019
 */
class AdminActivity : AppCompatActivity() {

    /**
     *  creates the admin home page screen
     *  @param savedInstanceState data most recently provided
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin) // set the layout to the particular xml file

        // set the text of the admin home screen text
        adminMessage.text = "Welcome to the Admin page " + intent.getStringExtra("name") + "!"

        // add sign out fragment to view
        if (savedInstanceState == null) {
            addFragment(activity_admin_parent.id, SignOutFragment.newInstance())
        }

         // button to allow the admin to see the list of tutors that are in the database
        view_tutors_button.setOnClickListener {
            intent.setClass(this,AdminModifyUsersList::class.java)
            intent.putExtra("level", TUTOR_LEVEL)
            startActivity(intent)
        }

        // button to allow the admin to see the list of admins that are in the database
        view_admins_button.setOnClickListener {
            intent.setClass(this,AdminModifyUsersList::class.java)
            intent.putExtra("level", ADMIN_LEVEL)
            startActivity(intent)
        }

        // button to allow the admin to see the schedules of the tutors in the database
        view_schedule_button.setOnClickListener {
            var vsIntent = ViewScheduleIntent(AdminViewScheduleStrategy())
            startActivity(vsIntent.generateIntent(this, intent))
        }
    }

    override fun onResume() {
        super.onResume()
        adminMessage.text = "Welcome to the Admin page " + DatabaseManager.getUserById(intent.getStringExtra("id")).userName + "!"
    }
}
