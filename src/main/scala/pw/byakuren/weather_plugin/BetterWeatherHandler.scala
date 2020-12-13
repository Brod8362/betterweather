package pw.byakuren.weather_plugin

import org.bukkit.World

import scala.util.Random

class BetterWeatherHandler(seed: Long, worlds: Seq[World]) extends Runnable {

  val random = new Random(seed)

  override def run(): Unit = {
    
  }
}
