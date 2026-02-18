package edu.nd.pmcburne.hello

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri

@Composable
fun CountingTutorial() {
    val context = LocalContext.current
    TextButton(
        onClick = { launchYoutubeIntent(context) },
        modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
            .padding(8.dp)
    ) {
        Text("Tutorial on counting")
    }
}

const val COUNTING_TUTORIAL_URL = "https://www.youtube.com/watch?v=2AoxCkySv34"
const val COUNTING_VIDEO_CODE = "2AoxCkySv34"

private fun launchYoutubeIntent(context: Context) {
    val appIntent = Intent(Intent.ACTION_VIEW, "vnd.youtube:$COUNTING_VIDEO_CODE".toUri())
    try {
        context.startActivity(appIntent) // Try to open in YouTube app specifically
    } catch (e: ActivityNotFoundException) {
        val webIntent = Intent(Intent.ACTION_VIEW, COUNTING_TUTORIAL_URL.toUri())
        context.startActivity(webIntent) // Fallback to browser if YouTube app is not installed or
        // otherwise can't be found
    }
}