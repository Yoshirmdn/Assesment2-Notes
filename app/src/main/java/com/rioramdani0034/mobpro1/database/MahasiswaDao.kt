package com.rioramdani0034.mobpro1.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rioramdani0034.mobpro1.model.Mahasiswa
import kotlinx.coroutines.flow.Flow

@Dao
interface MahasiswaDao {

     @Insert
     suspend fun insertMahasiswa(mahasiswa: Mahasiswa)

     @Update
     suspend fun updateMahasiswa(mahasiswa: Mahasiswa)

     @Query("SELECT * FROM mahasiswa Order By nim ASC")
     fun getMahasiswa(): Flow<List<Mahasiswa>>

     @Query("SELECT * FROM mahasiswa WHERE id = :id")
     suspend fun getMahasiswaById(id: Long): Mahasiswa?

     @Query("DELETE FROM mahasiswa WHERE id = :id")
     suspend fun deleteMahasiswaById(id: Long)
}