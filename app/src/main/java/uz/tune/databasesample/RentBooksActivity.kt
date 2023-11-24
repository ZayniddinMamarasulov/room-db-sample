package uz.tune.databasesample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.tune.databasesample.room.AppDatabase
import uz.tune.databasesample.room.model.Author
import uz.tune.databasesample.room.model.Book
import uz.tune.databasesample.room.model.Student
import uz.tune.databasesample.room.model.StudentBooksReference
import uz.tune.databasesample.room.model.StudentsAndBooks

class RentBooksActivity : AppCompatActivity() {

    private var database: AppDatabase? = null

    private var bookList: List<Book>? = emptyList()
    private var studentList: List<Student>? = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_books)

        database = AppDatabase.getInstance(this)

        bookList = database?.bookDao()?.getBooks()
        studentList = database?.studentDao()?.getStudents()

        try {
            val studentsAndBooksReference =
                studentList?.get(0)?.studentId?.let {
                    bookList?.get(0)?.bookId?.let { it1 ->
                        StudentBooksReference(
                            it,
                            it1
                        )
                    }
                }
            if (studentsAndBooksReference != null) {
                database?.studentDao()?.insertStudentBookCrossRef(studentsAndBooksReference)
            }
        } catch (e: Exception) {

        }

    }
}