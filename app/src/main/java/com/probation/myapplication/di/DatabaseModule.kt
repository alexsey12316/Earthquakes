package com.probation.myapplication.di

import android.content.Context
import androidx.room.Room
import com.probation.myapplication.database.EarthquakeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context):EarthquakeDatabase
    {
        return Room.databaseBuilder(context,EarthquakeDatabase::class.java,EarthquakeDatabase.NAME).build()
    }

    @Singleton
    @Provides
    fun provideDao(database: EarthquakeDatabase) = database.earthquakeDao()

}