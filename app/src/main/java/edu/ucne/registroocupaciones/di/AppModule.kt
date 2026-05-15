package edu.ucne.registroocupaciones.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registroocupaciones.data.ocupaciones.database.OcupacionDb
import edu.ucne.registroocupaciones.data.ocupaciones.local.daos.OcupacionDao
import okhttp3.internal.publicsuffix.PublicSuffixDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideOcupacionDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            OcupacionDb::class.java,
            "Ocupacion.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideOcupacionDao(database: OcupacionDb): OcupacionDao {
        return database.ocupacionDao()
    }
}