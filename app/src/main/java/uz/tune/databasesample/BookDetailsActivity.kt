package uz.tune.databasesample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.tune.databasesample.databinding.ActivityBookDetailsBinding
import uz.tune.databasesample.room.AppDatabase

class BookDetailsActivity : AppCompatActivity() {
    private var database: AppDatabase? = null

    private lateinit var binding: ActivityBookDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", 0)

        database = AppDatabase.getInstance(this)

        binding.tvName.text = database?.bookDao()?.getBookById(id)?.name
    }
}