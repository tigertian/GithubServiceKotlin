package com.example.kotlin.livedataroomretrofitkotlindemo.network

/**
 * Created by tianlu on 2017/11/22.
 */

data class Contributor(var login : String,
                       var contributions : Long)

data class User(var name : String,
                var email : String)