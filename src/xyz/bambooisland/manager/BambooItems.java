package xyz.bambooisland.manager;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.*;

import java.util.*;

public class BambooItems {
    @Deprecated
    public boolean equalsIgnoreAmount(ItemStack a,ItemStack b) {
        ItemStack x = a.clone();
        x.setAmount(1);
        ItemStack y = a.clone();
        y.setAmount(1);
        return x.equals(y);
    }

    public ItemStack getWorldEditorTool() {
        ItemStack item0 = new ItemStack(Material.WOODEN_AXE);
        item0.addEnchantment(Enchantment.DURABILITY, 3);
        ItemMeta meta0 = item0.getItemMeta();
        meta0.setDisplayName("World Editor");
        item0.setItemMeta(meta0);
        return item0;
    }

    public ItemStack getEntityKillerTool() {
        ItemStack item1 = new ItemStack(Material.GOLDEN_SWORD);
        item1.addEnchantment(Enchantment.DAMAGE_ALL, 5);
        item1.addEnchantment(Enchantment.DURABILITY,3);
        item1.addEnchantment(Enchantment.FIRE_ASPECT,2);
        item1.addEnchantment(Enchantment.KNOCKBACK,2);
        item1.addEnchantment(Enchantment.MENDING,1);
        item1.addEnchantment(Enchantment.SWEEPING_EDGE,3);
        ItemMeta meta1 = item1.getItemMeta();
        meta1.setUnbreakable(true);
        meta1.setDisplayName("Entity Killer");
        item1.setItemMeta(meta1);
        return item1;
    }

    public ItemStack getGamemodeSwitcherTool() {
        ItemStack item2 = new ItemStack(Material.DIAMOND_HELMET);
        item2.addEnchantment(Enchantment.DURABILITY,3);
        ItemMeta meta2 = item2.getItemMeta();
        meta2.setUnbreakable(true);
        meta2.setDisplayName("Switch Gamemode");
        item2.setItemMeta(meta2);
        return item2;
    }

    public ItemStack getKickTool() {
        ItemStack item3 = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta meta3 = item3.getItemMeta();
        meta3.setUnbreakable(true);
        meta3.setDisplayName("Kick all players");
        item3.setItemMeta(meta3);
        return item3;
    }

    public ItemStack getFlyModeTool() {
        ItemStack item4 = new ItemStack(Material.ELYTRA);
        item4.addEnchantment(Enchantment.DURABILITY,3);
        item4.addEnchantment(Enchantment.MENDING,1);
        ItemMeta meta4 = item4.getItemMeta();
        meta4.setDisplayName("Switch on/off flight mode");
        item4.setItemMeta(meta4);
        return item4;
    }

    public ItemStack getRandomPlaceTeleportTool () {
        ItemStack item5 = new ItemStack(Material.ENDER_EYE);
        ItemMeta meta5 = item5.getItemMeta();
        meta5.setUnbreakable(true);
        meta5.setDisplayName("Random Teleporter (Place)");
        item5.setItemMeta(meta5);
        return item5;
    }

    public ItemStack getRandomPlayerTeleportTool() {
        ItemStack item6 = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta6 = item6.getItemMeta();
        meta6.setUnbreakable(true);
        meta6.setDisplayName("Random Teleporter (Player)");
        item6.setItemMeta(meta6);
        return item6;
    }

    public ItemStack getEffectManagerTool() {
        ItemStack item7 = new ItemStack(Material.ENDER_CHEST);
        ItemMeta meta7 = item7.getItemMeta();
        meta7.setUnbreakable(true);
        meta7.setDisplayName("Effect Manager");
        List<String> _lore7 = new ArrayList<String>();
        _lore7.add("Manage multiple effects in an easy way.");
        meta7.setLore(_lore7);
        item7.setItemMeta(meta7);
        return item7;
    }

    public ItemStack getPotion(String displayName, Color color, PotionEffectType type) {
        ItemStack _potion = new ItemStack(Material.POTION);
        PotionMeta potmeta = (PotionMeta) _potion.getItemMeta();
        potmeta.setColor(color);
        potmeta.addCustomEffect(new PotionEffect(type,3600,0),false);
        potmeta.setUnbreakable(true);
        potmeta.setDisplayName(displayName);
        potmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        _potion.setItemMeta(potmeta);
        return _potion;
    }

    public ItemStack getTeleportReel(boolean isPermanent) {
        ItemStack reel = new ItemStack(Material.PAPER);
        ItemMeta meta = reel.getItemMeta();
        meta.setDisplayName("BambooIsland Teleport Reel");
        meta.setUnbreakable(true);
        List<String> _lore = new ArrayList<String>();
        _lore.add("Quick teleport to the server spawn point.");
        if(isPermanent) _lore.add("Permanent"); else _lore.add("Disposable reel");
        meta.setLore(_lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        reel.setItemMeta(meta);
        return reel;
    }
}
