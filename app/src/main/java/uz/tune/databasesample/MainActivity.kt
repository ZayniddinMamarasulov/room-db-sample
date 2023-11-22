package uz.tune.databasesample

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import uz.tune.databasesample.databinding.ActivityMainBinding
import uz.tune.databasesample.databinding.ItemDialogEditBinding
import uz.tune.databasesample.models.User


class MainActivity : AppCompatActivity(), UserAdapter.CallBack {

    private var dbHelper: DBHelper? = null
    private lateinit var binding: ActivityMainBinding
    private var userAdapter: UserAdapter? = null
    private var usersList = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        dbHelper?.getUsers()?.let { usersList.addAll(it) }
        userAdapter = UserAdapter(usersList, callBack = this)

        binding.apply {
            recyclerView.adapter = userAdapter

            btnSave.setOnClickListener {
                val name = etName.text.toString()
                val age = etAge.text.toString()
                val phone = etPhone.text.toString()

                if (name.isNullOrEmpty().not()
                    && age.isNullOrEmpty().not()
                    && phone.isNullOrEmpty().not()
                ) {
                    val user = User(name = name, age = age.toInt(), phone = phone)
                    dbHelper?.addUser(user)

                    usersList.add(user)

                    userAdapter?.notifyDataSetChanged()

                } else {
                    Toast
                        .makeText(
                            baseContext,
                            "Barcha maydonni to'ldiring!!!",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
            }
        }
    }

    override fun onItemDelete(id: Int?, position: Int) {
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        dbHelper?.deleteUser(id)
                        usersList.removeAt(position)
                        userAdapter?.notifyItemRemoved(position)
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

    override fun onItemEdit(user: User, position: Int) {
        val dialog = AlertDialog.Builder(this).create()
        val itemDialogEditBinding = ItemDialogEditBinding.inflate(layoutInflater)
        dialog.setView(itemDialogEditBinding.root)

        itemDialogEditBinding.apply {
            etName.setText(user.name)
            etAge.setText(user.age.toString())
            etPhone.setText(user.phone)

            btnSave.setOnClickListener {
                user.name = etName.text.toString()
                user.age = etAge.text.toString().toInt()
                user.phone = etPhone.text.toString()
                dbHelper?.editUser(user)
                userAdapter?.notifyItemChanged(position)

                dialog.dismiss()
            }
        }

        dialog.show()
    }

    override fun onItemClick(id: Int?) {
        val user = dbHelper?.getUserById(id ?: 0)
        Toast.makeText(this, user?.name, Toast.LENGTH_SHORT).show()
    }
}