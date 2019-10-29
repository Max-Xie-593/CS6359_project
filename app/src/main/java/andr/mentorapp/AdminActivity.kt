package andr.mentorapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_admin.*

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        adminMessage.text = "Welcome to the Admin page " + intent.getStringExtra("name") + "!"

        // add sign out fragment to view
        if (savedInstanceState == null) {
            addFragment(activity_admin_parent.id, SignOutFragment.newInstance())
        }

        view_tutors_button.setOnClickListener {
            intent.setClass(this,AdminListActivity::class.java)
            startActivity(intent)
        }

        view_admins_button.setOnClickListener {

        }

        view_schedule_button.setOnClickListener {
            intent.setClass(this, TutorListActivity::class.java)
            startActivity(intent)
        }
    }

}
