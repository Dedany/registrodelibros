package com.dedany.registrodelibros.ui.screens.book

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dedany.registrodelibros.ui.screens.booksAuthors.BooksWithAuthorsViewModel

@Composable
fun BookDetailScreen(
    bookId: String,
    navController: NavController,
    viewModel: BookDetailViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val book by viewModel.book.collectAsState()

    LaunchedEffect(bookId) {
        viewModel.loadBookById(bookId)
    }

    if (book != null) {
        val b = book!!

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = b.coverId?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" },
                contentDescription = "Portada de ${b.title ?: "Libro"}",
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .aspectRatio(2f / 3f)
                    .clip(RoundedCornerShape(12.dp))
                    .padding(top = 32.dp),
                contentScale = ContentScale.Crop
            )

            BookDetailText(text = b.title ?: "Sin título")

            b.author?.let { authors ->
                AuthorsClickableText(
                    authors = authors,
                    onAuthorClick = { authorName ->
                        navController.navigate("autores")
                    }
                )
            } ?: BookDetailText(text = "Autor desconocido")

            BookDetailText(text = "Publicado en: ${b.publishYear ?: "Desconocido"}")

            // Aquí añadimos la descripción
            BookDetailText(
                text = b.description ?: "Sin descripción",
                modifier = Modifier.padding(top = 16.dp),
                color = androidx.compose.ui.graphics.Color.DarkGray
            )

            // Mostrar estado favorito, leído y rating
            BookDetailText(
                text = if (b.isFavorite) "❤️ Favorito" else "🤍 No es favorito",
                modifier = Modifier.padding(top = 16.dp)
            )
            BookDetailText(
                text = if (b.isRead) "📖 Leído" else "📕 No leído",
                modifier = Modifier.padding(top = 4.dp)
            )
            BookDetailText(
                text = "⭐ Puntuación: ${b.rating}",
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    } else {
        Text("Cargando...")
    }
}



@Composable
fun BookDetailText(
    text: String,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified
) {

    val combinedModifier = modifier
        .fillMaxWidth()
        .then(
            if (onClick != null) Modifier.clickable { onClick() } else Modifier
        )

    Text(
        text = text,
        modifier = combinedModifier,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = color
    )
}
@Composable
fun AuthorsClickableText(
    authors: List<String>,
    onAuthorClick: (String) -> Unit
) {
    val annotatedString = buildAnnotatedString {
        authors.forEachIndexed { index, author ->
            val tag = "author_$index"
            pushStringAnnotation(tag = tag, annotation = author)
            withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)) {
                append(author)
            }
            pop()
            if (index != authors.lastIndex) append(", ")
        }
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    onAuthorClick(annotation.item)
                }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        style = androidx.compose.ui.text.TextStyle(
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    )
}
