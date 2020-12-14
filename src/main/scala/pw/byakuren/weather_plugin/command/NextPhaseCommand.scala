package pw.byakuren.weather_plugin.command

import org.bukkit.ChatColor
import org.bukkit.command.{Command, CommandExecutor, CommandSender}
import org.bukkit.entity.Player

class NextPhaseCommand extends CommandExecutor {
  override def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
    sender match {
      case p: Player =>
        val time = p.getWorld.getTime
        val timeUntilNext = 6000-(time%6000)
        p.getWorld.setTime(p.getWorld.getFullTime+(timeUntilNext-100))
        p.sendMessage(s"${ChatColor.GREEN}Time advanced to just before next weather phase (${(timeUntilNext-100) max 100} ticks)")
        true
      case _ =>
        sender.sendMessage(s"${ChatColor.RED}This command is only available to players")
        false
    }
  }
}
