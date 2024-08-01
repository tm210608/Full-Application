package com.mito.network.dummy_login.domain

data class User(
    val name: String,
    val email: String,
    val password: String,
    val phone: String? = null,
    val address: String? = null,
    val city: String? = null,
    val state: String? = null,
    val zip: String? = null,
    val photo: String? = null
)
