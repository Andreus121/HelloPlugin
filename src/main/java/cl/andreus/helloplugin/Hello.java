package cl.andreus.helloplugin;

//imports para plugin
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.NullMarked;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;

//imports de java
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.HashMap;

@NullMarked
public class Hello implements BasicCommand{
    //guarda al padre para acceder a "config.yml"
    private final HelloPlugin plugin;
    //HasMap para los cooldowns de //hello world
    private final HashMap<UUID, Long> cooldowns = new HashMap<>();

    //constructor
    public Hello(HelloPlugin plugin) {
        this.plugin = plugin;
    }

    //comandos del plugin
    //las cosas que muestra al escribir el comando
    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        //sugerencias para el comando en la posición 1
        if (args.length == 1) {
            //Collection de jugadores
            Collection<? extends Player> players = Bukkit.getOnlinePlayers();
            //crear una lista de strings con "world"
            List<String> optionsList = new ArrayList<String>(List.of("world","reload"));
            //a la lista añadirle como strings los nombres de todos los jugadores conectados
            for(Player p: players){
                optionsList.add(p.getName());
            }
            //retornar todas las opciones
            return optionsList;
        }
        //retornar lista vacía
        return java.util.List.of();
    }

    //función a ejecutar si usan el comando
    @Override
    public void execute(CommandSourceStack source, String[] args){
        CommandSender sender = source.getSender(); //quien ejecutó el mensaje
        Entity executor = source.getExecutor(); //qué entidad ejecutó el comando?

        //verificar que fue un jugador
        if (!(executor instanceof Player player)) {
            //si no fue un jugador fue la consola u otra cosa
            //toma el mensaje de config.yml o si no existe el mensaje, muestra el mensaje por defecto
            String message = plugin.getConfig().getString("messages.not-player","Solo los jugadores pueden saludar!");
            sender.sendPlainMessage(message);
            return;
        }

        //solo usó /hello
        if (args.length == 0) {
            //toma el mensaje de config.yml o si no existe el mensaje, muestra el mensaje por defecto
            String message = plugin.getConfig().getString("messages.hello","Que gusto verte de nuevo {player}!");
            message = message.replace("{player}",player.getName());
            sender.sendPlainMessage(message);
            return;
        }

        //va a recargar la configuración, usó /hello reload
        else if (args[0].equalsIgnoreCase("reload")) {
            //recargar el config.yml
            plugin.reloadConfig();
            //toma el mensaje de config.yml o si no existe el mensaje, muestra el mensaje por defecto
            String message = plugin.getConfig().getString("messages.reload","El config se ha recargado!");
            sender.sendPlainMessage(message);
        }

        //usó /hello world
        else if (args[0].equalsIgnoreCase("world")) {
            //calcular los cooldowns
            long currentTime = System.currentTimeMillis();//tiempo actual
            long cooldownTime = plugin.getConfig().getLong("cooldown.hello-world", 5000);//cooldown 5 segundos

            if (cooldowns.containsKey(player.getUniqueId())) {//si el jugador ya ha usado el comando antes
                long lastUsed = cooldowns.get(player.getUniqueId());//ultima vez que usó el comando
                if (currentTime - lastUsed < cooldownTime){//debe esperar más para ejecutar el comando
                    //toma el mensaje de config.yml o si no existe el mensaje, muestra el mensaje por defecto
                    String message = plugin.getConfig().getString("messages.hello-world-cooldown", "Debes esperar {cooldown} segundos para usar este comando de nuevo!");
                    int secondsLeft = (int) ((cooldownTime - (currentTime - lastUsed)) / 1000);
                    message = message.replace("{cooldown}", String.valueOf(secondsLeft));
                    sender.sendPlainMessage(message);
                    return;
                }
            }
            //ya pasó el cooldown establecido
            //guarda el tiempo actual
            cooldowns.put(player.getUniqueId(), currentTime);
            //toma el mensaje de config.yml o si no existe el mensaje, muestra el mensaje por defecto
            String message = plugin.getConfig().getString("messages.hello-world", "{player} le manda saludos a todos!");
            message = message.replace("{player}", player.getName());
            //crear el componente
            Component broadcastMessage = MiniMessage.miniMessage().deserialize(message);
            //mostrarlo a todos
            Bukkit.broadcast(broadcastMessage);
        }

        //usó /hello <jugador>
        else{
            //buscar al jugador
            Player objective = Bukkit.getPlayer(args[0]);
            //ver si el jugador está conectado
            if (objective == null) {
                //toma el mensaje de config.yml o si no existe el mensaje, muestra el mensaje por defecto
                String message = plugin.getConfig().getString("messages.offline","El jugador no esta conectado!");
                message = message.replace("{player}",player.getName());
                sender.sendPlainMessage(message);
                return;
            }
            //toma el mensaje de config.yml o si no existe el mensaje, muestra el mensaje por defecto
            String message = plugin.getConfig().getString("messages.hello-player","{player} te manda saludos!");
            message = message.replace("{player}",player.getName());
            //mandarle el mensaje al jugador
            objective.sendPlainMessage(message);
        }

        //fin de la función
    }
}
