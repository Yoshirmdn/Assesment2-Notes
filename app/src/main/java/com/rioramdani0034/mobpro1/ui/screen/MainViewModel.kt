package com.rioramdani0034.mobpro1.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rioramdani0034.mobpro1.database.NotesDao
import com.rioramdani0034.mobpro1.model.Notes
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(dao: NotesDao) : ViewModel() {
    val data: StateFlow<List<Notes>> = dao.getNote().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )
}