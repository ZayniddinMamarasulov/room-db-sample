package uz.tune.databasesample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.tune.databasesample.databinding.ActivityAuthorsBinding
import uz.tune.databasesample.databinding.ActivityRoomBinding
import uz.tune.databasesample.room.AppDatabase
import uz.tune.databasesample.room.AuthorAdapter
import uz.tune.databasesample.room.BookAdapter
import uz.tune.databasesample.room.model.Author
import uz.tune.databasesample.room.model.Book

class AuthorsActivity : AppCompatActivity(), AuthorAdapter.CallBack {

    private var database: AppDatabase? = null
    private var authorAdapter: AuthorAdapter? = null
    private lateinit var binding: ActivityAuthorsBinding

    private var authorList = mutableListOf<Author>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)

        authorList.clear()
        database?.authorDao()?.getAuthors()?.let { authorList.addAll(it) }
        authorAdapter = AuthorAdapter(authorList, callBack = this)

        binding.apply {
            recyclerView.adapter = authorAdapter
        }
    }

    override fun onItemDelete(book: Author, position: Int) {

    }

    override fun onItemEdit(book: Author, position: Int) {
    }

    override fun onItemClick(id: Int?) {
    }


}