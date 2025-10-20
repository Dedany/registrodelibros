package com.dedany.registrodelibros.ui.screens.mySpace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedany.domain.entities.Book
import com.dedany.usecase.book.GetFavoriteBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MySpaceViewModel @Inject constructor(
    private val getFavoriteBooksUseCase: GetFavoriteBooksUseCase
) : ViewModel() {


    private val _favoriteBooks = MutableStateFlow<List<Book>>(emptyList())
    val favoriteBooks: MutableStateFlow<List<Book>> = _favoriteBooks

    fun loadFavoriteBooks() {
        viewModelScope.launch {
            val books = getFavoriteBooksUseCase()
            _favoriteBooks.value = books
        }
    }

}


