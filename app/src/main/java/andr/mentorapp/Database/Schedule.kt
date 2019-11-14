package andr.mentorapp.Database

/**
 * Class for representing data relating to a Tutor's Schedule
 *
 * @author Courtney Erbes
 * @date 11/11/19
 */
class Schedule(
    var day : String, // Day of the Tutor's shift

    var shiftStart : String, // Start time of the Tutor's shift in format "hh:mm:ss"

    var shiftEnd : String // End time of the Tutor's shift in format "hh:mm:ss"
)