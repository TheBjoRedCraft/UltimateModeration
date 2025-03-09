package dev.thebjoredcraft.ultimatemoderation.discord

data class EmbedBuilder (
    var title: String = "Unknown",
    var description: String = "Unknown",
    var color: Int = 0,
    var footer: String = "Unknown",
    var author: String = "Unknown",
    var fields: MutableList<EmbedField> = mutableListOf()
) {
    fun edit(block: EmbedBuilder.() -> Unit): EmbedBuilder {
        block()
        return this
    }

    fun build(): String {
        val fields = fields.joinToString(", ") { it.build() }
        return "{title: \"$title\", description: \"$description\", color: $color, footer: \"$footer\", author: \"$author\", fields: [$fields]}"
    }
}
