package com.example.kotlin.livedataroomretrofitkotlindemo.network

import android.arch.persistence.room.*
import java.util.*

/**
 * Created by tianlu on 2017/11/22.
 */

data class Contributor(var login : String,
                       var contributions : Long)

@Entity(tableName="users", indices=arrayOf(Index(name="index_name", value="name")))
data class User(@PrimaryKey @ColumnInfo(name = "name") var name : String,
                @ColumnInfo(name = "email")  var email : String?,
                @ColumnInfo(name = "type")  var type : String?,
                @ColumnInfo(name = "company")  var company : String?,
                @ColumnInfo(name = "location")  var location : String?,
                @ColumnInfo(name = "created_at")  var created_at : Date?)

@Entity(tableName="subscriptions", indices=arrayOf(Index(name="index_username", value="username")))
data class Subscription(@PrimaryKey @ColumnInfo(name="id") var id:Int,
                        @ColumnInfo(name="owner_id") var ownerId:Int?,
                        @Ignore var owner : Owner?,
                        @ColumnInfo(name = "name")  var name : String?,
                        @ColumnInfo(name = "username")  var username : String?,
                        @ColumnInfo(name = "full_name")  var full_name : String?,
                        @ColumnInfo(name = "description")  var description : String?,
                        @ColumnInfo(name = "url")  var  url: String?){
    constructor() : this(-1, -1, null, "", "", "", "", "")
}

@Entity(tableName="owners", indices=arrayOf(Index(name="index_login", value="login")))
data class Owner(@PrimaryKey @ColumnInfo(name="id") var id:Int,
                 @ColumnInfo(name = "login")  var login : String?,
                 @ColumnInfo(name = "url")  var url : String?)

data class SubscriptionWithOwner(@Embedded var subscription : Subscription?,
                                 @Relation(parentColumn = "owner_id", entityColumn = "id", entity=Owner::class) var owner : List<Owner>?){
    constructor() : this(null, null)
}