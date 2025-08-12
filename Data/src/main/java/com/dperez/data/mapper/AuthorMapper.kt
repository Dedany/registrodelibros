package com.dperez.data.mapper

import com.dedany.domain.entities.Author
import com.dperez.data.datasource.local.dbo.AuthorDbo
import com.dperez.data.datasource.remote.dto.author.AuthorDto
import com.dperez.data.datasource.remote.dto.author.BioField

fun AuthorDbo.toDomain(): Author {
    return Author(
        id = id,
        name = name,
        birthDate = birthDate,
        topWork = topWork,
        workCount = workCount,
        alternateNames = alternateNames,
        topSubjects = topSubjects,
        bio = bio,
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
        bio = bio,
        isFavorite = isFavorite
    )
}

fun AuthorDto.toDbo(): AuthorDbo {
    val bioText = when(val bioField = this.bio) {
        is BioField.BioString -> bioField.bioString
        is BioField.BioValue -> bioField.value
        else -> null
    }
    return AuthorDbo(
        id = this.key,
        name = this.name,
        birthDate = this.birthDate,
        topWork = this.topWork,
        workCount = this.workCount,
        alternateNames = this.alternateNames ?: emptyList(),
        topSubjects = this.topSubjects ?: emptyList(),
        bio = bioText,
        isFavorite = false
    )
}