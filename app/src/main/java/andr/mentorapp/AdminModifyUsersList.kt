package andr.mentorapp

import andr.mentorapp.Database.AdminUser
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_admin_modify_users_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import andr.mentorapp.Database.TutorUser
import android.app.Activity
import andr.mentorapp.Database.TUTOR_LEVEL
import andr.mentorapp.Database.ADMIN_LEVEL

const val GET_NEW_USER_RESULT : Int = 0
const val UPDATE_USER_RESULT: Int = 1

/**
 *  Admin List Screen
 *  creates the list of tutors that the admin can modify as needed
 *
 *  @author Max Xie, Pratik Bhusal
 *  @date 10/26/2019
 */
class AdminModifyUsersList : AppCompatActivity() {

    var database_level: Int = TUTOR_LEVEL

    /**
     *  creates the admin list screen
     *  @param savedInstanceState data most recently provided
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_modify_users_list) // set the layout to the particular xml file

        this.database_level = intent.getIntExtra("level", TUTOR_LEVEL)

        listMessage.text = "LIST" // set the text of the screen

        // set the add button to allow admin to add new tutors
        add_button.setOnClickListener {
            intent.setClass(this,AddUserActivity::class.java)
            startActivityForResult(intent,GET_NEW_USER_RESULT)
        }

        this.displayList()
    }

    /**
     *  code to create the list of tutors
     */
    fun displayList() {
        val context = this
        val db = MentorAppDatabase.invoke(context).userDao() // call the database

        // add a row containing an edit and delete button for each user in the list
        for (user in db.getUsersByLevel(database_level)) {
            val row = TableRow(context)
            val params = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT) // set the params for the Row
            val name = TextView(context)

            row.layoutParams = params
            name.text = user.userName // grab the name of the user
            row.addView(name, 0)

            val editButton = Button(context) // create the edit button for to modify the particular user
            editButton.text = "EDIT"
            editButton.setOnClickListener {
                intent = user.setIntent(this,EditUserActivity::class.java)
                intent.putExtra("id", user.userId)
                startActivityForResult(intent,UPDATE_USER_RESULT)
            }
            row.addView(editButton,1)

            val deleteButton = Button(context) // create the edit button to remove the particular user
            deleteButton.text = "DELETE"
            deleteButton.setOnClickListener {
                db.delete(user) // delete the user from the database
                recreate() // update the screen
            }
            row.addView(deleteButton,2)
            listTable.addView(row)
        }
    }

    /**
     *  function that is called from any results from the function [startActivityForResult] in this class
     *  @param requestCode code from the param in initialization of [startActivityForResult]
     *  @param resultCode code that is return from the finished execution of [startActivityForResult]
     *  @param data information received from the finished execution of [startActivityForResult]
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val context = this
        val db = MentorAppDatabase(context).userDao()
        if(requestCode == GET_NEW_USER_RESULT && resultCode == Activity.RESULT_OK && data != null){ // from the Add Button
            when (database_level) {
                TUTOR_LEVEL -> GlobalScope.launch {
                    db.insert(TutorUser(data.getStringExtra("id"), data.getStringExtra("name")))
                }
                ADMIN_LEVEL -> GlobalScope.launch {
                    db.insert(AdminUser(data.getStringExtra("id"), data.getStringExtra("name")))
                }
            }
        }
        else if (requestCode == UPDATE_USER_RESULT && resultCode == Activity.RESULT_OK && data != null) { // from the Edit Button
            // TODO: implement this code section
            // db.update(data.getStringExtra("id"), data.getStringExtra("name"))
        }
    }
}
