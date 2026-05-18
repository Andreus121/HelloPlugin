package cl.andreus.helloplugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class HelloPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        //lógica cuando el plugin se inicia
        //guardar los mensajes de "config.yml"
        saveDefaultConfig();
        //registrar el comando
        registerCommand("hello", new Hello(this));
        //registrar el listener
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
    }

    @Override
    public void onDisable() {
        //lógica cuando el plugin se finaliza
    }
}
