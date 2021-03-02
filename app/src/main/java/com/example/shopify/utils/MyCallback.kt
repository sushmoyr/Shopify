package com.example.shopify.utils

import com.example.shopify.data.model.User

interface MyCallback {
    fun onCallback(value: User)
}