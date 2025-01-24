package com.mito.common.tools

enum class Gender(private val displayGender: String) {
    MALE("Male"),
    FEMALE("Female");

    override fun toString(): String {
        return displayGender
    }
}