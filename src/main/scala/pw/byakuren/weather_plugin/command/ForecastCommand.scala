package pw.byakuren.weather_plugin.command

import org.bukkit.command.{Command, CommandExecutor, CommandSender}

class ForecastCommand extends CommandExecutor {
  override def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
    true
  }
}
