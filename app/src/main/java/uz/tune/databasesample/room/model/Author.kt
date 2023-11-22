package uz.tune.databasesample.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Authors")
data class Author(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    var city: String
) {

    override fun toString(): String {
        return name
    }
}