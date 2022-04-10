package com.pbussolutions.di

import com.pbussolutions.domain.data.repository.UserDataSourceImpl
import com.pbussolutions.domain.repository.UserDataSource
import com.pbussolutions.util.Constants.DATABASE_NAME
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val koinModule = module {
    single {
        KMongo.createClient()
            .coroutine
            .getDatabase(DATABASE_NAME)
    }
    single<UserDataSource> {
        UserDataSourceImpl(get())
    }
}