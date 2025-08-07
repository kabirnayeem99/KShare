package io.github.kabirnayeem99.kshare

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSURL
import platform.Foundation.create
import platform.Foundation.writeToFile
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController

actual class ContentSharer(
    private val viewController: UIViewController
) {
    actual fun shareText(text: String, title: String?): Result<Unit> {
        return runCatching {
            val activityVC = UIActivityViewController(listOf(text), null)
            viewController.presentViewController(activityVC, animated = true, completion = null)
        }
    }

    @OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
    actual fun shareFile(
        fileName: String, fileBytes: ByteArray, mimeType: String, title: String?
    ): Result<Unit> {
        return runCatching {
            memScoped {
                val dir = NSTemporaryDirectory()
                val path = dir + fileName

                fileBytes.usePinned { pinned ->
                    val nsData = NSData.create(
                        bytes = pinned.addressOf(0), length = fileBytes.size.toULong()
                    )
                    nsData.writeToFile(path, true)
                }

                val url = NSURL.fileURLWithPath(path)
                val activityVC = UIActivityViewController(listOf(url), null)
                viewController.presentViewController(activityVC, animated = true, completion = null)
            }
        }
    }


    actual fun shareFileByPath(filePath: String, title: String?): Result<Unit> {
        return runCatching {
            val url = NSURL.fileURLWithPath(filePath)
            val activityVC = UIActivityViewController(listOf(url), null)
            viewController.presentViewController(activityVC, animated = true, completion = null)
        }
    }
}

@Composable
actual fun ProvideContentSharer(
    content: @Composable (() -> Unit),
) {
    val sharer = ContentSharer(viewController = getCurrentUIViewController())
    CompositionLocalProvider(
        LocalContentSharer provides sharer,
        content = content
    )
}

fun getCurrentUIViewController(): UIViewController {
    val window = UIApplication.sharedApplication.keyWindow
        ?: throw IllegalStateException("No keyWindow found")
    var vc = window.rootViewController ?: throw IllegalStateException("No rootViewController")
    while (vc.presentedViewController != null) {
        vc = vc.presentedViewController!!
    }
    return vc
}