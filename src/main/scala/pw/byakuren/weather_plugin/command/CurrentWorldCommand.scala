package pw.byakuren.weather_plugin.command

import org.bukkit.ChatColor
import org.bukkit.command.{Command, CommandExecutor, CommandSender}
import org.bukkit.entity.Player

class CurrentWorldCommand extends CommandExecutor{
  override def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
    sender match {
      case p: Player =>
        sender.sendMessage(s"Current World: ${p.getWorld.getName}")
        true
      case _ =>
        sender.sendMessage(s"${ChatColor.RED}This command is only available to players")
        false
    }
  }
}
