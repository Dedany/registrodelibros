package com.dperez.data.mapper

import com.dedany.domain.entities.Author
import com.dperez.data.datasource.local.dbo.AuthorDbo

fun AuthorDbo.toDomain(): Author {
    return Author(
        id = id,
        name = name,
        birthDate = birthDate,
        topWork = topWork,
        workCount = workCount,
        alternateNames = alternateNames,
        topSubjects = topSubjects,
        isFavorite = isFavorite
    )
}

fun Author.toDbo(): AuthorDbo {
    return AuthorDbo(
        id = id,
        name = name,
        birthDate = birthDate,
        topWork = topWork,
        workCount = workCount,
        alternateNames = alternateNames,
        topSubjects = topSubjects,
        isFavorite = isFavorite
    )
}