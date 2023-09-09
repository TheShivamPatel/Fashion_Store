package com.example.shopmart.model

data class AllOrderModel(

    val brandName: String ?= " ",
    val orderId: String ?= " ",
    val productCoverImg: String ?= " ",
    val productId: String ?= " ",
    val productName: String ?= " ",
    val productSp: String ?= " ",
    val userId: String ?= " ",
    val status : String ?= " ",
    val orderedAt : String ?= " ",
)

