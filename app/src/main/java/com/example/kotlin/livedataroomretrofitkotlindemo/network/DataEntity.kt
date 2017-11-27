package com.example.kotlin.livedataroomretrofitkotlindemo.network

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
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