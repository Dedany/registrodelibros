package com.dedany.registrodelibros.ui.screens.book

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun DraggableGenreBox(
    genero: String,
    dropTargetBounds: Rect?,
    onDrop: (String) -> Unit
) {
    var offset by remember { mutableStateOf(IntOffset.Zero) }
    var itemBounds by remember { mutableStateOf<Rect?>(null) }
    var isDragging by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .onGloballyPositioned { coords ->
                itemBounds = coords.boundsInWindow()
            }
            .offset { offset }
            .pointerInput(genero) { // Usar genero como key es una buena práctica
                detectDragGestures(
                    onDragStart = { isDragging = true },
                    onDragEnd = {
                        isDragging = false
                        if (dropTargetBounds != null && itemBounds != null) {
                            val finalRect = itemBounds!!.translate(
                                offset.x.toFloat(),
                                offset.y.toFloat()
                            )
                            if (finalRect.overlaps(dropTargetBounds)) {
                                onDrop(genero)
                            }
                        }
                        // Siempre resetea el offset
                        offset = IntOffset.Zero
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        offset = IntOffset(
                            (offset.x + dragAmount.x).roundToInt(),
                            (offset.y + dragAmount.y).roundToInt()
                        )
                    }
                )
            }
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = if (isDragging) MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                else MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        Text(
            text = genero,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )
    }
}
