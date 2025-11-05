package com.dedany.registrodelibros.ui.screens.book

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import com.dedany.domain.entities.Author
import com.dedany.domain.entities.Book
import com.dedany.domain.entities.BookWithAuthors
import kotlin.text.isNotBlank


fun cleanReferences(text: String): String {
    // Elimina patrones tipo [1], [2], etc.
    return text.replace(Regex("""\[\d+\]"""), "")
}

fun extractMainDescription(text: String): String {
    // Corta justo antes de "See also", enlaces largos o cualquier sección no descriptiva
    val keywords = listOf("See also", "Fuente:", "https://", "http://")
    var idx = -1
    for (kw in keywords) {
        val found = text.indexOf(kw)
        if (found > 0 && (idx == -1 || found < idx)) idx = found
    }
    val cleanText = if (idx > 0) text.substring(0, idx) else text
    return cleanText.trim()
}

@Composable
fun BookDetailScreen(
    bookId: String,
    navController: NavController,
    viewModel: BookDetailViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val bookWithAuthorsData: BookWithAuthors? by viewModel.book.collectAsState()

    LaunchedEffect(bookId) {
        viewModel.loadBookById(bookId)
    }

    val currentBookDetails = bookWithAuthorsData
    val authorList = bookWithAuthorsData?.authors.orEmpty()

    if (currentBookDetails != null) {
        val bookEntity: Book = currentBookDetails.book // Accede a la entidad Book interna
        val authorsList: List<Author> =
            currentBookDetails.authors // Accede a la lista de Author interna


        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //Portada
            AsyncImage(
                model = bookEntity.coverId?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" },
                contentDescription = "Portada de ${bookEntity.title ?: "Libro"}",
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .aspectRatio(2f / 3f)
                    .clip(RoundedCornerShape(12.dp))
                    .padding(top = 32.dp),
                contentScale = ContentScale.Crop
            )
            //Titulo
            BookDetailText(text = bookEntity.title ?: "Sin título")

            //Author, clickable
            if (authorsList.isNotEmpty()) {
                AuthorsClickableText(
                    authorsData = authorsList,
                    onAuthorClick = { clickedAuthor ->
                        val authorIdNavigate = clickedAuthor.id
                        if (authorIdNavigate.isNotBlank()) {
                            val encodedId = Uri.encode(authorIdNavigate)
                            Log.d(
                                "BookDetailNav",
                                "Navegando a authorDetail/$encodedId desde BookDetailScreen"
                            )
                            navController.navigate("authorDetail/$encodedId")
                        } else {
                            Log.w(
                                "BookDetailNav",
                                "Intento de navegar a autor con ID vacío: ${clickedAuthor.name}"
                            )
                        }
                    }
                )
            } else {
                BookDetailText(text = "Autor desconocido")
            }

            //Año
            BookDetailText(text = "Publicado en: ${bookEntity.publishYear ?: "Desconocido"}")

            // Descripcion principal
            BookDetailText(
                text = cleanReferences(bookEntity.description ?: "Sin descripción"),
                modifier = Modifier.padding(top = 16.dp),
                color = androidx.compose.ui.graphics.Color.DarkGray
            )


            //temas visuales
            val subjects = bookEntity.subjects
            if (!subjects.isNullOrEmpty()) {
                val displaySubjects = subjects.take(5).joinToString(", ")
                val moreCount = subjects.size - 5
                val text =
                    if (moreCount > 0) "$displaySubjects, +$moreCount more" else displaySubjects

                BookDetailText(
                    text = "Temas: $text",
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color.DarkGray
                )
            } else {
                BookDetailText(
                    text = "Temas desconocidos",
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color.DarkGray
                )
            }

            Button(
                onClick = {
                    viewModel.toggleFavorite(bookEntity.id, !bookEntity.isFavorite)
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(if (bookEntity.isFavorite) "❤️ Favorito" else "🤍 No es favorito")
            }

            // Mostrar estado favorito, leído y rating

            BookDetailText(
                text = if (bookEntity.isRead) "📖 Leído" else "📕 No leído",
                modifier = Modifier.padding(top = 4.dp)
            )
            BookDetailText(
                text = "⭐ Puntuación: ${bookEntity.rating}",
                modifier = Modifier.padding(top = 4.dp)
            )
            Button(
                onClick = {
                    val amazonUrl = "https://www.amazon.com/s?k=${Uri.encode(bookEntity.title ?: "")}"
                    navController.navigate("webview?url=${Uri.encode(amazonUrl)}")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Comprar en Amazon")
            }
        }
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
    authorsData: List<Author>,
    onAuthorClick: (Author) -> Unit
) {
    val annotatedString = buildAnnotatedString {
        authorsData.forEachIndexed { index, author ->
            val tag = "author_$index"
            pushStringAnnotation(tag = tag, annotation = author.id)
            withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)) {
                append(author.name)
            }
            pop()
            if (index != authorsData.lastIndex) append(", ")
        }
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    val clickedAuthor = authorsData.find { it.id == annotation.item }
                    clickedAuthor?.let { onAuthorClick(it) }

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
