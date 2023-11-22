package uz.tune.databasesample

import android.R
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import uz.tune.databasesample.databinding.ActivityRoomBinding
import uz.tune.databasesample.databinding.ItemDialogAuthorAddBinding
import uz.tune.databasesample.databinding.ItemDialogBookAddBinding
import uz.tune.databasesample.databinding.ItemDialogBookEditBinding
import uz.tune.databasesample.room.AppDatabase
import uz.tune.databasesample.room.BookAdapter
import uz.tune.databasesample.room.model.Author
import uz.tune.databasesample.room.model.Book

class RoomActivity : AppCompatActivity(), BookAdapter.CallBack {

    private var database: AppDatabase? = null
    private var bookAdapter: BookAdapter? = null
    private lateinit var binding: ActivityRoomBinding

    private var bookList = mutableListOf<Book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)

        bookList.clear()
        database?.bookDao()?.getBooks()?.let { bookList.addAll(it) }
        bookAdapter = BookAdapter(bookList, callBack = this)

        binding.recyclerView.adapter = bookAdapter

        binding.btnAddBook.setOnClickListener {
            openAddBookDialog()
        }

        binding.btnAddAuthor.setOnClickListener {
            openAuthorAddDialog()
        }

        binding.btnAuthors.setOnClickListener {
            val intent = Intent(this@RoomActivity, AuthorsActivity::class.java)
            startActivity(intent)
        }

        binding.btnStudents.setOnClickListener {
            val intent = Intent(this@RoomActivity, StudentActivity::class.java)
            startActivity(intent)
        }

        binding.btnRentBook.setOnClickListener {
            val intent = Intent(this@RoomActivity, RentBooksActivity::class.java)
            startActivity(intent)
        }

        // syntactic sugar
    }

    override fun onItemDelete(book: Book, position: Int) {
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        database?.bookDao()?.deleteBook(book)
                        bookList.removeAt(position)
                        bookAdapter?.notifyItemRemoved(position)
                        dialog.dismiss()
                    }

                    DialogInterface.BUTTON_NEGATIVE -> {
                        dialog.dismiss()
                    }
                }
            }

        val builder = AlertDialog.Builder(this)
        builder
            .setMessage("Are you sure?")
            .setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener)
            .show()
    }

    override fun onItemEdit(book: Book, position: Int) {
        val dialog = AlertDialog.Builder(this).create()
        val itemDialogEditBinding = ItemDialogBookEditBinding.inflate(layoutInflater)
        dialog.setView(itemDialogEditBinding.root)

        itemDialogEditBinding.apply {
            etName.setText(book.name)
//            etAuthor.setText(user.age.toString())
            etPrice.setText(book.price.toString())

            btnSave.setOnClickListener {
                book.name = etName.text.toString()
                book.author = Author(name = "", city = "")
                book.price = etPrice.text.toString().toDouble()
                database?.bookDao()?.updateBook(book)
                bookAdapter?.notifyItemChanged(position)

                dialog.dismiss()
            }
        }

        dialog.show()
    }

    override fun onItemClick(id: Int?) {
        val intent = Intent(this, BookDetailsActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun openAddBookDialog() {
        val dialog = AlertDialog.Builder(this).create()
        val itemDialogBookAddBinding = ItemDialogBookAddBinding.inflate(layoutInflater)
        dialog.setView(itemDialogBookAddBinding.root)

        itemDialogBookAddBinding.apply {

            initAuthorSpinner(itemDialogBookAddBinding)

            btnSave.setOnClickListener {

                val name = etName.text.toString()
                val author = spinnerAuthor.selectedItem as Author
                val price = etPrice.text.toString()

                if (name.isNullOrEmpty().not()
                    && price.isNullOrEmpty().not()
                ) {
                    val book =
                        Book(
                            name = name,
                            price = price.toDouble(),
                            language = "uzbek",
                            publishedYear = 2023,
                            author = author
                        )
                    database?.bookDao()?.addBook(book)

                    bookList.add(book)
                    bookAdapter?.notifyDataSetChanged()

                    dialog.dismiss()
                }
            }
            dialog.show()
        }
    }

    private fun openAuthorAddDialog() {
        val dialog = AlertDialog.Builder(this).create()
        val itemDialogAuthorAddBinding = ItemDialogAuthorAddBinding.inflate(layoutInflater)
        dialog.setView(itemDialogAuthorAddBinding.root)

        itemDialogAuthorAddBinding.apply {

            btnSave.setOnClickListener {

                val name = etName.text.toString()
                val city = etCity.text.toString()

                if (name.isNullOrEmpty().not()
                    && name.isNullOrEmpty().not()
                    && city.isNullOrEmpty().not()
                ) {
                    val author = Author(name = name, city = city)

                    database?.authorDao()?.addAuthor(author)
                    bookAdapter?.notifyDataSetChanged()

                    dialog.dismiss()
                }
            }

            dialog.show()
        }
    }

    private fun getAuthorsName(): ArrayList<Author> {
        val customObjects = ArrayList<Author>()
        database?.authorDao()?.getAuthors()?.let { customObjects.addAll(it) }
        return customObjects
    }

    private fun initAuthorSpinner(binding: ItemDialogBookAddBinding) {
        val authorsName = getAuthorsName()

        val spinnerAdapter = ArrayAdapter(
            this@RoomActivity,
            R.layout.simple_list_item_1,
            authorsName
        )

        binding.spinnerAuthor.adapter = spinnerAdapter

        binding.spinnerAuthor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // You can define your actions as you want
            }

            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                position: Int,
                p3: Long
            ) {

                val selectedObject = binding.spinnerAuthor.selectedItem as Author

//                Toast.makeText(
//                    this@RoomActivity,
//                    "ID: ${selectedObject.id} Name: ${selectedObject.name}",
//                    Toast.LENGTH_SHORT
//                ).show()

            }
        }
    }
}