package pw.byakuren.weather_plugin

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.world.TimeSkipEvent
import org.bukkit.event.{EventHandler, Listener}
import org.bukkit.{Bukkit, GameRule, World}
import org.bukkit.plugin.java.JavaPlugin
import pw.byakuren.weather_plugin.command.{CurrentWorldCommand, ForecastCommand, ReseedCommand, WeatherSeedCommand, WeatherToggleCommand}

import scala.collection.JavaConverters._

class WeatherPlugin extends JavaPlugin with Listener {

  val config: FileConfiguration = getConfig
  private var taskId = -1
  private var lastReschedule = -1L
  val wHandler: BetterWeatherHandler = new BetterWeatherHandler(config, getWorlds)

  override def onLoad(): Unit = {
    saveDefaultConfig()
  }

  override def onEnable(): Unit = {
//    Bukkit.broadcastMessage("BetterWeather loaded")
    weatherPluginControl(true)

    // register commands
    this.getCommand("forecast").setExecutor(new ForecastCommand(config))
    this.getCommand("weather_seed").setExecutor(new WeatherSeedCommand(config))
    this.getCommand("weather_control").setExecutor(new WeatherToggleCommand(weatherPluginControl))
    this.getCommand("curworld").setExecutor(new CurrentWorldCommand)
    this.getCommand("reseed").setExecutor(new ReseedCommand(config))

    //register timeskip detection
    getServer.getPluginManager.registerEvents(this, this)
    reschedule()
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

  def reschedule(): Unit = {
    wHandler.run()
    if (taskId != -1) {
      Bukkit.getScheduler.cancelTask(taskId)
    }
    val delay = 6000-(getWorlds.head.getTime%6000)
    taskId = Bukkit.getScheduler.scheduleSyncRepeatingTask(this, wHandler, delay, 6002)
  }


  @EventHandler
  def timeSkipEvent(tsE: TimeSkipEvent): Unit = {
    if (getWorlds.head.getFullTime-lastReschedule>20) {
//      Bukkit.broadcastMessage("timeskip, adjusting weather cycle")
      reschedule()
      lastReschedule=getWorlds.head.getFullTime
    } else {
      //rate limiting
    }
  }
}
