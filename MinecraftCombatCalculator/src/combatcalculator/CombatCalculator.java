package combatcalculator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CombatCalculator extends JavaPlugin implements CommandExecutor, Listener {
    
    @Override
    public void onEnable() {
        getCommand("simulatedamage").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        
        if (args[0].equalsIgnoreCase("sword")) {
            ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
            sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, Integer.parseInt(args[1]));
            player.getInventory().addItem(sword);
            return false;
        }
        
        if (args[0].equalsIgnoreCase("milk")) {
            Player target = args.length == 1 ? player : Bukkit.getPlayer(args[1]);
            for (PotionEffect effect : target.getActivePotionEffects()) {
                target.removePotionEffect(effect.getType());
            }
            return false;
        }
        
        if (args[0].equalsIgnoreCase("strength")) {
            Player target = args.length == 2 ? player : Bukkit.getPlayer(args[2]);
            target.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 3 * 60, Integer.parseInt(args[1]) - 1));
            return false;
        }
        
        if (args[0].equalsIgnoreCase("resistance")) {
            Player target = args.length == 2 ? player : Bukkit.getPlayer(args[2]);
            target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 3 * 60, Integer.parseInt(args[1]) - 1));
            return false;
        }

        if (args[0].equalsIgnoreCase("absorption")) {
            Player target = args.length == 2 ? player : Bukkit.getPlayer(args[2]);
            target.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 3 * 60, Integer.parseInt(args[1]) - 1));
            return false;
        }
        
        if (args[0].equalsIgnoreCase("zombie")) {
            player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
            return false;
        }
        
        if (args[0].equalsIgnoreCase("heal")) {
            Player target = args.length == 1 ? player : Bukkit.getPlayer(args[1]);
            target.setHealth(20);
            return false;
        }
        
        return false;
    }
    
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        DamageModifier[] modifiers = DamageModifier.values();
        Entity entity = e.getDamager();
        
        for (int i = 0; i < 3; i++) {
            entity.sendMessage("§a");
        }
        
        entity.sendMessage("§aOriginal damage: §f" + e.getDamage());
        entity.sendMessage("§aDamage modifiers: §f");
        for (DamageModifier modifier : modifiers) {
            entity.sendMessage("§f- " + modifier.toString() + ": " + e.getDamage(modifier));
        }
        
        entity.sendMessage("§aFinal damage: §f" + e.getFinalDamage());
        entity.sendMessage("§aOriginal modifiers: §f");
        for (DamageModifier modifier : modifiers) {
            entity.sendMessage("§f- " + modifier.toString() + ": " + e.getOriginalDamage(modifier));
        }
        
        for (int i = 0; i < 3; i++) {
            entity.sendMessage("§a");
        }
    }

}
