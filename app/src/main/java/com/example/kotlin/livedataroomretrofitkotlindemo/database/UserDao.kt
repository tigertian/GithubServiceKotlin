package com.example.kotlin.livedataroomretrofitkotlindemo.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.kotlin.livedataroomretrofitkotlindemo.network.Owner
import com.example.kotlin.livedataroomretrofitkotlindemo.network.Subscription
import com.example.kotlin.livedataroomretrofitkotlindemo.network.User

/**
 * Created by tianlu on 2017/11/17.
 */
@Dao
interface UserDao {

    //Users

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createUser(user: User)

    @Query("SELECT * FROM users")
    fun findAllUsers(): List<User>

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM users where name = :name")
    fun findUsersByName(name: String): LiveData<List<User>>

    @Query("SELECT * FROM users where name = :name limit 1")
    fun findUserByName(name: String): User?

    //Subscriptions

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createSubscription(sub: Subscription)

    @Query("SELECT * FROM subscriptions")
    fun findAllSubcriptions(): List<Subscription>

    @Update
    fun updateSubscription(sub: Subscription)

    @Delete
    fun deleteSubscription(sub: Subscription)

    @Query("SELECT * FROM subscriptions where username = :username")
    fun findSubscriptionsByUsername(username : String): List<Subscription>

    @Query("SELECT * FROM subscriptions where id = :id limit 1")
    fun findSubscriptionById(id : Int): Subscription?

    //Owners

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createOwner(owner: Owner)

    @Query("SELECT * FROM owners")
    fun findAllOwners(): List<Owner>

    @Update
    fun updateOwner(owner: Owner)

    @Delete
    fun deleteOwner(owner: Owner)

    @Query("SELECT * FROM owners where id = :id")
    fun findOwnerById(id : Int): Owner?

}