package com.sweet.iva.core.stream.user

import com.sweet.arch.core.domain.infra.stream.Stream
import com.sweet.arch.core.domain.model.user.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserStream : Stream<User> {
    override fun getFlow(): Flow<User> {
        return flow {

        }
    }
}