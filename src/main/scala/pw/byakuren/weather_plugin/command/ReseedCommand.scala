package pw.byakuren.weather_plugin.command

import org.bukkit.ChatColor
import org.bukkit.command.{Command, CommandExecutor, CommandSender}
import org.bukkit.configuration.file.FileConfiguration
import pw.byakuren.weather_plugin.WeatherGenerator

class ReseedCommand(config: FileConfiguration) extends CommandExecutor{
  override def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
    args.headOption match {
      case Some("confirm") =>
        val ns = (Math.random*Long.MaxValue).toLong
        config.set("seed", ns)
        sender.sendMessage(s"${ChatColor.GREEN}New weather seed is $ns.")
        WeatherGenerator.clearCache
        true
      case _ =>
        sender.sendMessage(s"${ChatColor.DARK_RED}Re-seeding the plugin will cause current and future weather to change. If you are sure you want to do this, type" +
          s"${ChatColor.RESET} /reseed confirm")
        true
    }
  }
}
