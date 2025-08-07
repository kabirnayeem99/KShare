package io.github.kabirnayeem99.kshare_sample

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KShare",
    ) {
        App()
    }
}