package edu.ucne.registroocupaciones.domain.empleado.usecase.empleados

import java.time.LocalDate
import javax.inject.Inject

data class ValidationResult @Inject constructor(
    val isValid: Boolean,
    val error: String? = null
)

fun validateNombre(nombre: String): ValidationResult {
    return when {
        nombre.isBlank() -> ValidationResult(false, "El nombre no puede estar vacio.")
        else -> ValidationResult(true)
    }
}

fun validateFecha(fecha: LocalDate?): ValidationResult {
    return when {
        fecha == null -> ValidationResult(false, "La fecha no puede estar vacia.")
        fecha.isAfter(LocalDate.now()) -> ValidationResult(false, "La fecha no puede ser futura al dia de hoy.")
        else -> ValidationResult(true)
    }
}

fun validateSueldo(sueldo: String): ValidationResult {
    return when {
        sueldo.isBlank() -> ValidationResult(false, "El sueldo no puede estar vacio.")
        sueldo.toDoubleOrNull() == null -> ValidationResult(false, "El sueldo debe ser un numero.")
        sueldo.toDouble() <= 0 -> ValidationResult(false, "El sueldo debe ser mayor que 0.")
        else -> ValidationResult(true)
    }
}