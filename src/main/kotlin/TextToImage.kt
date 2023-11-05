import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.model.ApiClient
import data.model.ImageFromText
import kotlinx.coroutines.launch
import theme.appGradient
import theme.blueTint
import theme.greenText
import theme.textColor
import utils.CollapsedTopBarHomeScreen
import utils.ImageState
import utils.ProfileImage
import utils.RepeatedCard

@Composable
fun TextToImage(imageUrl: String) {
    val text = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val imageData = remember {
        mutableStateOf<ByteArray?>(null)
    }
    val isAnimationVisible = remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()

    val imageState = remember { mutableStateOf<ImageState>(ImageState.NotStarted) }
    Scaffold(bottomBar = {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(0.85f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val containerColor = MaterialTheme.colors.primary
                OutlinedTextField(value = text.value, onValueChange = {
                    text.value = it
                }, label = {
                    Text(text = "Ask me anything", color = textColor)
                }, shape = RoundedCornerShape(20.dp), colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = containerColor,
                    textColor = textColor,
                    cursorColor = greenText,
                ), modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
            ) {
                Icon(Icons.Filled.Send,
                    contentDescription = "Send",
                    tint = blueTint,
                    modifier = Modifier
                        .size(39.dp)
                        .padding(end = 10.dp)
                        .clickable {
                            imageState.value = ImageState.Loading
                            coroutineScope.launch {
                                val imageDatas = ApiClient.getImage(
                                    ImageFromText(
                                        text.value.text
                                    )
                                )
                                if (imageDatas != null) {
                                    imageState.value = ImageState.Loaded(imageDatas)
                                    imageData.value = imageDatas
                                } else {
                                    imageState.value = ImageState.Error(Exception("Error Image Loading"))
                                }

                            }
                        }
                )
            }
            Spacer(Modifier.height(15.dp))
        }
    }, modifier = Modifier.padding(vertical = 10.dp)) {
        Box {
            println(it)
            if (imageState.value is ImageState.NotStarted) {
                Column(
                    modifier = Modifier
                        .background(appGradient).fillMaxSize().padding(it).padding(top = 150.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = textColor, fontSize = 30.sp, fontWeight = FontWeight.Black
                            )
                        ) {
                            append("Let's Generate ")
                        }
                        append("\n")
                        withStyle(
                            SpanStyle(
                                color = textColor, fontSize = 19.sp, fontWeight = FontWeight.Normal
                            )
                        ) {
                            append("AI Powered Images")
                        }
                    })
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "I'm your assistant.",
                        color = textColor,
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        RepeatedCard(
                            icon = "https://firebasestorage.googleapis.com/v0/b/palmapi-b548f.appspot.com/o/beta.png?alt=media&token=073dac0d-6de7-4998-abe6-540df47b4ad4",
                            description = "I'm still under development",
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        RepeatedCard(
                            icon = "https://firebasestorage.googleapis.com/v0/b/palmapi-b548f.appspot.com/o/remember.png?alt=media&token=6512db84-f4a7-4ec5-9e33-778b89d06cf5",
                            description = "I can remember our conversations",
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        RepeatedCard(
                            icon = "https://firebasestorage.googleapis.com/v0/b/palmapi-b548f.appspot.com/o/privacy.png?alt=media&token=ee397082-ac7f-4757-9b40-a3a24f70e1c6",
                            description = "Your Privacy is completely secured",
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        RepeatedCard(
                            icon = "https://firebasestorage.googleapis.com/v0/b/palmapi-b548f.appspot.com/o/tip.png?alt=media&token=dd665427-f899-4cda-9123-0ffcb3521687",
                            description = "Images are generated using AI",
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                }
            } else {
                Column(
                    modifier = Modifier.background(appGradient).fillMaxSize()
                        .then(if (isAnimationVisible.value) Modifier.blur(10.dp) else Modifier)
                ) {

                    when (imageState.value) {
                        is ImageState.Loading -> {
                            // Display loading indicator or message
                            isAnimationVisible.value = true
                        }

                        is ImageState.Loaded -> {
                            // Display the loaded image
                            isAnimationVisible.value = false
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                imageData.value?.let { it1 ->
                                    convertByteArrayToImageBitmap(it1)?.let {
                                        Image(
                                            painter = it,
                                            contentDescription = null,
                                            modifier = Modifier.size(400.dp).weight(1f)
                                        )
                                    }
                                }
                            }
                        }

                        is ImageState.Error -> {
                            isAnimationVisible.value = false
                            // Handle error state
                            val error = (imageState as ImageState.Error).exception
                            Text(text = "Error: ${error.message}")
                        }

                        else -> {
                            isAnimationVisible.value = false
                        }
                    }
                }
            }
            if (isAnimationVisible.value) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = greenText)
                }
            }
        }

    }
}



