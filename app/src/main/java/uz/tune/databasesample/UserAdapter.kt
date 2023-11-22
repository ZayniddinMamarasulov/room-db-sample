package uz.tune.databasesample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.tune.databasesample.databinding.ItemUserBinding
import uz.tune.databasesample.models.User

class UserAdapter(val list: List<User>, var callBack: CallBack) :
    RecyclerView.Adapter<UserAdapter.UserVH>() {

    inner class UserVH(var itemUserBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {
        fun onBind(user: User, position: Int) {
            itemUserBinding.apply {
                tvName.text = user.name
                tvAge.text = user.age.toString()
                tvPhone.text = user.phone

                btnDelete.setOnClickListener {
                    callBack.onItemDelete(user.id, position = position)
                }

                btnEdit.setOnClickListener {
                    callBack.onItemEdit(user, position)
                }

                root.setOnClickListener {
                    callBack.onItemClick(user.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        return UserVH(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        holder.onBind(list[position], position)
    }

    public interface CallBack {
        fun onItemDelete(id: Int?, position: Int)

        fun onItemEdit(user: User, position: Int)

        fun onItemClick(id: Int?)
    }
}