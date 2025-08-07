package io.github.kabirnayeem99.kshare

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File
import java.net.URLConnection

actual class ContentSharer(private val context: Context) {
    actual fun shareText(text: String, title: String?): Result<Unit> {
        return runCatching {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
                if (!title.isNullOrBlank()) {
                    putExtra(Intent.EXTRA_TITLE, title)
                }
            }
            val chooser = Intent.createChooser(intent, title ?: "Share via")
            context.startActivity(chooser)
        }
    }

    actual fun shareFile(
        fileName: String, fileBytes: ByteArray, mimeType: String, title: String?
    ): Result<Unit> {
        return runCatching {
            val file = File(context.cacheDir, fileName).apply {
                writeBytes(fileBytes)
            }
            val uri =
                FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = mimeType
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            context.startActivity(Intent.createChooser(intent, title ?: "Share via"))
        }
    }

    actual fun shareFileByPath(filePath: String, title: String?): Result<Unit> {
        return runCatching {
            val file = File(filePath)
            val uri =
                FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = getMimeTypeFromUri(uri)
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            context.startActivity(Intent.createChooser(intent, title ?: "Share via"))
        }
    }

    fun getMimeTypeFromUri(uri: Uri) = context.contentResolver.getType(uri)

    private fun getMimeType(fileName: String): String {
        return URLConnection.guessContentTypeFromName(fileName) ?: "application/octet-stream"
    }
}

@Composable
actual fun ProvideContentSharer(
    content: @Composable (() -> Unit),
) {
    val context = LocalContext.current
    val sharer = remember { ContentSharer(context) }
    CompositionLocalProvider(
        LocalContentSharer provides sharer,
        content = content,
    )
}