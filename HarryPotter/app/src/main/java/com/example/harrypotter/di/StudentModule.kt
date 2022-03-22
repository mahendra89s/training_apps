package com.example.harrypotter.di

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.harrypotter.data.network.NetworkConnectionIntercepter
import com.example.harrypotter.data.repository.StudentRepository
import com.example.harrypotter.ui.LoadingDialog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object StudentModule {

    @Singleton
    @Provides
    @ActivityContext
    fun provideDialogLoader(context: AppCompatActivity):LoadingDialog{
        return LoadingDialog(context)
    }

    @Singleton
    @Provides
    fun provideNetworkInterceptor(@ApplicationContext context: Context):NetworkConnectionIntercepter{
        return NetworkConnectionIntercepter(context)
    }

    @Singleton
    @Provides
    fun provideStudentRepository(networkConnectionIntercepter: NetworkConnectionIntercepter):StudentRepository{
        return StudentRepository(networkConnectionIntercepter)
    }



}