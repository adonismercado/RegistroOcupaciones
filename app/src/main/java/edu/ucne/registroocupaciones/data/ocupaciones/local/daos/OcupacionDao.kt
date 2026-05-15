package edu.ucne.registroocupaciones.data.ocupaciones.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.registroocupaciones.data.ocupaciones.local.entities.OcupacionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OcupacionDao {
    @Query("SELECT * FROM ocupaciones ORDER BY ocupacionId DESC")
    fun observeAll(): Flow<List<OcupacionEntity>>

    @Query("SELECT * FROM ocupaciones WHERE ocupacionId = :id")
    suspend fun getById(id: Int): OcupacionEntity?

    @Upsert
    suspend fun upsert(entity: OcupacionEntity)

    @Delete
    suspend fun delete(entity: OcupacionEntity)

    @Query("DELETE FROM ocupaciones WHERE ocupacionId = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT COUNT(*) > 0 FROM ocupaciones WHERE descripcion = :descripcion AND ocupacionId != :id")
    suspend fun existeDescripcion(descripcion: String, id: Int) : Boolean
}