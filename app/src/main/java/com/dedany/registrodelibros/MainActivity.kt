package com.dedany.registrodelibros


import BoxWithColumnClickableBoxes
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dedany.registrodelibros.ui.screens.BookDetailScreen
import com.dedany.registrodelibros.ui.screens.BooksScreen
import com.dedany.registrodelibros.ui.viewmodel.BooksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    BoxWithColumnClickableBoxes(
                        onGeneroClick = { /* navController.navigate("generos") */ },
                        onAutoresClick = { /* navController.navigate("autores") */ },
                        onLibrosClick = { navController.navigate("books") }
                    )
                }

                composable("books") {
                    // Aquí crea o inyecta tu ViewModel normalmente
                    val booksViewModel = hiltViewModel<BooksViewModel>()
                    BooksScreen(viewModel = booksViewModel, navController = navController)
                }

                composable("bookDetail/{bookId}") { backStackEntry ->
                    val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
                    BookDetailScreen(bookId = bookId, navController = navController)
                }

                // Puedes añadir más pantallas aquí:
                // composable("generos") { GenerosScreen() }
                // composable("autores") { AutoresScreen() }
            }
        }
    }
}
