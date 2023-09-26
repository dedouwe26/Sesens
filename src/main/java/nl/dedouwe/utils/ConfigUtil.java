package nl.dedouwe.utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import nl.dedouwe.Plugin;

public class ConfigUtil {
    private FileConfiguration configFile;
    private Plugin plugin;
    public ConfigUtil(FileConfiguration conf, Plugin p) {
        configFile = conf;
        plugin = p;
    }
    public Location getPlayerStorage(UUID p) {
        if (configFile.get("players."+p.toString()+".storageWorld") == null)
            return null;
        return new Location(Bukkit.getWorld((String)configFile.get("players."+p.toString()+".storageWorld")), (double)configFile.get("players."+p.toString()+".storageX"), (double)configFile.get("players."+p.toString()+".storageY"), (double)configFile.get("players."+p.toString()+".storageZ"));
    }
    public double GetPlayerLevel(UUID p) {
        if (configFile.contains("players."+p.toString()+".lvl"))
            return (double)configFile.get("players."+p.toString()+".lvl");
        SetLevel(p, 0);
        return 0;
    }
    public void SetLevel(UUID p, double lvl) {
        configFile.set("players."+p.toString()+".lvl", lvl);
        plugin.saveConfig();
    }
    public void SetPlayerStorage(UUID p, Location storageLocation) {
        configFile.set("players."+p.toString()+".storageWorld", storageLocation.getWorld().getName());
        configFile.set("players."+p.toString()+".storageX", storageLocation.getX());
        configFile.set("players."+p.toString()+".storageY", storageLocation.getY());
        configFile.set("players."+p.toString()+".storageZ", storageLocation.getZ());
        plugin.saveConfig();
    }
    public void SaveTomb(Location l) {
        configFile.set("cycle.tomb.world", l.getWorld().getName());
        configFile.set("cycle.tomb.x", l.getX());
        configFile.set("cycle.tomb.y", l.getY());
        configFile.set("cycle.tomb.z", l.getZ());
        plugin.saveConfig();
    }
    public Location GetTomb() {
        if (configFile.get("cycle.tomb.world") == null) {
            return null;
        }
        return new Location(Bukkit.getWorld((String)configFile.get("cycle.tomb.world")), (double)configFile.get("cycle.tomb.x"), (double)configFile.get("cycle.tomb.y"), (double)configFile.get("cycle.tomb.z"));
    }
    public void SaveTreasure(Location l, int i) {
        configFile.set("cycle.treasures."+i+".world", l.getWorld().getName());
        configFile.set("cycle.treasures."+i+".x", l.getX());
        configFile.set("cycle.treasures."+i+".y", l.getY());
        configFile.set("cycle.treasures."+i+".z", l.getZ());
        plugin.saveConfig();
    }
}
