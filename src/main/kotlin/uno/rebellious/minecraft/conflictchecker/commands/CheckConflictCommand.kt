package uno.rebellious.minecraft.conflictchecker.commands

import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.ShapedRecipes
import net.minecraft.item.crafting.ShapelessRecipes
import net.minecraft.server.MinecraftServer
import net.minecraftforge.fml.common.registry.ForgeRegistries
import org.apache.logging.log4j.Level
import sun.security.provider.SHA
import uno.rebellious.minecraft.conflictchecker.ConflictChecker
import uno.rebellious.minecraft.conflictchecker.Shaped
import uno.rebellious.minecraft.conflictchecker.Shapeless
import java.io.File


class CheckConflictCommand : CommandBase() {

    override fun getRequiredPermissionLevel(): Int {
        return 0
    }

    override fun getName(): String {
        return "CheckConflicts"
    }

    override fun execute(server: MinecraftServer?, sender: ICommandSender?, args: Array<out String>?) {
        val recipes = ForgeRegistries.RECIPES
        val shapedList = ArrayList<Shaped>()
        val shapelessList = ArrayList<Shapeless>()
        recipes.keys.forEach { it ->
            ConflictChecker.logger?.log(Level.INFO, it)
            val item = recipes.getValue(it)
            when (item) {
                is ShapedRecipes -> shapedList.add(expandShapedRecipe(item))
                is ShapelessRecipes -> shapelessList.add(expandShapelessRecipes(item))
            }
        }

        File("recipes.txt").printWriter().use {
            out ->
            shapedList.forEachIndexed({ind: Int, it: Shaped ->
                out.print("Shaped $ind ${it.name}: ${it.height}x${it.width} [")
                it.ingredients.forEachIndexed { index, s ->  out.print(" $index - $s, ")}
                out.println("]")
            })
            shapelessList.forEach {
                out.println("Shapeless ${it.name}: ${it.ingredients}")
            }
        }

        // Get List of All Receipes
        // Put Recepies into a data class
        // Print out to a file
    }

    fun expandShapelessRecipes(item: ShapelessRecipes): Shapeless {
        ConflictChecker.logger?.log(Level.INFO, "Shapeless Recipe for: ${item.recipeOutput.displayName}")
        return Shapeless(item.recipeOutput.displayName, item.recipeItems.map { it.matchingStacks[0].displayName })
    }

    fun expandShapedRecipe(item: ShapedRecipes): Shaped {
        ConflictChecker.logger?.log(Level.INFO, "Shaped Recipe for: ${item.recipeOutput.displayName}")

        return Shaped(item.recipeOutput.displayName, item.ingredients
                .map { it ->
                    if (it.matchingStacks.isNotEmpty())
                        it.matchingStacks[0].displayName
                    else
                        "BLANK"
                }, item.height, item.width)
    }

    override fun getUsage(sender: ICommandSender?): String {
        return "conflictchecker.command.usage"
    }


}