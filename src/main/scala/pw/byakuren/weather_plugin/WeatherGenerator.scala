package pw.byakuren.weather_plugin

import pw.byakuren.weather_plugin.types.WeatherType.WeatherType
import pw.byakuren.weather_plugin.types.{DayWeather, WeatherType}

import scala.collection.mutable
import scala.util.Random

object WeatherGenerator {

  val RAIN_CHANCE: Float = 0.30f
  val THUNDER_CHANCE: Float = RAIN_CHANCE * 0.35f //if it rains, there's a 35% for a thunderstorm.

  private val day_cache: mutable.Map[Long, DayWeather] = new mutable.HashMap[Long, DayWeather]()

  def day(day: Long, seed: Long): DayWeather = {
    day_cache.get(day) match {
      case Some(weather) => weather
      case None => {
        val random = new Random(seed)
        //each day has 4 random calls, one for each "segment" of the day. we need to advance the random up to this point.
        var i = 0L
        while (i < day*4) { //this is necessary as scala cannot do the other for loops with longs
          random.nextFloat()
          i+=1
        }
        //while we're at it, may as well generate tomorrow's too if its not already there
        val wt = new DayWeather(floatToWeather(random.nextFloat()),
          floatToWeather(random.nextFloat()),
          floatToWeather(random.nextFloat()),
          floatToWeather(random.nextFloat()))
        day_cache.put(day, wt)

        val wt2 = new DayWeather(floatToWeather(random.nextFloat()),
          floatToWeather(random.nextFloat()),
          floatToWeather(random.nextFloat()),
          floatToWeather(random.nextFloat()))
        day_cache.put(day + 1, wt2)
        wt
      }
    }
  }

  def floatToWeather(f: Float): WeatherType = {
    if (f <= THUNDER_CHANCE) {
      WeatherType.THUNDER
    } else if (f <= RAIN_CHANCE) {
      WeatherType.RAIN
    } else {
      WeatherType.CLEAR
    }
  }

  def clearCache: Unit = {
    day_cache.clear()
  }


}

