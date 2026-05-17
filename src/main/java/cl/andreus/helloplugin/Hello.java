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

@NullMarked
public class Hello implements BasicCommand{

    //las cosas que muestra
    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        //sugerencias para el comando en la posición 1
        if (args.length == 1) {
            //Collection de jugadores
            Collection<? extends Player> players = Bukkit.getOnlinePlayers();
            //crear una lista de strings con "world"
            List<String> optionsList = new ArrayList<String>(List.of("world"));
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
            sender.sendPlainMessage("Solo los jugadores pueden saludar!");
            return;
        }
        //solo usó /hello
        if (args.length == 0) {
            sender.sendPlainMessage("Hola " + player.getName() + "! Que gusto verte por aquí!");
            return;
        }
        //si usa /hello world
        else if (args[0].equalsIgnoreCase("world")) {
            //crear el componente
            Component broadcastMessage = MiniMessage.miniMessage().deserialize(
                    player.getName() + " le manda saludos a todos en el servidor!"
            );
            //mostrarlo a todos
            Bukkit.broadcast(broadcastMessage);
        }
        //usa /hello <jugador>
        else{
            //buscar al jugador
            Player objective = Bukkit.getPlayer(args[0]);
            //ver si el jugador está conectado
            if (objective == null) {
                sender.sendPlainMessage("el jugador no está conectado!");
                return;
            }
            //si se manda un mensaje a si mismo
            if(objective == player){
                sender.sendPlainMessage("hola de parte de... ti mismo?");
                return;
            }
            //mandarle el mensaje al jugador
            objective.sendPlainMessage(player.getName() + " te manda saludos!");
        }

        //fin de la función
    }
}
