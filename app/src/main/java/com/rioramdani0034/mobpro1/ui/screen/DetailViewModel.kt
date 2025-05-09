package com.rioramdani0034.mobpro1.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rioramdani0034.mobpro1.database.NotesDao
import com.rioramdani0034.mobpro1.model.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: NotesDao) : ViewModel() {

    fun insert(name: String, content: String, categories: String) {
        val notes = Notes(
            name = name,
            content = content,
            categories = categories
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insertNotes(notes)
        }
    }
    suspend fun getNotes(id: Long): Notes? {
        return dao.getNotesById(id)
    }
    fun update(id: Long, name: String, content: String, categories: String) {
        val notes = Notes(
            id = id,
            name = name,
            content = content,
            categories = categories
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.updateNotes(notes)
        }
    }
    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteNotesById(id)
        }
    }
}
