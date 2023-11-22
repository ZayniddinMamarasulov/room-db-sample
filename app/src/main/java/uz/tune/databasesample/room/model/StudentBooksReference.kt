package uz.tune.databasesample.room.model

import androidx.room.Entity


@Entity(primaryKeys = ["studentId", "bookId"])
data class StudentBooksReference(
    var studentId: Int,
    var bookId: Int
)