package pw.byakuren.weather_plugin

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.{Bukkit, GameRule}
import org.bukkit.plugin.java.JavaPlugin
import scala.collection.JavaConverters._

class WeatherPlugin extends JavaPlugin {

  val config: FileConfiguration = getConfig


  override def onEnable(): Unit = {
    Bukkit.broadcastMessage("BetterWeather loaded")
    getServer.getPluginManager.registerEvents(new WeatherListener, this)
    weatherPluginControl(true)
  }

  override def onDisable(): Unit = {
    weatherPluginControl(false)
  }

  def weatherPluginControl(enabled: Boolean): Unit = {
    config.getStringList("worlds").asScala.map(getServer.getWorld(_))
      .filter(_ != null).foreach(_.setGameRule(GameRule.DO_WEATHER_CYCLE, enabled))
  }

}
