package edu.ucne.registroocupaciones.data.ocupaciones.mapper

import edu.ucne.registroocupaciones.data.ocupaciones.local.entities.EmpleadoEntity
import edu.ucne.registroocupaciones.domain.ocupacion.model.Empleado

fun EmpleadoEntity.toDomain(): Empleado = Empleado(
    empleadoId = empleadoId,
    fechaIngreso = fechaIngreso,
    nombres = nombres,
    sexo = sexo,
    sueldo = sueldo
)

fun Empleado.toEntity(): EmpleadoEntity = EmpleadoEntity(
    empleadoId = empleadoId,
    fechaIngreso = fechaIngreso,
    nombres = nombres,
    sexo = sexo,
    sueldo = sueldo
)