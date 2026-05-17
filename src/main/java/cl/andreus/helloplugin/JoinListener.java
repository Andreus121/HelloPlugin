package cl.andreus.helloplugin;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import net.kyori.adventure.text.Component;

public class JoinListener implements Listener{

    //cuando se detecte un jugador conectándose al sv
    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        //guardar al jugador que se conectó
        Player player = event.getPlayer();
        //crear el mensaje
        final Component mainTitle = Component.text("Que gusto verte de nuevo "+player.getName()+"!", NamedTextColor.BLUE);
        //crear el "título" que muestra el mensaje sin subtítulo
        final Title title = Title.title(mainTitle,Component.empty());

        //enviar el título al jugador
        player.showTitle(title);
    }
}
