package andr.mentorapp

import andr.mentorapp.Database.*
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_admin_modify_users_list.*
import android.app.Activity
import android.view.View

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
     *
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

        undo_button.setVisibility(View.GONE)
        undo_button.setOnClickListener {
            undo_button.setVisibility(View.GONE)
            DatabaseManager.restoreLastUser()
            recreate()
        }


        this.displayList()
    }

    /**
     * Delete leftover memento when page is destroyed
     */
    override fun onDestroy() {
        super.onDestroy()
        DatabaseManager.clearHistory()
    }

    /**
     *  code to create the list of tutors
     */
    protected fun displayList() {
        val context = this
        var users = if (database_level == TUTOR_LEVEL)
                        DatabaseManager.getAllTutors()
                    else
                        DatabaseManager.getAllAdmins()

        // add a row containing an edit and delete button for each user in the list
        for (user in users) {
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
                intent.putExtra("level", if (TUTOR_LEVEL == database_level) TUTOR_LEVEL else ADMIN_LEVEL)

                startActivityForResult(intent,UPDATE_USER_RESULT)
            }
            row.addView(editButton,1)

            val deleteButton = Button(context) // create the edit button to remove the particular user
            deleteButton.text = "DELETE"
            deleteButton.setOnClickListener {
                DatabaseManager.deleteUser(user) // delete the user from the database
                undo_button.setVisibility(View.VISIBLE)
                recreate() // update the screen
            }
            row.addView(deleteButton,2)
            listTable.addView(row)
        }
    }

    /**
     *  override the method to update the screen after certain actions
     */
    override fun recreate() {
        listTable.removeAllViews()
        this.displayList()
    }

    /**
     *  function that is called from any results from the function [startActivityForResult] in this class
     *  @param requestCode code from the param in initialization of [startActivityForResult]
     *  @param resultCode code that is return from the finished execution of [startActivityForResult]
     *  @param data information received from the finished execution of [startActivityForResult]
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == GET_NEW_USER_RESULT && data != null){ // from the Add Button
            when (database_level) {
                TUTOR_LEVEL -> {
                    DatabaseManager.insertUser(TutorUser(data.getStringExtra("id"), data.getStringExtra("name")))
                }
                ADMIN_LEVEL -> {
                    DatabaseManager.insertUser(AdminUser(data.getStringExtra("id"), data.getStringExtra("name")))
                }
            }
        }

        if (resultCode == Activity.RESULT_OK) {
            recreate()
        }
    }
}
