package edu.ucne.registroocupaciones.data.ocupaciones.mapper

import edu.ucne.registroocupaciones.data.ocupaciones.local.entities.EmpleadoEntity
import edu.ucne.registroocupaciones.domain.ocupacion.model.Empleado
import java.time.LocalDate

fun EmpleadoEntity.toDomain(): Empleado = Empleado(
    empleadoId = empleadoId,
    fechaIngreso = LocalDate.parse(fechaIngreso),
    nombres = nombres,
    sexo = sexo,
    sueldo = sueldo
)

fun Empleado.toEntity(): EmpleadoEntity = EmpleadoEntity(
    empleadoId = empleadoId,
    fechaIngreso = fechaIngreso.toString(),
    nombres = nombres,
    sexo = sexo,
    sueldo = sueldo
)