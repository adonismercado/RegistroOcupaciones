package edu.ucne.registroocupaciones.data.ocupaciones.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.registroocupaciones.data.ocupaciones.local.entities.EmpleadoEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface EmpleadoDao {
    @Query("SELECT * FROM empleados ORDER BY empleadoId DESC")
    fun observeALl(): Flow<List<EmpleadoEntity>>

    @Query("SELECT * FROM empleados WHERE empleadoId = :id")
    suspend fun getById(id: Int): EmpleadoEntity?

    @Upsert
    suspend fun upsert(entity: EmpleadoEntity)

    @Delete
    suspend fun delete(entity: EmpleadoEntity)

    @Query("DELETE FROM empleados WHERE empleadoId = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM empleados WHERE nombres LIKE '%' || :nombre || '%'")
    fun filterByName(nombre: String): Flow<List<EmpleadoEntity>>

    @Query("SELECT * FROM empleados WHERE fechaIngreso = :fecha")
    fun filterByDate(fecha: String): Flow<List<EmpleadoEntity>>
}