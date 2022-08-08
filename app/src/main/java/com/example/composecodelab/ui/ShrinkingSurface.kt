package com.example.composecodelab.ui

import android.view.MotionEvent
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ShrinkingSurface(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    shape: Shape = RectangleShape,
    shrink: Int = 5,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val transition = updateTransition(targetState = isPressed, label = "shrink")

    val padding by transition.animateDp(label = "shrink") { state ->
        when(state) {
            false -> 0.dp
            true -> shrink.dp
        }
    }

    Surface(
        modifier = modifier
            .clip(shape)
            .clickable(
                onClick = onClick,
                indication = LocalIndication.current,
                interactionSource = interactionSource
            )
            .padding(padding),
        content = content,
        shape = shape
    )
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ClickableTileInterop() {
    val context = LocalContext.current

    var padding by remember {
        mutableStateOf(0)
    }

    Box(modifier = Modifier
        .animateContentSize()
        .fillMaxSize()
    ) {
        Card(modifier = Modifier
            .fillMaxWidth(0.5f)
            .aspectRatio(1f)
            .align(Alignment.Center)
            .clickable {
                //padding = 5
                Toast
                    .makeText(context, "click", Toast.LENGTH_SHORT)
                    .show()
            }
            .padding(padding.dp)
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        padding += 5
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        padding -= 5
                        //onClick
                        true
                    }
                    else -> true
                }
            }
        ) {
            GlideImage(imageModel = "https://picsum.photos/200")
        }
    }

}