package pw.byakuren.weather_plugin.types

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
        s"$SUN_EMOJI Sunny"
      case WeatherType.RAIN =>
        if (DESERT_BIOMES.contains(b)) {
          s"$CLOUD_EMOJI Overcast"
        } else if (SNOWY_BIOMES.contains(b)) {
          s"$SNOW_EMOJI Snowy"
        } else {
          s"$RAIN_EMOJI Rain"
        }
      case WeatherType.THUNDER =>
        s"$THUNDER_EMOJI Thunderstorm"
    }
  }

  implicit def toEmoji(t: Value, b: Biome): String = {
    t match {
      case WeatherType.CLEAR =>
        SUN_EMOJI
      case WeatherType.RAIN =>
        if (DESERT_BIOMES.contains(b)) {
          CLOUD_EMOJI
        } else if (SNOWY_BIOMES.contains(b)) {
          SNOW_EMOJI
        } else {
          RAIN_EMOJI
        }
      case WeatherType.THUNDER =>
        THUNDER_EMOJI
    }
  }
}
