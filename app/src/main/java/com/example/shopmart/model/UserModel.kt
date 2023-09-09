package com.example.shopmart.model

data class UserModel(
    val name : String? = "",
    val number : String? ="",
    val favouriteList : ArrayList<String> = ArrayList() ,
    val shoppingBag : ArrayList<String> = ArrayList() ,
    val pinCode : String?= "",
    val address : String? = "",
    val city : String? = "",
    val state : String? ="",
    val locality : String? = ""
)