package uz.tune.databasesample.room.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Students")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val studentId: Int = 0,
    var firstName: String,
    var lastName: String,
    var group: String
)