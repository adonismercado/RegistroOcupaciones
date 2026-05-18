package edu.ucne.registroocupaciones.data.empleados.mapper

import edu.ucne.registroocupaciones.data.empleados.entity.EmpleadoEntity
import edu.ucne.registroocupaciones.domain.empleado.model.Empleado
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