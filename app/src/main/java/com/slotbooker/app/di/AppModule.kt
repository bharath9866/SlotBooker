package com.slotbooker.app.di

import com.slotbooker.app.data.remote.ApiService
import com.slotbooker.app.data.repository.BookingRepositoryImpl
import com.slotbooker.app.domain.repository.BookingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBookingRepository(api: ApiService): BookingRepository =
        BookingRepositoryImpl(api)
}