package com.example.kotlin.livedataroomretrofitkotlindemo.network

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull

/**
 * Created by tianlu on 2017/11/22.
 */

data class Contributor(var login : String,
                       var contributions : Long)

@Entity(tableName="users", indices=arrayOf(Index(name="index_name", value="name")))
data class User(@PrimaryKey @NotNull @ColumnInfo(name = "name") var name : String,
                @ColumnInfo(name = "email") var email : String)