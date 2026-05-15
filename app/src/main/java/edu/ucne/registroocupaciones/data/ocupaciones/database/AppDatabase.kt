package edu.ucne.registroocupaciones.data.ocupaciones.database

import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ucne.registroocupaciones.data.ocupaciones.local.daos.OcupacionDao
import edu.ucne.registroocupaciones.data.ocupaciones.local.entities.OcupacionEntity
import edu.ucne.registroocupaciones.data.ocupaciones.local.entities.EmpleadoEntity
import edu.ucne.registroocupaciones.data.ocupaciones.local.daos.EmpleadoDao

@Database(
    entities = [
        OcupacionEntity::class,
        EmpleadoEntity::class

    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ocupacionDao(): OcupacionDao
    abstract fun empleadoDao(): EmpleadoDao
}