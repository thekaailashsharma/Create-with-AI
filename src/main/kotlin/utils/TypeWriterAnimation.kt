package utils

import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import theme.textColor

@Composable
fun TypewriterText(
    modifier: Modifier = Modifier,
    texts: List<String>,
    text: String,
    delay: Long = 160,
    fontSize: TextUnit = 20.sp,
    softWrap: Boolean = false,
    afterAnimation: () -> Unit = {},
) {
    var textIndex by remember {
        mutableStateOf(0)
    }
    var textToDisplay by remember {
        mutableStateOf("")
    }

    LaunchedEffect(
        key1 = texts,
    ) {
        try {
            while (textIndex < texts.size) {
                if (textIndex == texts.size - 1) {
                    delay(1000)
                    afterAnimation()
                }
                delay(1000)
                texts[textIndex].forEachIndexed { charIndex, _ ->
                    textToDisplay = texts[textIndex]
                        .substring(
                            startIndex = 0,
                            endIndex = charIndex + 1,
                        )
                    delay(delay)
                }
                textIndex = (textIndex + 1)
                delay(6000)
            }
        } catch (e: Exception) {
            afterAnimation()
            textToDisplay = text
        }
    }

    Text(
        text = textToDisplay,
        fontSize = fontSize,
        modifier = modifier,
        softWrap = softWrap,
        color = textColor
    )
}