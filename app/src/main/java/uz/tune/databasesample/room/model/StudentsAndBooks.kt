package uz.tune.databasesample.room.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

data class StudentsAndBooks(
    @Embedded val student: Student,
    @Relation(
        parentColumn = "studentId",
        entityColumn = "bookId",
//        associateBy = Junction(StudentBooksReference::class)
    )
    val book: Book
)