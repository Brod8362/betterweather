package pw.byakuren.weather_plugin.command

import org.bukkit.ChatColor
import org.bukkit.command.{Command, CommandExecutor, CommandSender}

class WeatherToggleCommand(fn: Boolean => Unit) extends CommandExecutor{
  override def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
    args.headOption match {
      case Some("on") =>sender.sendMessage(s"${ChatColor.GREEN}BetterWeather control enabled")
        fn(true)
        true
      case Some("off") =>sender.sendMessage(s"${ChatColor.GREEN}BetterWeather control disabled")
        fn(false)
        true
      case _ => sender.sendMessage(s"${ChatColor.RED}Invalid Arguments - on or off only")
        false
    }
  }
}
