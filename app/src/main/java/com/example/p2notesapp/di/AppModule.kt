package com.example.p2notesapp.di

import android.content.Context
import com.example.p2notesapp.data.local.db.NoteDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context): NoteDataBase {

        return NoteDataBase.getDataBase(context)
    }

    @Provides
    @Singleton
    fun getDao(dataBase: NoteDataBase) = dataBase.noteDAO()
}
