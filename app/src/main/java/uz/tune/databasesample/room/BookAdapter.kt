package uz.tune.databasesample.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.tune.databasesample.databinding.ItemBookBinding
import uz.tune.databasesample.databinding.ItemUserBinding
import uz.tune.databasesample.models.User
import uz.tune.databasesample.room.model.Book

class BookAdapter(val list: List<Book>, var callBack: CallBack) :
    RecyclerView.Adapter<BookAdapter.BookVH>() {

    inner class BookVH(private var itemBookBinding: ItemBookBinding) :
        RecyclerView.ViewHolder(itemBookBinding.root) {
        fun onBind(book: Book, position: Int) {
            itemBookBinding.apply {
                tvName.text = book.name
                tvAuthor.text = book.author.name.toString()
                tvPrice.text = book.price.toString() + " UZS"

                btnDelete.setOnClickListener {
                    callBack.onItemDelete(book, position = position)
                }

                btnEdit.setOnClickListener {
                    callBack.onItemEdit(book, position)
                }

                root.setOnClickListener {
//                    callBack.onItemClick(book)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookVH {
        return BookVH(
            ItemBookBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BookVH, position: Int) {
        holder.onBind(list[position], position)
    }

    public interface CallBack {
        fun onItemDelete(book: Book, position: Int)

        fun onItemEdit(book: Book, position: Int)

        fun onItemClick(id: Int?)
    }
}