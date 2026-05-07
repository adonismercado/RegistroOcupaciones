package edu.ucne.registroocupaciones.domain.ocupacion.repository

import edu.ucne.registroocupaciones.domain.ocupacion.model.Ocupacion
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun observeOcupaciones(): Flow<List<Ocupacion>>
    suspend fun getOcupacion(id: Int): Ocupacion?
    suspend fun upsert(ocupacion: Ocupacion): Int
    suspend fun delete(id: Int)
}