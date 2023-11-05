package utils

sealed class ImageState {
    object Loading : ImageState()
    data class Loaded(val imageData: ByteArray) : ImageState() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Loaded

            if (!imageData.contentEquals(other.imageData)) return false

            return true
        }

        override fun hashCode(): Int {
            return imageData.contentHashCode()
        }
    }

    data class Error(val exception: Exception) : ImageState()

    object NotStarted : ImageState()
}