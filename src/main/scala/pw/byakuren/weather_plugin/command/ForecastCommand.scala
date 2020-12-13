package pw.byakuren.weather_plugin.command

import org.bukkit.block.Biome
import org.bukkit.command.{Command, CommandExecutor, CommandSender}
import org.bukkit.entity.Player
import org.bukkit.ChatColor
import pw.byakuren.weather_plugin.WeatherGenerator
import pw.byakuren.weather_plugin.types.WeatherType
import pw.byakuren.weather_plugin.types.WeatherType.WeatherType

class ForecastCommand(seed: Long) extends CommandExecutor {

  val DESERT_BIOMES: Set[Biome] = Set(Biome.DESERT, Biome.DESERT_HILLS, Biome.DESERT_LAKES, Biome.SAVANNA, Biome.SAVANNA_PLATEAU,
    Biome.SHATTERED_SAVANNA, Biome.SHATTERED_SAVANNA_PLATEAU, Biome.BADLANDS, Biome.BADLANDS_PLATEAU, Biome.ERODED_BADLANDS,
    Biome.MODIFIED_BADLANDS_PLATEAU, Biome.MODIFIED_WOODED_BADLANDS_PLATEAU, Biome.WOODED_BADLANDS_PLATEAU)

  val SNOWY_BIOMES: Set[Biome] = Set(Biome.SNOWY_TUNDRA, Biome.ICE_SPIKES, Biome.SNOWY_TAIGA, Biome.SNOWY_TAIGA_HILLS, Biome.FROZEN_RIVER,
    Biome.SNOWY_BEACH, Biome.SNOWY_TAIGA_MOUNTAINS)

  val CLOUD_EMOJI = "☁"
  val SUN_EMOJI = "☀"
  val RAIN_EMOJI = "\uD83C\uDF27"
  val SNOW_EMOJI = "❄"
  val THUNDER_EMOJI = "⚡"

  override def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
    //NOTE - show "rain" or "snow" depending on the user's biome when they execute the command. in desert, show "cloudy"
    sender match {
      case p: Player => {
        val pos = p.getLocation
        val x = pos.getBlockX
        val y = pos.getBlockY
        val z = pos.getBlockZ
        val biome = p.getWorld.getBiome(x, y, z)

        val day: Long =
          args.headOption match {
            case Some("week") =>
              sender.sendMessage(weekPreview(p.getWorld.getFullTime/24000, biome))
              return true
            case Some(str) =>
              str.toLongOption match {
                case Some(int) => int
                case None =>
                  sender.sendMessage(s"${ChatColor.RED}Invalid day number")
                  return false
              }
            case _ => p.getWorld.getFullTime / 24000
          }

        val wt = WeatherGenerator.day(day, seed)

        val gl = ChatColor.GOLD
        val rst = ChatColor.RESET

        val msg = s"${gl}Day $rst$day$gl Weather Forecast" +
          s"\n${gl}Morning: $rst${weatherToString(wt.morning, biome)}" +
          s"\n${gl}Afternoon: $rst${weatherToString(wt.afternoon, biome)}" +
          s"\n${gl}Evening: $rst${weatherToString(wt.evening, biome)}" +
          s"\n${gl}Late Night: $rst${weatherToString(wt.after_midnight, biome)}"
        sender.sendMessage(msg)
        true
      }
      case _ => sender.sendMessage(s"${ChatColor.RED}Only players can use this command.")
    }
    true
  }

  def weatherToString(weather: WeatherType, biome: Biome): String = {
    weather match {
      case WeatherType.CLEAR =>
        s"$SUN_EMOJI Sunny"
      case WeatherType.RAIN =>
        if (DESERT_BIOMES.contains(biome)) {
          s"$CLOUD_EMOJI Overcast"
        } else if (SNOWY_BIOMES.contains(biome)) {
          s"$SNOW_EMOJI Snowy"
        } else {
          s"$RAIN_EMOJI Rain"
        }
      case WeatherType.THUNDER =>
        s"$THUNDER_EMOJI Thunderstorm"
    }
  }

  def shortWeatherString(weather: WeatherType, biome: Biome): String = {
    weatherToString(weather, biome).head.toString
  }

  def dayPreview(day: Long, b: Biome): String = {
    val wt = WeatherGenerator.day(day, seed)
    s"${ChatColor.GOLD}Day ${ChatColor.RESET}$day ${ChatColor.GOLD}: " +
      s"${shortWeatherString(wt.morning, b)}${shortWeatherString(wt.afternoon, b)}"+
      s"${shortWeatherString(wt.evening,b)}${shortWeatherString(wt.after_midnight,b)}"
  }

  def weekPreview(day: Long, biome: Biome): String = {
    (day to day+7).map(dayPreview(_, biome)).mkString(s"${ChatColor.UNDERLINE}Week Preview${ChatColor.RESET}\n", "\n", "")
  }
}
