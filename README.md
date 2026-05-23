# HelloPlugin

A simple Paper plugin that greets players with commands and a welcome title on join.

## Commands

| Command           | Description                           | Permissions |
|-------------------|---------------------------------------|-------------|
| `/hello reload`   | reload the messages from config.yml   | op          |
| `/hello`          | Greets you privately                  | user        |
| `/hello world`    | Broadcasts your greetings to everyone | user        |
| `/hello <player>` | Sends greetings to a specific player  | user        |

## Configuration

After the first run, a `config.yml` will be generated in `plugins/HelloPlugin/`.

```yaml
messages:
  reload: "El config se ha recargado!"
  hello: "Que gusto verte de nuevo {player}!"
  hello-world: "{player} le manda saludos a todos!"
  hello-world-cooldown: "Debes esperar {cooldown} segundos para usar este comando de nuevo!"
  hello-player: "{player} te manda saludos!"
  join: "Bienvenido {player}!"
  not-player: "Solo los jugadores pueden saludar!"
  offline: "El jugador no esta conectado!"

cooldown:
  hello-world: 5000 #milliseconds
```

> When updating from a previous version, only change the .jar for the new version. The config will be updated automatically with any new messages or cooldowns, while keeping your custom messages and cooldown values.

## Download

Go to the [Releases](../../releases/latest) page and download the latest `.jar`.

## Requirements

- Minecraft 26.1.2
- Paper build #60+
- Java 25

## Installation

1. Download the `.jar`
2. Place it in your server's `plugins/` folder
3. Restart the server

## License

MIT
