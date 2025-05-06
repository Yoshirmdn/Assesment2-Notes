package com.rioramdani0034.mobpro1.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rioramdani0034.mobpro1.database.NotesDao
import com.rioramdani0034.mobpro1.model.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: NotesDao) : ViewModel() {

    fun insert(nama: String, nim: String, kelas: String) {
        val mahasiswa = Notes(
            name = nama,
            content = nim,
            categories = kelas
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insertMahasiswa(mahasiswa)
        }
    }
    suspend fun getMahasiswa(id: Long): Notes? {
        return dao.getMahasiswaById(id)
    }
    fun update(id: Long, nama: String, nim: String, kelas: String) {
        val mahasiswa = Notes(
            id = id,
            name = nama,
            content = nim,
            categories = kelas
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.updateMahasiswa(mahasiswa)
        }
    }
    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteMahasiswaById(id)
        }
    }
}
