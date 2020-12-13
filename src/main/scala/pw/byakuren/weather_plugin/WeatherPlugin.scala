package pw.byakuren.weather_plugin

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.{Bukkit, GameRule, World}
import org.bukkit.plugin.java.JavaPlugin
import pw.byakuren.weather_plugin.command.{CurrentWorldCommand, ForecastCommand, ReseedCommand, WeatherSeedCommand, WeatherToggleCommand}

import scala.collection.JavaConverters._

class WeatherPlugin extends JavaPlugin {

  val config: FileConfiguration = getConfig

  override def onLoad(): Unit = {
    saveDefaultConfig()
  }

  override def onEnable(): Unit = {
    Bukkit.broadcastMessage("BetterWeather loaded")
    weatherPluginControl(true)
    Bukkit.getScheduler.scheduleSyncRepeatingTask(this, new BetterWeatherHandler(config.getLong("seed"), getWorlds), 40, 600)

    // register commands
    this.getCommand("forecast").setExecutor(new ForecastCommand(config))
    this.getCommand("weather_seed").setExecutor(new WeatherSeedCommand(config))
    this.getCommand("weather_control").setExecutor(new WeatherToggleCommand(weatherPluginControl))
    this.getCommand("curworld").setExecutor(new CurrentWorldCommand)
    this.getCommand("reseed").setExecutor(new ReseedCommand(config))
  }

  override def onDisable(): Unit = {
    weatherPluginControl(false)
    saveConfig()
  }

  def weatherPluginControl(enabled: Boolean): Unit = {
    for (world <- getWorlds) {
      val default = world.getGameRuleDefault(GameRule.DO_WEATHER_CYCLE)
      if (enabled) {
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, java.lang.Boolean.FALSE)
      } else {
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, default)
      }
    }
  }

  def getWorlds: Seq[World] = {
    config.getStringList("worlds").asScala.map(getServer.getWorld(_)).filter(_ != null).toSeq
  }

}
