package uz.tune.databasesample.room.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val bookId: Int = 0,
    var name: String,
    val language: String,
    var price: Double,
    val publishedYear: Int,
    @Embedded(prefix = "author_")
    var author: Author
)