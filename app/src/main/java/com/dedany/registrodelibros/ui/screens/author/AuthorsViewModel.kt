package com.dedany.registrodelibros.ui.screens.author

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedany.domain.entities.Author
import com.dedany.usecase.author.GetAuthorsByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorsViewModel @Inject constructor(
    private val getAuthorsByNameUseCase: GetAuthorsByNameUseCase
) : ViewModel() {

    private val _authors = MutableStateFlow<List<Author>>(emptyList())
    val authors: StateFlow<List<Author>> = _authors

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    init {
        loadAuthors("")
    }

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery

        viewModelScope.launch {
            if (newQuery.isBlank()) {
                loadAuthors("")
            } else {
                val response = getAuthorsByNameUseCase(newQuery)
                _authors.value = response.sortedBy { it.name }
            }
        }


    }


    fun loadAuthors(defaultQuery: String ) {
        viewModelScope.launch {
            val response = getAuthorsByNameUseCase(defaultQuery)
            _authors.value = response.sortedBy { it.name }
        }
    }

}

