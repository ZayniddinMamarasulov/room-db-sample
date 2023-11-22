package uz.tune.databasesample.services

import uz.tune.databasesample.models.User

interface DBService {
    fun addUser(user: User)

    fun getUsers(): List<User>

    fun getUserById(id: Int): User

    fun deleteUser(id: Int?)

    fun editUser(user: User)
}

// CRUD
// C - Create
// R - Read
// U - Update
// D - Delete