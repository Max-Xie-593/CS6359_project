package andr.mentorapp

import android.os.Bundle
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_admin_list.*
import kotlinx.android.synthetic.main.activity_tutor_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AdminListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_list)

        listMessage.text = "LIST"
        this.displayList()
    }

    fun displayList() {
        val context = this
        val db = MentorAppDatabase.invoke(context)

        val tutors = db.userDao().getAllTutors()

        // add a row containing a button for each Tutor
        for (tutor in tutors) {
            val row = TableRow(context)
            val params = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT)
            val name = TextView(context)

            row.layoutParams = params
            name.text = tutor.userName
            row.addView(name, 0)

            val editButton = Button(context)
            editButton.text = "EDIT"
            editButton.setOnClickListener {

            }
            row.addView(editButton,1)

            val deleteButton = Button(context)
            deleteButton.text = "DELETE"
            deleteButton.setOnClickListener {

            }
            row.addView(deleteButton,2)


            listTable.addView(row)
        }
    }
}