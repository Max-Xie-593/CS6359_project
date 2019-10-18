package andr.mentorapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = arrayOf(User::class), exportSchema = false, version = 4)
abstract class MentorAppDatabase : RoomDatabase(){
    abstract fun userDao() : UserDao

    companion object{
        @Volatile private var instance : MentorAppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            MentorAppDatabase::class.java, "mentor-app.db")
            .fallbackToDestructiveMigration()
            .addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.execSQL("insert into user values ('nymisha', 'Nymisha', $ADMIN_LEVEL)")
                    db.execSQL("insert into user values ('mugdha', 'Mugdha', $STUDENT_LEVEL)")
                    db.execSQL("insert into user values ('courtney', 'Courtney', $TUTOR_LEVEL)")

                }
            })
            .build()

    }
}