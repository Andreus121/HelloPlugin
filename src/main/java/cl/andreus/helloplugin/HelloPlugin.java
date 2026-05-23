package cl.andreus.helloplugin;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStream;
import java.io.InputStreamReader;

public final class HelloPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        //lógica cuando el plugin se inicia
        //guardar los mensajes de "config.yml"
        saveDefaultConfig();
        //actualizar el config.yml
        updateConfig();
        //registrar el comando
        registerCommand("hello", new Hello(this));
        //registrar el listener
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
    }

    @Override
    public void onDisable() {
        //lógica cuando el plugin se finaliza
    }

    //actualizar el config.yml en caso de actualización
    private void updateConfig() {
        //obtener el config.yml del jar
        InputStream defaultStream = getResource("config.yml");
        if (defaultStream == null) return;

        //convierte en config.yml a un archivo yml (antes eran bits)
        YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(
                new InputStreamReader(defaultStream)
        );

        //comparar con el config.yml del servidor
        boolean changed = false;
        //verificar si las palabras claves están
        for (String key : defaultConfig.getKeys(true)) {
            //si no existe la palabra clave añádela y marca el cambio
            if (!getConfig().contains(key)) {
                getConfig().set(key, defaultConfig.get(key));
                changed = true;
            }
        }
        //si hubo algún cambio guarda el nuevo config.yml
        if (changed) saveConfig();
    }
}
