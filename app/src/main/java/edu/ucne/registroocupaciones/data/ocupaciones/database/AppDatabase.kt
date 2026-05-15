package edu.ucne.registroocupaciones.data.ocupaciones.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registroocupaciones.data.ocupaciones.local.daos.OcupacionDao
import edu.ucne.registroocupaciones.data.ocupaciones.local.entities.OcupacionEntity

@Database(
    entities = [
        OcupacionEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun ocupacionDao(): OcupacionDao
}