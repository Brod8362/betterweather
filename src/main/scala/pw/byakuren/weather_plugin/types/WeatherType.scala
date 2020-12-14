package pw.byakuren.weather_plugin.types

import org.bukkit.ChatColor
import org.bukkit.block.Biome

object WeatherType extends Enumeration {

  type WeatherType = Value
  val CLEAR, RAIN, THUNDER = Value

  val CLOUD_EMOJI = "☁"
  val SUN_EMOJI = "☀"
  val RAIN_EMOJI = "\uD83C\uDF27"
  val SNOW_EMOJI = "❄"
  val THUNDER_EMOJI = "⚡"

  val DESERT_BIOMES: Set[Biome] = Set(Biome.DESERT, Biome.DESERT_HILLS, Biome.DESERT_LAKES, Biome.SAVANNA, Biome.SAVANNA_PLATEAU,
    Biome.SHATTERED_SAVANNA, Biome.SHATTERED_SAVANNA_PLATEAU, Biome.BADLANDS, Biome.BADLANDS_PLATEAU, Biome.ERODED_BADLANDS,
    Biome.MODIFIED_BADLANDS_PLATEAU, Biome.MODIFIED_WOODED_BADLANDS_PLATEAU, Biome.WOODED_BADLANDS_PLATEAU)

  val SNOWY_BIOMES: Set[Biome] = Set(Biome.SNOWY_TUNDRA, Biome.ICE_SPIKES, Biome.SNOWY_TAIGA, Biome.SNOWY_TAIGA_HILLS, Biome.FROZEN_RIVER,
    Biome.SNOWY_BEACH, Biome.SNOWY_TAIGA_MOUNTAINS, Biome.SNOWY_MOUNTAINS)

  implicit def toWeatherString(t: Value, b: Biome): String = {
    t match {
      case WeatherType.CLEAR =>
        s"${toEmoji(t,b)} Sunny"
      case WeatherType.RAIN =>
        if (DESERT_BIOMES.contains(b)) {
          s"${toEmoji(t,b)} Overcast"
        } else if (SNOWY_BIOMES.contains(b)) {
          s"${toEmoji(t,b)} Snowy"
        } else {
          s"${toEmoji(t,b)} Rain"
        }
      case WeatherType.THUNDER =>
        s"${toEmoji(t,b)} Thunderstorm"
    }
  }

  implicit def toEmoji(t: Value, b: Biome): String = {
    t match {
      case WeatherType.CLEAR =>
        s"${ChatColor.GOLD}$SUN_EMOJI${ChatColor.RESET}"
      case WeatherType.RAIN =>
        if (DESERT_BIOMES.contains(b)) {
          s"${ChatColor.GRAY}$CLOUD_EMOJI${ChatColor.RESET}"
        } else if (SNOWY_BIOMES.contains(b)) {
          s"${ChatColor.AQUA}$SNOW_EMOJI${ChatColor.RESET}"
        } else {
          s"${ChatColor.BLUE}$RAIN_EMOJI${ChatColor.RESET}"
        }
      case WeatherType.THUNDER =>
        s"${ChatColor.RED}$THUNDER_EMOJI${ChatColor.RESET}"
    }
  }
}
