package io.github.kabirnayeem99.kshare

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Cross-platform utility for sharing text and files via native system share dialogs.
 *
 * Use this class to share plain text or files seamlessly on Android and iOS.
 *
 * @constructor Platform-specific implementation is provided via `expect/actual`.
 */
expect class ContentSharer {

    /**
     * Shares plain text using the platform's native share UI.
     *
     * @param text The text content to share.
     * @param title Optional chooser dialog title.
     * @return [Result] indicating success or failure of the operation.
     */
    fun shareText(text: String, title: String? = null): Result<Unit>

    /**
     * Shares a file given its name, content bytes, and MIME type.
     *
     * @param fileName The name of the file to share (used for temporary storage).
     * @param fileBytes Byte content of the file.
     * @param mimeType MIME type of the file (e.g. "application/pdf").
     * @param title Optional chooser dialog title.
     * @return [Result] indicating success or failure of the operation.
     */
    fun shareFile(
        fileName: String, fileBytes: ByteArray, mimeType: String, title: String? = null
    ): Result<Unit>

    /**
     * Shares a file by its absolute file system path.
     *
     * @param filePath Absolute path to the file to be shared.
     * @param title Optional chooser dialog title.
     * @return [Result] indicating success or failure of the operation.
     */
    fun shareFileByPath(filePath: String, title: String?): Result<Unit>
}

/**
 * [androidx.compose.runtime.CompositionLocal] providing the current [ContentSharer] instance.
 *
 * This must be provided by [ProvideContentSharer] at a suitable scope.
 */
val LocalContentSharer = staticCompositionLocalOf<ContentSharer> {
    error("No ContentSharer provided")
}

/**
 * Provides a [ContentSharer] implementation to the composition.
 *
 * Wrap your app or UI hierarchy with this to enable sharing features via
 * [LocalContentSharer.current].
 *
 * @param content Composable content that can access [LocalContentSharer].
 */
@Composable
expect fun ProvideContentSharer(
    content: @Composable () -> Unit
)
