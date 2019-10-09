package andr.mentorapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class NewStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_student)

        var nameEditText = findViewById(R.id.name_input) as EditText
        var createProfileButton = findViewById(R.id.add_new_user_button) as Button

        createProfileButton.setOnClickListener{
            val newIntent : Intent = Intent()
            newIntent.putExtra("name", nameEditText.text.toString())
            setResult(Activity.RESULT_OK, newIntent)
            finish()
        }
    }
}
