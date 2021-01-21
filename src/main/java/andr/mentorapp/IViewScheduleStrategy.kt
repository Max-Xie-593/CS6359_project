package andr.mentorapp

import android.content.Context
import android.content.Intent

/**
 * This class encompasses the interface for Strategies of viewing schedules by User types
 *
 * @author Courtney Erbes
 * @date 11/08/19
 */
abstract class IViewScheduleStrategy {
    /**
     *  Generates intent for a User to view schedules
     *
     *  @param context      Context needed to create the intent
     *  @param intent       Intent needed to edit
     *  @return intent      Intent used by User
     */
    abstract fun generateIntent(context: Context, intent: Intent) : Intent
}
