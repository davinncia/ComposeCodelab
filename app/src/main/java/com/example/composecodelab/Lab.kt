package com.example.composecodelab

import android.content.Context
import android.net.Uri
import android.view.MotionEvent
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.sharp.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.composecodelab.ui.CollapsingBox
import com.example.composecodelab.ui.FlowLayout
import com.example.composecodelab.ui.FlowLayout2
import com.example.composecodelab.ui.ShrinkingSurface
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.skydoves.landscapist.glide.GlideImage
import dev.jeziellago.compose.markdowntext.MarkdownText
import java.net.URL
import kotlin.math.roundToInt

private val videoUrl =
    "https://cdn.fizzup.com/full/nutrition/recipes/video/1_1506953427petit_dej_3_carre_fr_v21.m4v"


@Preview(widthDp = 340, backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun ScreenLab() {

    Scaffold(
        content = {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    CoverView()
                    TitleView()
                    Tags()
                    Specs()
                    MarkDownView()
                }




        }
    )
}

@Composable
private fun CoverView(url: String = "https://picsum.photos/200") {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(ratio = 1.5f)
    ) {
        GlideImage(
            imageModel = url,
            contentScale = ContentScale.Crop
        )
    }

}

@Composable
private fun TitleView(label: String = "Hmm c'est un délice") {
    Text(
        text = label,
        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 21.sp),
    )
}

//7827 fucks with scrollable column
@Composable
private fun Tags(
    tags: List<String> = listOf(
        "un",
        "beaucoup beacoup",
        "encore un ou deux",
        "voilke ça ira"
    )
) {
    FlowLayout2 {
        tags.forEach { TagView(label = it) }
    }
}

@Composable
private fun TagView(label: String) {
    Box(modifier = Modifier.padding(4.dp)) {
        Surface(color = Color.LightGray, shape = RoundedCornerShape(16.dp)) {
            Text(
                text = label, modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 12.dp, end = 12.dp, bottom = 8.dp, top = 6.dp)
            )
        }
    }

}

@Composable
fun Specs(
    specs: List<Pair<String, String>> = listOf(
        Pair("648", "Personnes"),
        Pair("648", "Personnes"),
        Pair("648", "Personnes")
    )
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        for (i in 0..specs.lastIndex) {
            SpecView(
                modifier = Modifier.weight(1f),
                value = specs[i].first,
                label = specs[i].second
            )
            if (i != specs.lastIndex) {
                Surface(
                    modifier = Modifier
                        .width(1.dp)
                        .height(20.dp)
                        .align(Alignment.CenterVertically),
                    color = Color.LightGray
                ) {}
            }
        }
    }
}

@Composable
private fun SpecView(
    modifier: Modifier = Modifier,
    value: String = "648",
    label: String = "Personnes"
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = value,
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W500)
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = label,
            color = Color.Gray, // 7827 darkmode
            style = TextStyle(fontSize = 11.sp)
        )
    }
}

@Composable
private fun MarkDownView() {
    MarkdownText(
        markdown = "OMELETTE AUX POIS CHICHES ET OIGNON \n1. Dans un petit saladier, mélanger la farine de pois chiches, un peu de sel et de poivre. \n2. Ajouter l'eau et mélanger le tout. \n3. Couper l'oignon, l'aneth et le basilic \n4. Ajouter le tout à la préparation. \n5. Dans une poêle préalablement huilée, verser la préparation.\n6. Faire chauffer quelques minutes puis retourner le tout. \n7. Retirer du feu. \n8. Laisser refroidir et servir. \n\nJUS D'ORANGE/BANANE\n1. Couper l'orange, la presser.  \n2. Éplucher la banane et la couper en morceaux. \n3. Mixer ensemble la banane, le jus d'orange et le jus de citron.",
    )
}

/*
@Composable
fun VideoPlayer() {
    Column(
        Modifier.fillMaxSize()
    ) {

        val context = LocalContext.current

        // Initializing ExoPLayer
        val mExoPlayer = remember(context) {
            ExoPlayer.Builder(context).build().apply {

                val mediaItem = MediaItem.Builder()
                    .setUri(Uri.parse(videoUrl))
                    .build()
                setMediaItem(mediaItem)

                playWhenReady = false
                prepare()
            }
        }


        // Implementing ExoPlayer
        AndroidView(factory = { context ->
            PlayerControlView(context).apply {
                player = mExoPlayer
            }
        })
    }
}

 */
