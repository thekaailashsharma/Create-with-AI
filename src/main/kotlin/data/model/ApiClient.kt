package data.model

import com.google.gson.annotations.SerializedName
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.gson.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.*
import kotlinx.serialization.Serializable

object ApiClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
           GsonSerializer(){
                setPrettyPrinting()
                disableHtmlEscaping()
            }
            json()
        }
        install(Logging)
        install(WebSockets)
        install(HttpTimeout) {
            requestTimeoutMillis = 1000000
        }
    }

    suspend fun getQrCode(
        text: String,
        centerUrl: String = "https://firebasestorage.googleapis.com/v0/b/palmapi-b548f.appspot.com/o/elogo.jpeg?alt=media%26token=a1763f07-c79f-4b1a-b02e-a88c4cb483df",
        dark: String = "22f55a",
        light: String = "000000"
    ): ByteArray? {
        return try {
            client.get {
                url("https://quickchart.io/qr?text=$text&centerImageUrl=$centerUrl&dark=$dark&light=$light")
                headers {
                    append("Content-Type", "application/json")
                }
            }.body()
        } catch (e: Exception) {
            null
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun getImage(imageFromText: ImageFromText): ByteArray? {
        return try {
            val a = client.post {
                url("https://api-inference.huggingface.co/models/stabilityai/stable-diffusion-xl-base-1.0")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(imageFromText)
                headers {
                    this.append("Authorization", "Bearer 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")
                    this.append("Content-Type", "application/json")
                }
            }.body<ByteArray>()
            println("AAAAAA is $a")
            a
        } catch (e: Exception) {
            println("AAAAAA is $e")
            null
        }
    }
}

@Serializable
data class ImageFromText(
    @SerializedName("inputs")
    val inputs: String?
)
