package pw.byakuren.weather_plugin.command

import org.bukkit.command.{Command, CommandExecutor, CommandSender}
import org.bukkit.configuration.file.FileConfiguration

class WeatherSeedCommand(config: FileConfiguration) extends CommandExecutor{
  override def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
    sender.sendMessage("Weather Seed: "+config.getLong("seed").toString)
    true
  }
}
