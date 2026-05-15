package edu.ucne.registroocupaciones.data.ocupaciones.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registroocupaciones.data.ocupaciones.local.daos.OcupacionDao
import edu.ucne.registroocupaciones.data.ocupaciones.local.entities.OcupacionEntity
import edu.ucne.registroocupaciones.data.ocupaciones.local.entities.EmpleadoEntity
import edu.ucne.registroocupaciones.data.ocupaciones.local.daos.EmpleadoDao

@Database(
    entities = [
        OcupacionEntity::class,
        EmpleadoEntity::class

    ],
    version = 2,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun ocupacionDao(): OcupacionDao
    abstract fun empleadoDao(): EmpleadoDao
}