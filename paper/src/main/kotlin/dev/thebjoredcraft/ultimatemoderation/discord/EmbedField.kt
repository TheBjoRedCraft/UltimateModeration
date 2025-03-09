package dev.thebjoredcraft.ultimatemoderation.discord

data class EmbedField (
    val name: String,
    val value: String,
    val inline: Boolean
) {
    fun edit(block: EmbedField.() -> Unit): EmbedField {
        block()
        return this
    }

    fun build(): String {
        return """{"name": "$name", "value": "$value", "inline": $inline}"""
    }
}