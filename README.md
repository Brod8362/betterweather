BetterWeather
=============
Spigot plugin to make minecraft's weather more interesting

### Usage
Copy the plugin jar into the `plugins` folder in your bukkit or spigot server.

### Commands
Command | Description |
--------|-------------|
`/forecast` | View the forecast for today, and a preview of tomorrow.
`/forecast week` | View the forecast for the next week.
`/reseed` | Randomize the weather seed. 
`/weather_control (on/off)` | Turn on or off the weather control. Turning it off will relinquish control to the world default.
`/weather_seed` | View the weather seed
`/curworld` | View the current world name. Useful for the config file.
`/nextphase` | Skip to 5 seconds before the next weather phase. Intended as a debug command.

### Configuration (config.yml)
Key | Expected Value | Description | Example | 
----|----------------|-------------|---------|
`seed` | long | The weather seed. This can be randomized with the `/reseed` command. | `95498208` |
`worlds` | string list | The worlds that the plugin controls. Use the `/curworld` command to get the name of the world you're currently in. | `["world","other_world"]` |

### Permissions 
Command | Permission | Default |
--------|------------|---------|
`/forecast` | `betterweather.forecast` | all players
`/curworld` | `betterweather.curworld` | all players
`/reseed` | `betterweather.reseed` | op only
`/weather_control` | `betterweather.weather_control` | op only
`/weather_seed` | `betterweather.weather_seed` | op only
`/nextphase` | `betterweather.timeskip` | op only

### Default Config
```yaml
seed: 91850195

#List of worlds the plugin should apply to. Defaults to only the overworld. if the world is invalid, it will fail silently.
worlds: ["world"]
```