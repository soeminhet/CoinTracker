package com.smh.design

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ImageLoader(
    model: Any,
    modifier: Modifier = Modifier,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.Crop
) {
    AsyncImage(
        model = model, 
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier,
        placeholder = painterResource(id = R.drawable.placeholder_default),
        error = painterResource(id = R.drawable.placeholder_default),
        onError = {
            Log.i("IMAGELOADER", "Error ${it.result.throwable.message}")
        }
    )
}

@Preview
@Composable
private fun ImageLoaderPreview() {
    ImageLoader(
        model = "https://picsum.photos/200",
        modifier = Modifier.size(50.dp),
    )
}