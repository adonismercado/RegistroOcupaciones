package edu.ucne.registroocupaciones.data.ocupaciones.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.ucne.registroocupaciones.domain.ocupacion.model.Ocupacion

@Entity(tableName = "ocupaciones")
data class OcupacionEntity(
    @PrimaryKey(autoGenerate = true)
    val ocupacionId: Int = 0,
    val descripcion: String,
    val sueldo: String
)