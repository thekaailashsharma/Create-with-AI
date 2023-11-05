package theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp

val appGradient: Brush
    @Composable
    get() = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colors.primary,
            MaterialTheme.colors.primary.copy(0.9f),
            MaterialTheme.colors.primary.copy(0.8f),
            MaterialTheme.colors.primary.copy(0.7f),
            MaterialTheme.colors.primary.copy(0.6f),
            MaterialTheme.colors.primary.copy(0.5f),
            MaterialTheme.colors.primary.copy(0.5f),
        ),
        tileMode = TileMode.Clamp
    )

val textColor: Color
    @Composable
    get() = MaterialTheme.colors.surface

val blueTint: Color
    @Composable
    get() = Color(0xFF5FA3F7)

val buttonColor: Color
    @Composable
    get() = MaterialTheme.colors.primaryVariant

val CardColor: Color
    @Composable
    get() = MaterialTheme.colors.secondary

val greenText: Color
    @Composable
    get() = Color(0xFF59FD59)

val COLLAPSED_TOP_BAR_HEIGHT = 80.dp
val EXPANDED_TOP_BAR_HEIGHT = 360.dp