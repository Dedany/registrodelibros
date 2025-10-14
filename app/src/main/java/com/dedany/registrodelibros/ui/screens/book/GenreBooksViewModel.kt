package com.dedany.registrodelibros.ui.screens.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedany.domain.entities.Book
import com.dedany.usecase.book.GetBooksByGenreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreBooksViewModel @Inject constructor(
    private val getBooksByGenreUseCase: GetBooksByGenreUseCase
) : ViewModel() {
        private val _books = MutableStateFlow<List<Book>>(emptyList())
        val books: StateFlow<List<Book>> = _books

        private val _isLoading = MutableStateFlow(false)
        val isLoading: StateFlow<Boolean> = _isLoading

        fun loadBooksByGenre(genre: String) {
            viewModelScope.launch {
                _isLoading.value = true
                val result = getBooksByGenreUseCase(genre)
                _books.value = result
                _isLoading.value = false
            }
        }
    }