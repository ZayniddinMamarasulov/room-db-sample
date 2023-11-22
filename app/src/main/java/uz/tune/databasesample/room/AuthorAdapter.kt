package uz.tune.databasesample.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.tune.databasesample.databinding.ItemAuthorBinding
import uz.tune.databasesample.databinding.ItemBookBinding
import uz.tune.databasesample.databinding.ItemUserBinding
import uz.tune.databasesample.models.User
import uz.tune.databasesample.room.model.Author
import uz.tune.databasesample.room.model.Book

class AuthorAdapter(val list: List<Author>, var callBack: CallBack) :
    RecyclerView.Adapter<AuthorAdapter.AuthorVH>() {

    inner class AuthorVH(private var itemAuthorBinding: ItemAuthorBinding) :
        RecyclerView.ViewHolder(itemAuthorBinding.root) {
        fun onBind(author: Author, position: Int) {
            itemAuthorBinding.apply {
                tvName.text = author.name
                tvCity.text = author.city

                btnDelete.setOnClickListener {
                    callBack.onItemDelete(author, position = position)
                }

                btnEdit.setOnClickListener {
                    callBack.onItemEdit(author, position)
                }

                root.setOnClickListener {
                    callBack.onItemClick(author.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorVH {
        return AuthorVH(
            ItemAuthorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AuthorVH, position: Int) {
        holder.onBind(list[position], position)
    }

    public interface CallBack {
        fun onItemDelete(book: Author, position: Int)

        fun onItemEdit(book: Author, position: Int)

        fun onItemClick(id: Int?)
    }
}