package andr.mentorapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import andr.mentorapp.Database.*

/**
 * Creates the database used by the app using Room
 *
 * @author Mugdha Gupta
 * @date 9/28/19
 */
@Database(entities = arrayOf(User::class, TutorSchedule::class), exportSchema = false, version = 4)
abstract class MentorAppDatabase : RoomDatabase(){
    /**
     * Returns User Data Access Object for the db
     *
     * @return UserDao  the object used to access User entity
     */
    abstract fun userDao() : UserDao

    /**
     * Returns Tutor Schedule Data Access Object for the db
     *
     * @return UserDao  the object used to access TutorSchedule entity
     */
    abstract fun tutorScheduleDao() : TutorScheduleDao

    companion object{
        var TEST_MODE = false
        @Volatile private var instance : MentorAppDatabase? = null
        private val LOCK = Any()

        /**
         * Returns the existing db or creates a db depending on test mode
         *
         * @param context               Context used to build db
         * @return MentorAppDatabase    the built db
         */
        operator fun invoke(context: Context) = synchronized(LOCK){
            if (TEST_MODE) {
                instance = null
                instance ?: buildTestDatabase(context).also { instance = it }
            } else {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        /**
         * Builds the existing db to be used by the app
         *
         * @param context               Context used to build db
         * @return MentorAppDatabase    the built db
         */
        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            MentorAppDatabase::class.java, "mentor-app.db")
            .fallbackToDestructiveMigration()
            .addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    // insert initial data into the database
                    db.execSQL("insert into user values ('nymisha', 'Nymisha', $ADMIN_LEVEL)")
                    db.execSQL("insert into user values ('mugdha', 'Mugdha', $STUDENT_LEVEL)")
                    db.execSQL("insert into user values ('courtney', 'Courtney', $TUTOR_LEVEL)")
                    db.execSQL("insert into user values ('mila', 'Mila', $TUTOR_LEVEL)")
                    db.execSQL("insert into tutor_schedule values ('courtney', 'Sunday', '12:00:00', '13:30:00')")
                    db.execSQL("insert into tutor_schedule values ('courtney', 'Sunday', '13:30:00', '15:00:00')")
                    db.execSQL("insert into tutor_schedule values ('mila', 'Monday', '10:00:00', '11:30:00')")
                    db.execSQL("insert into user values ('android', 'Android', $TUTOR_LEVEL)")
                    db.execSQL("insert into user values ('manifest', 'Manifest', $TUTOR_LEVEL)")
                }
            })
            .allowMainThreadQueries()
            .build()

        /**
         * Returns the existing db to be used for testing
         *
         * @param context               Context used to build db
         * @return MentorAppDatabase    the built db
         */
        private fun buildTestDatabase(context: Context) = Room.inMemoryDatabaseBuilder(context,
            MentorAppDatabase::class.java)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    }
}
