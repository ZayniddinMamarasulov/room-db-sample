package uz.tune.databasesample.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import uz.tune.databasesample.room.model.Book

@Dao
interface BookDao {

    @Query("select * from Books")
    fun getBooks(): List<Book>

    @Insert
    fun addBook(book: Book)

    @Delete
    fun deleteBook(book: Book)

    @Update
    fun updateBook(book: Book)

    @Query("select * from Books where bookId = :id")
    fun getBookById(id: Int): Book

    @Query("select * from Books where author_name = :authorName")
    fun getBookByAuthor(authorName: String): List<Book>
}