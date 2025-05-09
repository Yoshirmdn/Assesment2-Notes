package com.rioramdani0034.mobpro1.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rioramdani0034.mobpro1.database.NotesDao
import com.rioramdani0034.mobpro1.model.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailViewModel(private val dao: NotesDao) : ViewModel() {

    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    fun insert(name: String, content: String, categories: String, imageUri: String? = null) {
        val notes = Notes(
            name = name,
            content = content,
            categories = categories,
            createdDate = formatter.format(Date()),
            imageUri = imageUri
        )
        println("INSERT NOTE: $notes")
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertNotes(notes)
        }
    }

    suspend fun getNotes(id: Long): Notes? {
        return dao.getNotesById(id)
    }

    fun update(id: Long, name: String, content: String, categories: String, imageUri: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentNote = dao.getNotesById(id)
            val notes = Notes(
                id = id,
                name = name,
                content = content,
                categories = categories,
                createdDate = currentNote?.createdDate ?: formatter.format(Date()),
                imageUri = imageUri ?: currentNote?.imageUri
            )
            dao.updateNotes(notes)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteNotesById(id)
        }
    }
}
