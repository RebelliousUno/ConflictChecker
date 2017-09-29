package uno.rebellious.minecraft.conflictchecker


data class Shapeless(val name: String, val ingredients: List<String>)

data class Shaped(val name: String, val ingredients: List<String>, val height: Int, val width: Int)