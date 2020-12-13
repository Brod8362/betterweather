package pw.byakuren.weather_plugin.types

import pw.byakuren.weather_plugin.types.WeatherType.WeatherType

class DayWeather(val morning: WeatherType, val afternoon: WeatherType, val evening: WeatherType, val after_midnight: WeatherType) {

}
