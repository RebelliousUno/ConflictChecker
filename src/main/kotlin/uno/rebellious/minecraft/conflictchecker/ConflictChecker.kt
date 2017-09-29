package uno.rebellious.minecraft.conflictchecker

import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.event.FMLServerStartingEvent
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.Logger
import uno.rebellious.minecraft.conflictchecker.commands.CheckConflictCommand


@Mod(modid = ConflictChecker.MOD_ID, name = ConflictChecker.NAME, version = ConflictChecker.VERSION, modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter")
object ConflictChecker {
    const val MOD_ID = "conflictchecker"
    const val NAME = "Conflict Checker"
    const val VERSION = "@VERSION@"

    @Mod.Instance
    var instance: ConflictChecker? = null

    var logger: Logger? = null

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        logger = event.modLog
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {

    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {

    }

    @Mod.EventHandler
    fun serverStarting(event: FMLServerStartingEvent) {
        logger?.log(Level.INFO, "${MOD_ID}Register Commands")
        //ClientCommandHandler.instance.registerCommand(CheckConflictCommand()) // This is probably wrong
        event.registerServerCommand(CheckConflictCommand())
    }
}
