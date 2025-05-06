package com.rioramdani0034.mobpro1.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rioramdani0034.mobpro1.model.Notes
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

     @Insert
     suspend fun insertMahasiswa(mahasiswa: Notes)

     @Update
     suspend fun updateMahasiswa(mahasiswa: Notes)

     @Query("SELECT * FROM notes Order By content ASC")
     fun getMahasiswa(): Flow<List<Notes>>

     @Query("SELECT * FROM notes WHERE id = :id")
     suspend fun getMahasiswaById(id: Long): Notes?

     @Query("DELETE FROM notes WHERE id = :id")
     suspend fun deleteMahasiswaById(id: Long)
}