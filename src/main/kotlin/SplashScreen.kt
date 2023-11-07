import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import theme.*

@Composable
fun SplashScreen() {
    val currentTheme = remember { mutableStateOf(ThemeMode.Black) }
    val isAnimationPlaying = remember { mutableStateOf(false) }
    PalmApiTheme(currentTheme.value.name) {
        LaunchedEffect(Unit){
            delay(1)
            isAnimationPlaying.value = true
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(appGradient)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource("drawable/appicon.png"),
                    contentDescription = "Logo",
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(20.dp))
                AnimatedVisibility(
                    visible = isAnimationPlaying.value,
                    enter = slideInHorizontally(tween(150), initialOffsetX = {
                        it
                    })
                ) {
                    Text(
                        text = "Evolve with Ai",
                        color = textColor,
                        fontSize = 25.sp,
                        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                    )

                }
            }
        }
    }
}