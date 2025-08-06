package com.dedany.registrodelibros.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedany.domain.entities.Book
import com.dedany.usecase.book.GetBookByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val getBookByIdUseCase: GetBookByIdUseCase,
) : ViewModel() {

    private val _book = MutableStateFlow<Book?>(null)
    val book: StateFlow<Book?> = _book

    fun loadBookById(id: String) {
        viewModelScope.launch {
            _book.value = getBookByIdUseCase(id)
        }
    }
}
