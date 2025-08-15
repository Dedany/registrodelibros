package com.dedany.registrodelibros

import BoxWithColumnClickableBoxes
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dedany.registrodelibros.ui.navigation.AppNavigation
import com.dedany.registrodelibros.ui.screens.author.AuthorDetailScreen
import com.dedany.registrodelibros.ui.screens.author.AuthorsScreen
import com.dedany.registrodelibros.ui.screens.book.BookDetailScreen
import com.dedany.registrodelibros.ui.screens.book.BooksScreen
import com.dedany.registrodelibros.ui.screens.author.AuthorsViewModel
import com.dedany.registrodelibros.ui.screens.book.BooksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

