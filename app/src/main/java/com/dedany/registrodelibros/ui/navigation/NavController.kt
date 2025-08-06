package com.dedany.registrodelibros.ui.navigation

import BoxWithColumnClickableBoxes
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dedany.registrodelibros.ui.screens.BookDetailScreen
import com.dedany.registrodelibros.ui.screens.BooksScreen
import com.dedany.registrodelibros.ui.viewmodel.BooksViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            BoxWithColumnClickableBoxes(
                onGeneroClick = { navController.navigate("generos") },
                onAutoresClick = { navController.navigate("autores") },
                onLibrosClick = { navController.navigate("libros") }
            )
        }

        composable("libros") {
            val booksViewModel = hiltViewModel<BooksViewModel>()
            BooksScreen(viewModel = booksViewModel, navController = navController)
        }

        composable(
            "bookDetail/{bookId}",
            arguments = listOf(
                navArgument("bookId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val encodedId = backStackEntry.arguments?.getString("bookId") ?: ""
            val decodedId = Uri.decode(encodedId)
            BookDetailScreen(bookId = decodedId,navController=navController)
        }
    }
}