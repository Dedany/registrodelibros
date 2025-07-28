package com.dperez.data.mapper

import com.dedany.domain.entities.Author
import com.dperez.data.datasource.local.dbo.AuthorDbo
import com.dperez.data.datasource.remote.dto.author.AuthorDto

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

fun AuthorDto.toDbo(): AuthorDbo {
    return AuthorDbo(
        id = this.key,
        name = this.name,
        birthDate = this.birthDate,
        topWork = this.topWork,
        workCount = this.workCount,
        alternateNames = this.alternateNames ?: emptyList(),
        topSubjects = this.topSubjects ?: emptyList(),
        isFavorite = false
    )
}