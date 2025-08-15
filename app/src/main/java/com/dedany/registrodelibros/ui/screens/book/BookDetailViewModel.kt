package com.dedany.registrodelibros.ui.screens.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedany.domain.entities.Book
import com.dedany.domain.entities.BookWithAuthors
import com.dedany.usecase.book.GetBookByIdUseCase
import com.dedany.usecase.bookAuthor.GetBookWithAuthorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val getBookWithAuthorsByIdUseCase: GetBookWithAuthorsUseCase
) : ViewModel() {

    private val _book = MutableStateFlow<BookWithAuthors?>(null)
    val book: StateFlow<BookWithAuthors?> = _book.asStateFlow()

    fun loadBookById(id: String) {
        viewModelScope.launch {
            _book.value = getBookWithAuthorsByIdUseCase(id)
        }
    }
}
