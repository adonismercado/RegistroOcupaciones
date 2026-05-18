package edu.ucne.registroocupaciones.data.empleados.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "empleados")
data class EmpleadoEntity (
    @PrimaryKey(autoGenerate = true)
    val empleadoId: Int = 0,
    val fechaIngreso: String,
    val nombres: String,
    val sexo: String,
    val sueldo: String
)