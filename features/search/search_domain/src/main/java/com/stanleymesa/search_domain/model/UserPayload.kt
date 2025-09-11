package com.stanleymesa.search_domain.model

import java.util.UUID

data class UserPayload(
    val search: String = "",
    val uuid: UUID = UUID.randomUUID()
)
