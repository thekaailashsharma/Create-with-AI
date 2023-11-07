package utils

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme.COLLAPSED_TOP_BAR_HEIGHT
import theme.appGradient
import theme.buttonColor
import theme.textColor

@Composable
fun CollapsedTopBarHomeScreen(
    isImageVisible: Boolean,
    imageUrl: String,
    name: String = "",
    onLogout: () -> Unit = {}
) {
    Column {
        val isDropDownVisible = remember { mutableStateOf(false) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(appGradient)
                .height(COLLAPSED_TOP_BAR_HEIGHT)
                .padding(vertical = 10.dp, horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = textColor,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black
                    )
                ) {
                    if (name != "") {
                        append(name)
                    } else {
                        append("Create ")
                    }
                }
                withStyle(
                    SpanStyle(
                        color = textColor,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal
                    )
                ) {
                    if (name == "") {
                        append("with AI")
                    }
                }
            }, modifier = Modifier.padding(end = 10.dp))
            if (isImageVisible) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ProfileImage(
                        imageUrl = imageUrl,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .clickable(indication = null, interactionSource = MutableInteractionSource()) {
                                isDropDownVisible.value = !isDropDownVisible.value
                            },
                    )
                    if (isDropDownVisible.value) {
                        DropdownMenu(
                            expanded = isDropDownVisible.value,
                            onDismissRequest = {
                                isDropDownVisible.value = !isDropDownVisible.value
                            },
                            modifier = Modifier
                                .background(MaterialTheme.colors.primary)
                        ) {
                            Button(onClick = {
                                onLogout()
                            } , colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primary,
                                contentColor = textColor,
                            )){
                               Text(
                                   "Logout",
                                   color = textColor,
                                   fontSize = 15.sp
                               )
                            }
                        }
                    }
                }
            }

        }
        Divider(thickness = 1.dp, color = textColor.copy(0.5f))
    }
}
