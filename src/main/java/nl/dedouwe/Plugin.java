package nl.dedouwe;

import java.util.logging.Logger;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import nl.dedouwe.commands.SesensCommand;
import nl.dedouwe.events.SesensListener;
import nl.dedouwe.utils.ConfigUtil;

/**
 * sesens java plugin
 */
public class Plugin extends JavaPlugin
{
    public static Plugin instance;

    private static final Logger LOGGER=Logger.getLogger("Sesens");

    public NamespacedKey SesenType = new NamespacedKey(this, "type");
    public NamespacedKey SesenInstance = new NamespacedKey(this, "instance");
    public NamespacedKey SesenSouls = new NamespacedKey(this, "souls");

    public ConfigUtil config = new ConfigUtil(getConfig(), this);

    public void onEnable()
    {
        instance = this;
        saveDefaultConfig();

        new Sesens(config, this);
        getCommand("sesens").setExecutor(new SesensCommand());

        new SesensListener(this);

        LOGGER.info("sesens enabled");
    }

    public void onDisable()
    {
        LOGGER.info("sesens disabled");
    }
}
