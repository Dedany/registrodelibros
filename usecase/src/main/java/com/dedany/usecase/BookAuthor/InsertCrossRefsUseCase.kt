package com.dedany.usecase.BookAuthor

import com.dedany.domain.repository.BookAuthorRepository

class InsertCrossRefsUseCase(private val repository: BookAuthorRepository) {
    suspend operator fun invoke(crossRefs: List<Pair<String, String>>) = repository.insertCrossRefs(crossRefs)
}