package me.destroy;
import me.destroy.listeners.Durability;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    private List<Material> armor;

    public Main() {
    }
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        this.armor = new ArrayList();
        Iterator var2 = this.getConfig().getStringList("Armor").iterator();

        while(var2.hasNext()) {
            String armorType = (String)var2.next();
            Material material = Material.matchMaterial(armorType);
            if (material == null) {
                Bukkit.getLogger().info("Error, this armor doesn't exist -'" + armorType + "'!");
            } else {
                this.armor.add(material);
            }
        }
        Bukkit.getPluginManager().registerEvents(new Durability(), this);
    }
    public void onDisable() {
        instance = null;
    }
    public static Main getInstance() {
        return instance;
    }
    public List<Material> getArmourList() {
        return this.armor;
    }
}