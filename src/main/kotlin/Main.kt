import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.model.ApiClient
import dto.StringAttribute
import io.appwrite.Client
import io.appwrite.extensions.toJson
import io.appwrite.models.Collection
import io.appwrite.services.Databases
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.launch
import org.jetbrains.skia.Color
import theme.*
import utils.CollapsedTopBarHomeScreen
import utils.ProfileImage
import utils.TypewriterText
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.security.SecureRandom
import javax.imageio.ImageIO
import kotlin.random.Random


@Composable
@Preview
fun App() {
    val qrCode = remember { mutableStateOf<ByteArray?>(null) }
    val email = remember { mutableStateOf("") }
    val data = remember { mutableStateOf("") }
    val pfp = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val isGenerated = remember { mutableStateOf(false) }
    val isAIVisible = remember { mutableStateOf(false) }
    val collection = remember { mutableStateOf<Collection?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val currentTheme = remember { mutableStateOf(ThemeMode.Black) }


    PalmApiTheme(currentTheme.value.name) {
        val aiImage = listOf("Let's Create with AI")
        Scaffold(topBar = {
            CollapsedTopBarHomeScreen(
                isImageVisible = pfp.value != "",
                imageUrl = pfp.value,
                name = name.value
            )
        }, modifier = Modifier.fillMaxSize().background(appGradient)) {
            AnimatedVisibility(!isAIVisible.value) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(modifier = Modifier.fillMaxWidth().weight(1f)) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(7.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = "\uD83D\uDCF1 Evolve with AI: Your ultimate Android companion! \uD83D\uDE80✨\n" +
                                        "Supercharge your device with our cutting-edge AI integration \uD83E\uDD16\uD83D\uDD0C," +
                                        " bringing you enhanced keyboard accessibility \uD83C\uDFB9 and powerful machine learning " +
                                        "capabilities \uD83D\uDCDA\uD83D\uDCA1. Experience the future of Android, where innovation meets" +
                                        " accessibility. Get ready to evolve your digital experience with Evolve with AI! " +
                                        "\uD83C\uDF1F\uD83D\uDD13 \n\n" +
                                        "Are you ready to embrace the power of innovation and take your Android experience " +
                                        "to new heights? \uD83C\uDF1F\uD83E\uDD16",
                                color = textColor,
                                fontSize = 12.sp,
                                softWrap = true,
                                modifier = Modifier
                                    .padding(horizontal = 20.dp)
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            TypewriterText(
                                texts = aiImage,
                                text = "",
                                modifier = Modifier,
                                delay = 50L
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            AnimatedVisibility(qrCode.value != null) {
                                convertByteArrayToImageBitmap(qrCode.value!!)?.let {
                                    Image(
                                        painter = it,
                                        contentDescription = null,
                                        modifier = Modifier.size(150.dp).weight(1f)
                                    )
                                }
                            }
                            AnimatedVisibility(qrCode.value == null) {
                                ProfileImage(
                                    imageUrl = "https://firebasestorage.googleapis.com/v0/b/palmapi-b548f.appspot.com/o/" +
                                            "qrcode.png?alt=media&token=f7de9b6d-a5aa-4342-8701-115a6157a183",
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(5.dp))
                                        .weight(1f)
                                )
                            }
                            Spacer(Modifier.height(20.dp))
                            AnimatedVisibility(!isGenerated.value) {
                                Button(
                                    onClick = {
                                        isGenerated.value = true
                                        coroutineScope.launch {
                                            try {
                                                data.value = generateString()
                                                qrCode.value = ApiClient.getQrCode(data.value)
                                                val client = Client()
                                                    .setEndpoint("https://cloud.appwrite.io/v1") // Your API Endpoint
                                                    .setProject("65469b6cbb0b40e8a44f") // Your project ID
                                                    .setKey("3b14bdc3f720db0253fe51192d31dc31df2f7051976dcaa760a94f81eb68525b25bfca177936b1cde4d8ad6fd6f87911aebcbaeba9ec6d7ec837a304ebcb0c8471beedd950ca2b0cac452e17c70594f455538b770a967ee73d2db954592fd632e81eff2be3b06c2e01f19ea0aaa05adc78a60c228e1fdf9453ec240b956f8b36") // Your secret API key

                                                val databases = Databases(client)

                                                collection.value = databases.createCollection(
                                                    databaseId = "generated-qr",
                                                    collectionId = data.value,
                                                    name = data.value,
                                                )
                                            } catch (e: Exception) {
                                                println("Appwrite Exception: $e")
                                            }
                                        }
                                    }, colors = ButtonDefaults.buttonColors(
                                        backgroundColor = buttonColor,
                                        contentColor = textColor
                                    )
                                ) {
                                    Text(
                                        "Generate QR Code",
                                        color = textColor,
                                        fontSize = 15.sp
                                    )
                                }
                            }
                            AnimatedVisibility(isGenerated.value) {
                                Button(
                                    onClick = {
                                        coroutineScope.launch {
                                            try {
                                                val client = Client()
                                                    .setEndpoint("https://cloud.appwrite.io/v1") // Your API Endpoint
                                                    .setProject("65469b6cbb0b40e8a44f") // Your project ID
                                                    .setKey("3b14bdc3f720db0253fe51192d31dc31df2f7051976dcaa760a94f81eb68525b25bfca177936b1cde4d8ad6fd6f87911aebcbaeba9ec6d7ec837a304ebcb0c8471beedd950ca2b0cac452e17c70594f455538b770a967ee73d2db954592fd632e81eff2be3b06c2e01f19ea0aaa05adc78a60c228e1fdf9453ec240b956f8b36") // Your secret API key

                                                val databases = Databases(client)

                                                val response = databases.listCollections(
                                                    databaseId = "mapping",
                                                )
                                                response.collections.forEach {
                                                    if (it.id == data.value) {
                                                        email.value = it.name
                                                    }
                                                }

                                                pfp.value = "https://avatars.githubusercontent.com/u/61358755?v=4"
                                                name.value = "Kailash"
                                                isAIVisible.value = true


                                            } catch (e: Exception) {
                                                println("Appwrite Exception: $e")
                                            }
                                        }
                                    }, colors = ButtonDefaults.buttonColors(
                                        backgroundColor = buttonColor,
                                        contentColor = textColor
                                    )
                                ) {
                                    Text(
                                        "Click Here once you see success on your phone",
                                        color = textColor,
                                        softWrap = true,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    }

                }
            }
            AnimatedVisibility(isAIVisible.value){
                TextToImage("https://avatars.githubusercontent.com/u/61358755?v=4")
            }
        }


    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Create With AI",
    ) {
        App()
    }
}

fun convertByteArrayToImageBitmap(byteArray: ByteArray): Painter? {
    try {
        val inputStream = ByteArrayInputStream(byteArray)
        val bufferedImage: BufferedImage = ImageIO.read(inputStream)
        return bufferedImage.toPainter()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


fun generateString(length: Int = 12): String {
    val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..length)
        .map { charset[Random.nextInt(0, charset.length)] }
        .joinToString("")

}




