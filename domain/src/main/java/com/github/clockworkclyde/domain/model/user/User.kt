package com.github.clockworkclyde.domain.model.user

data class User(
   val id: Int? = null,
   val firstName: String? = null,
   val lastName: String? = null,
   val email: String = "",
   val pic: UserPhoto? = null
)