package andr.mentorapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_student.*

/**
 * New Student creation page
 *
 * @author Mugdha Gupta
 * @date 9/28/19
 */
class NewStudentActivity : AppCompatActivity() {
    /**
     *  Creates the new student view
     *
     *  @param savedInstanceState data brought from previous request
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_student)

        add_new_user_button.setOnClickListener{
            val newIntent : Intent = Intent()
            newIntent.putExtra("name", name_input.text.toString())
            setResult(Activity.RESULT_OK, newIntent)
            finish()
        }
    }
}
