package andr.mentorapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_schedule.*
import kotlinx.android.synthetic.main.activity_add_user.*
import kotlinx.android.synthetic.main.activity_add_user.unique_id_input

/**
 *  Add Tutor Schedule Page
 *
 *  This class creates the add user screen to allow the admin to add a new tutor to the database
 *
 *  @author Courtney Erbes
 *  @date 11/10/2019
 */
class AddTutorScheduleActivity : AppCompatActivity() {

    /**
     *  creates the add tutor schedule page
     *
     *  @param savedInstanceState data most recently provided
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_schedule) // set the layout to the particular xml file

        add_schedule_button.setOnClickListener {
            val newIntent: Intent = Intent() // create new request
            newIntent.putExtra("day", shift_day_input.text.toString()) // add data enter in this screen
            newIntent.putExtra("start", shift_start_input.text.toString()) // add data enter in this screen
            newIntent.putExtra("end", shift_end_input.text.toString()) // add data enter in this screen
            setResult(Activity.RESULT_OK,newIntent) // set result
            finish() // exit the screen
        }
    }
}
