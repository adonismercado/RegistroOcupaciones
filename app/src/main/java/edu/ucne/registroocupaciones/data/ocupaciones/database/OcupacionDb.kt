package edu.ucne.registroocupaciones.data.ocupaciones.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registroocupaciones.data.ocupaciones.local.OcupacionDao
import edu.ucne.registroocupaciones.data.ocupaciones.local.OcupacionEntity

@Database(
    entities = [
        OcupacionEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class OcupacionDb : RoomDatabase() {
    abstract fun ocupacionDao(): OcupacionDao
}