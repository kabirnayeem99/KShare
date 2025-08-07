package io.github.kabirnayeem99.kshare_sample

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kabirnayeem99.kshare.LocalContentSharer
import io.github.kabirnayeem99.kshare.ProvideContentSharer
import org.jetbrains.compose.ui.tooling.preview.Preview

private val PalestineColorScheme = lightColorScheme(
    primary = Color(0xFFB1001C),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF007A3D),
    onSecondary = Color(0xFFE6F2ED),
    background = Color(0xFFF8F8F8),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1C1C1C),
    error = Color(0xFF000000),
    outline = Color(0xFFD3D3D3),
    tertiary = Color(0xFF2E2E2E)
)

@Composable
@Preview
fun App() {
    val colorScheme = remember { PalestineColorScheme }
    val shapes = remember {
        Shapes(
            extraSmall = ShapeDefaults.ExtraSmall.copy(CornerSize(4.dp)),
            small = ShapeDefaults.Small.copy(CornerSize(6.dp)),
            medium = ShapeDefaults.Medium.copy(CornerSize(8.dp)),
            large = ShapeDefaults.Large.copy(CornerSize(12.dp)),
            extraLarge = ShapeDefaults.ExtraLarge.copy(CornerSize(28.dp))
        )
    }

    ProvideContentSharer {
        MaterialTheme(
            colorScheme = colorScheme,
            shapes = shapes,
        ) {
            Column(
                modifier = Modifier.background(animatedPalestineGradient()).safeContentPadding()
                    .fillMaxSize().padding(horizontal = 16.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                val contentSharer = LocalContentSharer.current

                Spacer(Modifier.height(20.dp))
                Text(
                    "\uD83C\uDF49",
                    fontSize = 60.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(20.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Button(
                        onClick = {
                            contentSharer.shareText(
                                text = "Stop the attacks on Gaza!\n" + "Take action for Palestine",
                                title = "Show Solidarity: Share Support for Gaza"
                            )
                        },
                        shape = MaterialTheme.shapes.medium,
                    ) {
                        Text("Share Text")
                    }

                    Button(
                        onClick = { },
                        shape = MaterialTheme.shapes.medium,
                    ) {
                        Text("Share Files")
                    }
                }

                Spacer(Modifier.height(200.dp))
            }
        }
    }
}

@Composable
fun animatedPalestineGradient(): Brush {
    val infiniteTransition = rememberInfiniteTransition()

    val redColor by infiniteTransition.animateColor(
        initialValue = Color(0xFFFFC1C3),
        targetValue = Color(0xFFE57373),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val whiteColor by infiniteTransition.animateColor(
        initialValue = Color(0xFFF8F8F8),
        targetValue = Color(0xFFFFFFFF),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 700, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val greenColor by infiniteTransition.animateColor(
        initialValue = Color(0xFFBDE8D1),
        targetValue = Color(0xFF81C784),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1300, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val grayColor by infiniteTransition.animateColor(
        initialValue = Color(0xFFCCCCCC),
        targetValue = Color(0xFF9E9E9E),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    return Brush.verticalGradient(
        colors = listOf(redColor, whiteColor, greenColor, grayColor)
    )
}
