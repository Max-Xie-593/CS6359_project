package andr.mentorapp

import android.content.Context
import android.content.Intent

/**
 * This class allows different users to call different View Schedule Strategies
 *
 * @author Courtney Erbes
 * @date 11/08/19
 */
class ViewScheduleIntent {
    var vsStrategy: IViewScheduleStrategy = TutorViewScheduleStrategy()

    constructor(strategy: IViewScheduleStrategy) {
        vsStrategy = strategy
    }

    /**
     *  Calls generating intent func for a User to view schedules
     *
     *  @param context      Context needed to create the intent
     *  @param intent       Intent needed to edit
     *  @return intent      Intent used by User
     */
    fun generateIntent(context: Context, intent: Intent) : Intent {
        return vsStrategy.generateIntent(context, intent)
    }
}
