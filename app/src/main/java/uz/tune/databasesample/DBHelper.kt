package uz.tune.databasesample

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uz.tune.databasesample.models.User
import uz.tune.databasesample.services.DBService

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION), DBService {

    companion object {
        const val DB_NAME = "android36"
        const val DB_VERSION = 2

        const val TABLE_NAME = "users"
        const val USER_ID = "id"
        const val USER_NAME = "name"
        const val USER_AGE = "age"
        const val USER_NUMBER = "number"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val query =
            "CREATE TABLE $TABLE_NAME($USER_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, $USER_NAME TEXT NOT NULL, $USER_AGE INTEGER NOT NULL, $USER_NUMBER TEXT)"
        p0?.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
//        "table bo'yicha yangi o'zgarish shu yerda yoziladi"
        val newQuery = "ALTER TABLE users ADD age INTEGER"
        p0?.execSQL(newQuery)
    }

    override fun addUser(user: User) {
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(USER_NAME, user.name)
        contentValues.put(USER_AGE, user.age)
        contentValues.put(USER_NUMBER, user.phone)

        database.insert(TABLE_NAME, null, contentValues)
    }

    override fun getUsers(): List<User> {
        var list = ArrayList<User>()
        val database = this.readableDatabase
        val query = "SElECT * FROM $TABLE_NAME"
        val cursor = database.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val age = cursor.getInt(2)
                val phone = cursor.getString(3)

                val user = User(id = id, name = name, age = age, phone = phone)
                list.add(user)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun getUserById(id: Int): User {
        val database = this.readableDatabase
        val cursor = database.query(
            TABLE_NAME,
            arrayOf(USER_ID, USER_NAME, USER_AGE, USER_NUMBER),
            "$USER_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        cursor.moveToFirst()
        return User(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3))
    }

    override fun deleteUser(id: Int?) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$USER_ID = ?", arrayOf(id.toString()))

        database.close()
    }

    override fun editUser(user: User) {
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(USER_NAME, user.name)
        contentValues.put(USER_AGE, user.age)
        contentValues.put(USER_NUMBER, user.phone)

        database.update(
            TABLE_NAME,
            contentValues,
            "$USER_ID=?",
            arrayOf(user.id.toString())
        )
    }
}