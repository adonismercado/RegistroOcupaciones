package edu.ucne.registroocupaciones.domain.ocupacion.usecase

data class ValidationResult (
    val isValid: Boolean,
    val error: String? = null
)

fun validateDescripcion(descripcion: String): ValidationResult {
    return when {
        descripcion.isBlank() -> ValidationResult(false, "La descripcion no puede estar vacia.")
        else -> ValidationResult(true)
    }
}

fun validateSueldo(sueldo: Double): ValidationResult {
    return when{
        sueldo <= 0 -> ValidationResult(false, "El sueldo no puede ser menor o igual que 0.")
        else -> ValidationResult(true)
    }
}