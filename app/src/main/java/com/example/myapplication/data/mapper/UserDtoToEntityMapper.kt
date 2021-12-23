package com.example.myapplication.data.mapper

import com.example.myapplication.data.dto.UserDto
import com.example.myapplication.domain.entity.people.User

internal class UserDtoToEntityMapper : (UserDto) -> (User) {

    override fun invoke(user: UserDto): User {
        return User(
            userId = user.id,
            userName = user.name,
            email = user.email,
            avatar = user.avatar
        )
    }

}