package cl.andreus.helloplugin;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import net.kyori.adventure.text.Component;

public class JoinListener implements Listener{
    private final HelloPlugin plugin;

    public JoinListener(HelloPlugin plugin){
        this.plugin = plugin;
    }

    //cuando se detecte un jugador conectándose al sv
    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        //guardar al jugador que se conectó
        Player player = event.getPlayer();
        //tomar el mensaje de "config.yml"
        String message = plugin.getConfig().getString("messages.join","Bienvenido {player}!");
        message = message.replace("{player}",player.getName());
        //crear el mensaje
        final Component mainTitle = Component.text(message, NamedTextColor.BLUE);
        //crear el "título" que muestra el mensaje sin subtítulo
        final Title title = Title.title(mainTitle,Component.empty());

        //enviar el título al jugador
        player.showTitle(title);
    }
}
