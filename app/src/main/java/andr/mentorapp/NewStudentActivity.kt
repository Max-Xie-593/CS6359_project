package andr.mentorapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_student.*

class NewStudentActivity : AppCompatActivity() {

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
