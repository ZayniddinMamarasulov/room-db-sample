package uz.tune.databasesample.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.tune.databasesample.room.dao.AuthorDao
import uz.tune.databasesample.room.dao.BookDao
import uz.tune.databasesample.room.dao.StudentDao
import uz.tune.databasesample.room.model.Author
import uz.tune.databasesample.room.model.Book
import uz.tune.databasesample.room.model.Student
import uz.tune.databasesample.room.model.StudentBooksReference


@Database(
    entities = [Book::class, Author::class, Student::class, StudentBooksReference::class],
    version = 12
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao
    abstract fun authorDao(): AuthorDao
    abstract fun studentDao(): StudentDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "kutubxona_db"
                )
//                    .createFromAsset("kutubxona_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}