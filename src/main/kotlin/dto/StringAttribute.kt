package dto

data class StringAttribute(
    val array: Boolean,
    val default: String,
    val error: String,
    val key: String,
    val required: Boolean,
    val size: Int,
    val status: String,
    val type: String
)