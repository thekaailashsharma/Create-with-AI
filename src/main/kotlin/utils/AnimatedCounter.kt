package utils

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import theme.textColor

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedCounter(
    count: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.body1
) {
    var oldCount by remember {
        mutableStateOf(count)
    }
    SideEffect {
        oldCount = count
    }
    Row(modifier = modifier) {
        val countString = count.toString()
        val oldCountString = oldCount.toString()
        for (i in countString.indices) {
            val oldChar = oldCountString.getOrNull(i)
            val newChar = countString[i]
            val chars = if (oldChar == newChar) {
                oldCountString[i]
            } else {
                countString[i]
            }
            AnimatedContent(
                targetState = chars,
                transitionSpec = {
                    slideInVertically { it } with slideOutVertically { -it }
                }, label = ""
            ) { char ->
                Text(
                    text = char.toString(),
                    style = style,
                    softWrap = false,
                    fontSize = 20.sp,
                    color = textColor
                )
            }
        }
    }
}