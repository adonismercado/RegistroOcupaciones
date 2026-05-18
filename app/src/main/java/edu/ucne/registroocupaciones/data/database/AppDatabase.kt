package edu.ucne.registroocupaciones.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registroocupaciones.data.ocupaciones.local.dao.OcupacionDao
import edu.ucne.registroocupaciones.data.ocupaciones.local.entity.OcupacionEntity
import edu.ucne.registroocupaciones.data.empleados.entity.EmpleadoEntity
import edu.ucne.registroocupaciones.data.empleados.dao.EmpleadoDao

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