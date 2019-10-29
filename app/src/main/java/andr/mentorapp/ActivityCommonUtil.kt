package andr.mentorapp

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.util.*
import kotlin.collections.HashSet

var checkedInTutors = HashSet<String>()
var availableTutors = HashSet<String>()
var studentQueue: Queue<String> = ArrayDeque<String>()
var tutorSessions = HashSet<StudentTutorMatch>()

fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(frameId: Int, fragment: Fragment){
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

class ActivityCommonUtil {

}