package me.destroy.listeners;
import me.destroy.Main;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class Durability implements Listener {
    private Map<Integer, Double> betterUnbr = new HashMap();

    public Durability() {
        for(int level = 1; level < 256; ++level) {
            this.addLevel(level);
        }
    }

    @EventHandler(
        priority = EventPriority.LOWEST,
        ignoreCancelled = true
    )
    public void onItemDamage(PlayerItemDamageEvent e) {
        if (e.getDamage() > 0) {
            List<Material> armourTypes = Main.getInstance().getArmourList();
            if (armourTypes.contains(e.getItem().getType())) {
                if (e.getItem().containsEnchantment(Enchantment.DURABILITY)) {
                    int level = e.getItem().getEnchantmentLevel(Enchantment.DURABILITY);
                    if (level >= 1) {
                        if (!this.betterUnbr.containsKey(level)) {
                            this.addLevel(level);
                        }

                        double chance = (Double)this.betterUnbr.get(level);
                        int damage = e.getDamage();

                        for(int i = 0; i < e.getDamage(); ++i) {
                            if (Math.random() >= chance) {
                                --damage;
                            }
                        }

                        e.setDamage(damage);
                    }
                }
            }
        }
    }

    private void addLevel(int level) {
        this.betterUnbr.put(level, 5.0D / (3.0D * (double)level + 5.0D));
    }
}