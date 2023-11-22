package uz.tune.databasesample.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.tune.databasesample.databinding.ItemBookBinding
import uz.tune.databasesample.databinding.ItemStudentBinding
import uz.tune.databasesample.databinding.ItemUserBinding
import uz.tune.databasesample.models.User
import uz.tune.databasesample.room.model.Book
import uz.tune.databasesample.room.model.Student

class StudentAdapter(val list: List<Student>, var callBack: CallBack) :
    RecyclerView.Adapter<StudentAdapter.StudentVH>() {

    inner class StudentVH(private var itemStudentBinding: ItemStudentBinding) :
        RecyclerView.ViewHolder(itemStudentBinding.root) {
        fun onBind(student: Student, position: Int) {
            itemStudentBinding.apply {
                tvName.text = "${student.firstName} ${student.lastName}"
                tvGroup.text = student.group

                btnDelete.setOnClickListener {
                    callBack.onStudentDelete(student, position = position)
                }

                btnEdit.setOnClickListener {
                    callBack.onStudentEdit(student, position)
                }

                root.setOnClickListener {
                    callBack.onStudentClick(student)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentVH {
        return StudentVH(
            ItemStudentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: StudentVH, position: Int) {
        holder.onBind(list[position], position)
    }

    public interface CallBack {
        fun onStudentDelete(student: Student, position: Int)

        fun onStudentEdit(student: Student, position: Int)

        fun onStudentClick(student: Student?)
    }
}