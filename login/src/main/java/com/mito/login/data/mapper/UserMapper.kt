package com.mito.login.data.mapper

import com.mito.database.data.entity.UserEntity
import com.mito.network.dummy_login.domain.Gender
import com.mito.network.dummy_login.domain.User

fun User.toEntity(): UserEntity {
    return UserEntity(
        name = name,
        email = email,
        password = password,
        phone = phone,
        address = address,
        city = city,
        state = state,
        zip = zip,
        photo = photo,
        birthDate = birthday,
        gender = gender?.name
    )
}

fun UserEntity.toDomain(): User{
    return User(
        name = name,
        email = email,
        password = password,
        phone = phone,
        address = address,
        city = city,
        state = state,
        zip = zip,
        photo = photo,
        birthday = birthDate,
        gender = gender?.let { Gender.valueOf(it) }
    )
}