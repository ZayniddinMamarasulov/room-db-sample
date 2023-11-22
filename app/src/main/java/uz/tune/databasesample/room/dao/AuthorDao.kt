package uz.tune.databasesample.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import uz.tune.databasesample.room.model.Author
import uz.tune.databasesample.room.model.Book

@Dao
interface AuthorDao {

    @Query("select * from Authors")
    fun getAuthors(): List<Author>

    @Insert
    fun addAuthor(author: Author)

    @Delete
    fun deleteAuthor(author: Author)

    @Update
    fun updateAuthor(author: Author)

    @Query("select * from Authors where id = :id")
    fun getAuthorById(id: Int): Author
}