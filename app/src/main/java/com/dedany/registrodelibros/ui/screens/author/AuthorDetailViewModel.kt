package com.dedany.registrodelibros.ui.screens.author

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedany.domain.entities.Author
import com.dedany.usecase.author.GetAuthorByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthorDetailViewModel  @Inject constructor(
    private val getAuthorByIdUseCase: GetAuthorByIdUseCase,
) : ViewModel() {
    private val _author = MutableStateFlow<Author?>(null)
    val author: StateFlow<Author?> = _author


    fun loadAuthorById(id: String) {
        viewModelScope.launch {
            _author.value = getAuthorByIdUseCase(id)
        }
    }
}