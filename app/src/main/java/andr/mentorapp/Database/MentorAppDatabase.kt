package andr.mentorapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import andr.mentorapp.Database.*

@Database(entities = arrayOf(User::class, TutorSchedule::class), exportSchema = false, version = 4)
abstract class MentorAppDatabase : RoomDatabase(){
    abstract fun userDao() : UserDao
    abstract fun tutorScheduleDao() : TutorScheduleDao

    companion object{
        var TEST_MODE = false
        @Volatile private var instance : MentorAppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = synchronized(LOCK){
            if (TEST_MODE) {
                instance = null
                instance ?: buildTestDatabase(context).also { instance = it }
            } else {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

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
                    db.execSQL("insert into user values ('android', 'Android', $TUTOR_LEVEL)")
                    db.execSQL("insert into user values ('manifest', 'Manifest', $TUTOR_LEVEL)")
                    db.execSQL("insert into user values ('is', 'Is', $TUTOR_LEVEL)")
                    db.execSQL("insert into user values ('stupid', 'Stupid', $TUTOR_LEVEL)")
                }
            })
            .allowMainThreadQueries()
            .build()

        private fun buildTestDatabase(context: Context) = Room.inMemoryDatabaseBuilder(context,
            MentorAppDatabase::class.java)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    }
}