package pw.byakuren.weather_plugin

import org.bukkit.Bukkit
import org.bukkit.event.weather.WeatherChangeEvent
import org.bukkit.event.{EventHandler, Listener}

class WeatherListener extends Listener {

  @EventHandler
  def onWeatherChange(event: WeatherChangeEvent) {
    Bukkit.broadcastMessage("helo, the weather has changed!")
  }



}
