package com.example.kotlin.livedataroomretrofitkotlindemo.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.kotlin.livedataroomretrofitkotlindemo.network.User

/**
 * Created by tianlu on 2017/11/17.
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createUser(user: User)

    @Query("SELECT * FROM users")
    fun findAll(): List<User>

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM users where name = :name")
    fun findUsersByName(name: String): LiveData<List<User>>

    @Query("SELECT * FROM users where name = :name limit 1")
    fun findByName(name: String): LiveData<User>

    @Query("SELECT count(*) FROM users where name = :name")
    fun countByName(name: String): Int

}