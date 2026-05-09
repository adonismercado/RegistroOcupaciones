package edu.ucne.registroocupaciones.domain.ocupacion.usecase

import javax.inject.Inject

data class ValidationResult @Inject constructor(
    val isValid: Boolean,
    val error: String? = null
)

fun validateDescripcion(descripcion: String): ValidationResult {
    return when {
        descripcion.isBlank() -> ValidationResult(false, "La descripcion no puede estar vacia.")
        else -> ValidationResult(true)
    }
}

fun validateSueldo(sueldo: String): ValidationResult {
    return when{
        sueldo.isBlank() -> ValidationResult(false, "El sueldo no puede estar vacio.")
        sueldo.toDoubleOrNull() == null -> ValidationResult(false, "El sueldo debe ser un numero.")
        sueldo.toDouble() <= 0 -> ValidationResult(false, "El sueldo debe ser mayor que 0.")
        else -> ValidationResult(true)
    }
}