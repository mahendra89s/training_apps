package com.example.realmdb2.di

import com.example.realmdb2.utils.DatabaseInitializer
import com.example.realmdb2.utils.DatabaseManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabaseInitializer() : DatabaseInitializer {
        return DatabaseInitializer()
    }

    @Provides
    @Singleton
    fun provideDatabaseManager() : DatabaseManager{
        return DatabaseManager()
    }
}