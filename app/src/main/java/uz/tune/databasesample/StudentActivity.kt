package uz.tune.databasesample

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import uz.tune.databasesample.databinding.ActivityStudentBinding
import uz.tune.databasesample.databinding.ItemDialogBookAddBinding
import uz.tune.databasesample.databinding.ItemDialogStudentAddBinding
import uz.tune.databasesample.room.AppDatabase
import uz.tune.databasesample.room.BookAdapter
import uz.tune.databasesample.room.StudentAdapter
import uz.tune.databasesample.room.model.Author
import uz.tune.databasesample.room.model.Book
import uz.tune.databasesample.room.model.Student

class StudentActivity : AppCompatActivity(), StudentAdapter.CallBack {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityStudentBinding

    private var database: AppDatabase? = null
    private var studentList = mutableListOf<Student>()
    private var studentAdapter: StudentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)
        studentList.clear()

        database?.studentDao()?.getStudents()?.let { studentlar ->
            // agar getStudents null bo'lmasa bu kodni ichi ishlaydi
            studentList.addAll(studentlar)
        }

        studentAdapter = StudentAdapter(studentList, callBack = this)

        binding.recyclerView.adapter = studentAdapter

        binding.btnAddStudent.setOnClickListener { view ->
            openAddStudentDialog()
        }
    }

    private fun openAddStudentDialog() {
        val dialog = AlertDialog.Builder(this).create()
        val itemDialogStudentAddBinding = ItemDialogStudentAddBinding.inflate(layoutInflater)
        dialog.setView(itemDialogStudentAddBinding.root)

        itemDialogStudentAddBinding.apply {

            btnSave.setOnClickListener {

                val firstName = etFirstName.text.toString()
                val lastName = etLastName.text.toString()
                val group = etGroup.text.toString()

                if (firstName.isNullOrEmpty().not()
                    && lastName.isNullOrEmpty().not()
                    && group.isNullOrEmpty().not()
                ) {

                    val student = Student(
                        firstName = firstName,
                        lastName = lastName,
                        group = group
                    )


                    database?.studentDao()?.addStudent(student)

                    studentList.add(student)
                    studentAdapter?.notifyDataSetChanged()

                    dialog.dismiss()
                }
            }
            dialog.show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_student)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onStudentDelete(student: Student, position: Int) {

    }

    override fun onStudentEdit(student: Student, position: Int) {
    }

    override fun onStudentClick(student: Student?) {
    }
}