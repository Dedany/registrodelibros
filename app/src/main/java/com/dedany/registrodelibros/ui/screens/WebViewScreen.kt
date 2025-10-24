package com.dedany.registrodelibros.ui.screens

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(
    url: String,
    navController: NavController
) {
    var progress by remember { mutableStateOf(0) }

    // Manejo del botón de back físico
    BackHandler {
        navController.popBackStack()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Barra de progreso
        if (progress < 100) {
            LinearProgressIndicator(
                progress = progress / 100f,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Botón de volver
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Volver")
        }

        // WebView
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()
                    webChromeClient = object : WebChromeClient() {
                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            progress = newProgress
                        }
                    }
                    settings.javaScriptEnabled = true
                    loadUrl(url) // Ahora dinámico
                }
            },
            modifier = Modifier.weight(1f)
        )
    }
}
