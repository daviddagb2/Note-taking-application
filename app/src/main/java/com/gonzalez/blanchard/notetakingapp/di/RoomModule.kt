package com.gonzalez.blanchard.notetakingapp.di

import android.content.Context
import androidx.room.Room
import com.gonzalez.blanchard.notetakingapp.data.database.AppDatabase
import com.gonzalez.blanchard.notetakingapp.utils.getAndroidId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    private val dbname = "NotesDB"

    @Provides
    @Singleton
    fun databaseProvider(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            dbname,
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .addMigrations()
            .openHelperFactory(getHelperFactory(appContext)).build()

    private fun getHelperFactory(context: Context) = SupportFactory(
        SQLiteDatabase.getBytes(
            context.getAndroidId().toCharArray(),
        ),
    )

    @Singleton
    @Provides
    fun provideNotesDao(db: AppDatabase) = db.getNotesDao()
}
