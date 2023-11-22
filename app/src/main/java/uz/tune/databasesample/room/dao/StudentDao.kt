package uz.tune.databasesample.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import uz.tune.databasesample.room.model.Book
import uz.tune.databasesample.room.model.Student
import uz.tune.databasesample.room.model.StudentBooksReference
import uz.tune.databasesample.room.model.StudentsAndBooks

@Dao
interface StudentDao {

    @Query("select * from Students")
    fun getStudents(): List<Student>

    @Insert
    fun addStudent(student: Student)

    @Delete
    fun deleteStudent(student: Student)

    @Update
    fun updateStudent(student: Student)

    @Query("select * from Students where studentId = :id")
    fun getStudentById(id: Int): Student

    @Query("select * from Students where firstName = :group")
    fun getStudentsByGroup(group: String): List<Student>

    @Transaction
    @Query("select * from Students")
    fun getStudentAndBooks(): List<StudentsAndBooks>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudentBookCrossRef(crossRef: StudentBooksReference)

    @Query("select * from StudentBooksReference")
    fun getStudentBookCrossRef(): List<StudentBooksReference>
}