package pw.byakuren.weather_plugin

import org.bukkit.World

import scala.util.Random

class BetterWeatherHandler(seed: Long, worlds: Seq[World]) extends Runnable {

  val random = new Random(seed)

  override def run(): Unit = {
    for (world <- worlds) {
      val day: Long = world.getFullTime/24000 //day
      val phase = world.getTime/4 //4 phases of time

    }
  }
}
