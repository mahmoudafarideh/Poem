package m.a.nobahar.ui.shared.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.compositionLocalOf

val LocalWindowSize = compositionLocalOf<WindowSizeClass> {
    throw IllegalStateException()
}