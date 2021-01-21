package andr.mentorapp

import android.content.Context
import android.content.Intent

/**
 * This class encompasses the Strategy for viewing schedules by an Admin
 *
 * @author Courtney Erbes
 * @date 11/08/19
 */
class AdminViewScheduleStrategy : IViewScheduleStrategy() {
    /**
     *  Generates intent for an Admin to use to view schedules
     *
     *  @param context      Context needed to create the intent
     *  @param intent       Intent needed to edit
     *  @return intent      Intent used by Admin
     */
    @Override
    override fun generateIntent(context: Context, intent: Intent) : Intent {
        intent.setClass(context, TutorListActivity::class.java)

        return intent
    }
}
