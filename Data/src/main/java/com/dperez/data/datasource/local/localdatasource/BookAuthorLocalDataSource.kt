package com.dperez.data.datasource.local.localdatasource

import com.dperez.data.datasource.local.dao.BookAuthorDao
import com.dperez.data.datasource.local.dbo.BookAuthorCrossRef
import javax.inject.Inject

class BookAuthorLocalDataSource @Inject constructor(
private val bookAuthorDao: BookAuthorDao
){
    suspend fun insertCrossRef(crossRef: BookAuthorCrossRef) = bookAuthorDao.insertCrossRef(crossRef)
    suspend fun insertCrossRefs(crossRefs: List<BookAuthorCrossRef>) = bookAuthorDao.insertCrossRefs(crossRefs)
    suspend fun getBookWithAuthors(bookId: String) = bookAuthorDao.getBookWithAuthors(bookId)
    suspend fun getAuthorWithBooks(authorId: String) = bookAuthorDao.getAuthorWithBooks(authorId)
    suspend fun getCrossRefsByBookId(bookId: String): List<BookAuthorCrossRef> {
        return bookAuthorDao.getCrossRefsForBook(bookId)
    }
}