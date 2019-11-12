package andr.mentorapp

import android.content.Context
import android.content.Intent

/**
 * This class encompasses the Strategy for viewing schedules by a Tutor
 *
 * @author Courtney Erbes
 * @date 11/08/19
 */
class TutorViewScheduleStrategy : IViewScheduleStrategy() {
    /**
     *  Generates intent for a Tutor to use to view schedules
     *
     *  @param context      Context needed to create the intent
     *  @param intent       Intent needed to edit
     *  @return intent      Intent used by Tutor
     */
    @Override
    override fun generateIntent(context: Context, intent: Intent) : Intent {
        intent.setClass(context, TutorScheduleActivity::class.java)
        intent.putExtra("tutorId", intent.getStringExtra("id"))
        intent.putExtra("tutorName", intent.getStringExtra("name"))

        return intent
    }
}
