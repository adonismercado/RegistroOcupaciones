package edu.ucne.registroocupaciones.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registroocupaciones.data.database.AppDatabase
import edu.ucne.registroocupaciones.data.empleados.dao.EmpleadoDao
import edu.ucne.registroocupaciones.data.ocupaciones.local.dao.OcupacionDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideOcupacionDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "Ocupacion.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideOcupacionDao(database: AppDatabase): OcupacionDao {
        return database.ocupacionDao()
    }

    @Provides
    @Singleton
    fun provideEmpleadoDao(database: AppDatabase): EmpleadoDao {
        return database.empleadoDao()
    }
}