package cl.andreus.helloplugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class HelloPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        //lógica cuando el plugin se inicia
        //registrar el comando
        registerCommand("hello", new Hello());
    }

    @Override
    public void onDisable() {
        //lógica cuando el plugin se finaliza
    }
}
