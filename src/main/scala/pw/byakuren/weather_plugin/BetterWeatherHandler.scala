package pw.byakuren.weather_plugin

import org.bukkit.{Bukkit, World}
import org.bukkit.configuration.file.FileConfiguration
import pw.byakuren.weather_plugin.types.WeatherType

class BetterWeatherHandler(config: FileConfiguration, worlds: Seq[World]) extends Runnable {

  override def run(): Unit = {
    val seed = config.getLong("seed")
    for (world <- worlds) {
      val day: Long = world.getFullTime/24000 //day
      val phase = world.getTime/6000 //4 phases of time
      val wt = WeatherGenerator.day(day, seed)
      val timetable = IndexedSeq(wt.morning, wt.afternoon, wt.evening,wt.after_midnight)
      val len = 6000-(world.getTime%6000)
      timetable(phase.toInt) match {
        case WeatherType.CLEAR =>
          world.setStorm(false)
          world.setClearWeatherDuration(len.toInt)
//          Bukkit.broadcastMessage(s"clear $len")
        case WeatherType.RAIN =>
          world.setStorm(true)
          world.setWeatherDuration(len.toInt)
//          Bukkit.broadcastMessage(s"rain $len")
        case WeatherType.THUNDER =>
          world.setThundering(true)
          world.setThunderDuration(len.toInt)
//          Bukkit.broadcastMessage(s"thunder $len")
      }
    }
  }
}
