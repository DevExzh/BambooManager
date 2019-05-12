package xyz.bambooisland.manager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

// 监听器
public class BambooEventHandler implements Listener {
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getPlayer().hasPermission("BambooManager.Administrator")) {
			BambooItems items = new BambooItems();
			if(event.getItem().equals(items.getGamemodeSwitcherTool())) {
				if(event.getPlayer().getGameMode() == GameMode.SURVIVAL || event.getPlayer().getGameMode() == GameMode.ADVENTURE)
					event.getPlayer().setGameMode(GameMode.CREATIVE);
				else if(event.getPlayer().getGameMode() == GameMode.CREATIVE)
					event.getPlayer().setGameMode(GameMode.SURVIVAL);
				event.setCancelled(true);
			} else if(event.getItem().equals(items.getFlyModeTool())) {
				Player controller = event.getPlayer();
                controller.setFallDistance(0f);
				controller.setAllowFlight(BambooMethods.converseBoolean(controller.getAllowFlight()));
				event.setCancelled(true);
			} else if(event.getItem().equals(items.getKickTool())) {
			    for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			        if(!p.hasPermission("BambooManager.Administrator"))
			            p.kickPlayer("[Bamboo Island] Server is maintaining.");
                }
				event.setCancelled(true);
            } else if(event.getItem().equals(items.getRandomPlaceTeleportTool())) {
			    Location lo = BambooMethods.getRandomLocation(event.getPlayer());
			    while (!BambooMethods.isTallEnough(lo)) {
                    lo = BambooMethods.getRandomLocation(event.getPlayer());
                }
			    event.getPlayer().teleport(lo);
				event.setCancelled(true);
            } else if(event.getItem().equals(items.getRandomPlayerTeleportTool())) {
			    List l = Arrays.stream(Bukkit.getServer().getOnlinePlayers().toArray()).collect(Collectors.toList());
			    Collections.shuffle(l);
                event.getPlayer().teleport(((List<Player>)l).get(0));
                event.setCancelled(true);
			} else if(event.getItem().equals(items.getEffectManagerTool())) {
				Inventory manager = Bukkit.getServer().createInventory(null,InventoryType.CHEST,"Bamboo Island Effect Manager");
				manager.addItem(items.getPotion("Speed", Color.RED, PotionEffectType.SPEED));
				manager.addItem(items.getPotion("Haste", Color.AQUA, PotionEffectType.FAST_DIGGING));
				manager.addItem(items.getPotion("Strength", Color.BLACK, PotionEffectType.INCREASE_DAMAGE));
				manager.addItem(items.getPotion("Instant Health", Color.BLUE, PotionEffectType.HEAL));
				manager.addItem(items.getPotion("Jump Boost", Color.FUCHSIA, PotionEffectType.JUMP));
				manager.addItem(items.getPotion("Regeneration", Color.GRAY, PotionEffectType.REGENERATION));
				manager.addItem(items.getPotion("Resistance", Color.GREEN, PotionEffectType.DAMAGE_RESISTANCE));
				manager.addItem(items.getPotion("Fire Resistance", Color.LIME, PotionEffectType.FIRE_RESISTANCE));
				manager.addItem(items.getPotion("Water Breathing", Color.MAROON, PotionEffectType.WATER_BREATHING));
				manager.addItem(items.getPotion("Invisibility", Color.NAVY, PotionEffectType.INVISIBILITY));
				manager.addItem(items.getPotion("Night Vision", Color.OLIVE, PotionEffectType.NIGHT_VISION));
				manager.addItem(items.getPotion("Health Boost", Color.ORANGE, PotionEffectType.HEALTH_BOOST));
				manager.addItem(items.getPotion("Saturation", Color.PURPLE, PotionEffectType.SATURATION));
				manager.addItem(items.getPotion("Levitation", Color.SILVER, PotionEffectType.LEVITATION));
				manager.addItem(items.getPotion("Luck", Color.TEAL, PotionEffectType.LUCK));
				manager.addItem(items.getPotion("Slow Falling", Color.WHITE, PotionEffectType.SLOW_FALLING));
				manager.addItem(items.getPotion("Dolphins Grace", Color.YELLOW, PotionEffectType.DOLPHINS_GRACE));
				event.getPlayer().openInventory(manager);
				event.setCancelled(true);
			} else if(event.getItem().isSimilar(items.getTeleportReel(true))) {
				event.getPlayer().teleport(new Location(Bukkit.getServer().getWorld("world"),-500d,89d,500d));
				event.setCancelled(true);
			} else if(event.getItem().isSimilar(items.getTeleportReel(false))) {
				event.getPlayer().teleport(new Location(Bukkit.getServer().getWorld("world"),-500d,89d,500d));
				event.getItem().setAmount(event.getItem().getAmount() - 1);
				//event.getPlayer().getInventory().remove(event.getItem());
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
		if(event.getPlayer().hasPermission("BambooManager.Administrator")) {
			if(event.getPlayer().getInventory().getItem(event.getPlayer().getInventory().getHeldItemSlot()).equals(new BambooItems().getEntityKillerTool()))
				event.getRightClicked().remove();
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if(event.getWhoClicked().hasPermission("BambooManager.Administrator")) {
			if(event.getCurrentItem().getItemMeta().isUnbreakable()) {
				if(event.getCurrentItem().getType().equals(Material.POTION)) {
					ItemStack operation_add = new ItemStack(Material.GREEN_CONCRETE);
					ItemMeta xadd = operation_add.getItemMeta();
					xadd.setUnbreakable(true);
					xadd.setDisplayName("Increase Effect Level");
					operation_add.setItemMeta(xadd);
					ItemStack operation_minus = new ItemStack(Material.RED_CONCRETE);
					ItemMeta xminus = operation_minus.getItemMeta();
					xminus.setUnbreakable(true);
					xminus.setDisplayName("Decrease Effect Level");
					operation_minus.setItemMeta(xminus);

					ItemStack operation_plus = new ItemStack(Material.GREEN_CONCRETE);
					ItemMeta yadd = operation_plus.getItemMeta();
					yadd.setUnbreakable(true);
					yadd.setDisplayName("Increase Effect Time");
					operation_plus.setItemMeta(yadd);
					ItemStack operation_subtract = new ItemStack(Material.RED_CONCRETE);
					ItemMeta yminus = operation_subtract.getItemMeta();
					yminus.setUnbreakable(true);
					yminus.setDisplayName("Decrease Effect Time");
					operation_subtract.setItemMeta(yminus);

					Inventory lvlselect = Bukkit.createInventory(null,InventoryType.CHEST,"Effect options");
					BambooMethods.addNullSectionToInventory(lvlselect,10,0);
					ItemStack indicator_x = new ItemStack(Material.EXPERIENCE_BOTTLE);
					ItemMeta x = indicator_x.getItemMeta();
					x.setUnbreakable(true);
					x.setDisplayName("Effect Level: 0");
					indicator_x.setItemMeta(x);

					ItemStack indicator_y = new ItemStack(Material.CLOCK);
					ItemMeta y = indicator_y.getItemMeta();
					y.setUnbreakable(true);
					y.setDisplayName("Effect Time: 0s");
					indicator_y.setItemMeta(y);

					lvlselect.addItem(operation_minus);
					lvlselect.addItem(indicator_x);
					lvlselect.addItem(operation_add);
					BambooMethods.addNullSectionToInventory(lvlselect,1,13);
					lvlselect.addItem(operation_subtract);
					lvlselect.addItem(indicator_y);
					lvlselect.addItem(operation_plus);
					BambooMethods.addNullSectionToInventory(lvlselect,10,17);
					event.getWhoClicked().openInventory(lvlselect);
					event.setCancelled(true);
				} else if(event.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE) && event.getCurrentItem().getItemMeta().isUnbreakable()) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerBedEnter(PlayerBedEnterEvent event) {
		if(Bukkit.getServer().getOnlinePlayers().size() == 1) return;
		int _ply_in_bed = 0;
		for(Player p:Bukkit.getServer().getOnlinePlayers()) {
			if(p.isSleeping()) _ply_in_bed++;
		}
		Bukkit.getServer().broadcastMessage("§2[Bamboo Island 汇报器] §f"+event.getPlayer().getDisplayName()+"进入了梦乡。 "+ChatColor.AQUA+"("+_ply_in_bed+"/"+Bukkit.getServer().getOnlinePlayers().size()+")");
	}

	@EventHandler
	public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
		if(Bukkit.getServer().getOnlinePlayers().size() == 1) return;
		int _ply_in_bed = 0;
		for(Player p:Bukkit.getServer().getOnlinePlayers()) {
			if(p.isSleeping()) _ply_in_bed++;
		}
		Bukkit.getServer().broadcastMessage("§2[Bamboo Island 汇报器] §f"+event.getPlayer().getDisplayName()+"从梦中醒了过来。 "+ChatColor.AQUA+"("+_ply_in_bed+"/"+Bukkit.getServer().getOnlinePlayers().size()+")");
	}

	@EventHandler
	public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent event) {
		event.getPlayer().getInventory().addItem(new BambooItems().getTeleportReel(false));
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if((int)(1+Math.random()*(2000)) == 1) {
			event.setKeepInventory(true);
			event.setKeepLevel(true);
			event.setDeathMessage(ChatColor.GOLD+"[竹洲 VIP] "+ChatColor.WHITE+event.getEntity().getDisplayName()+" 欧皇附体，成功无损伤重生！");
		}
	}

	@EventHandler
public void onEntitiyExplode(EntityExplodeEvent event)
{
	StringBuilder playerListBuilder = new StringBuilder();
	for(Player p : Bukkit.getServer().getOnlinePlayers())
	{
		if(p.getWorld() == event.getLocation().getWorld())
		{
			if(p.getLocation().distance(event.getLocation()) <= 20.0)
				playerListBuilder.append(p.getName()).append(", ");
		}
	}
	String playerList = playerListBuilder.toString();
	playerList = StringUtils.substringBeforeLast(playerList,",");
	Bukkit.broadcastMessage("§2[Bamboo Island 汇报器] §f 检测到有 " + event.getEntityType().toString() + " 在 X=" + 
    event.getLocation().getBlockX() + " Y=" + event.getLocation().getBlockY() + " Z=" + event.getLocation().getBlockZ() + " 的位置产生了爆炸！\n附近的玩家："+
    ChatColor.GOLD + playerList);
}

@EventHandler(priority=EventPriority.MONITOR)
public void onJoin(PlayerJoinEvent event)
{
	Player p = event.getPlayer();
	String msg = "";
	if(p.hasPermission("BambooManager.Administrator"))
	{
		msg += (ChatColor.GOLD + "[管理员] " + p.getDisplayName());
	}
	else
	{
		msg += p.getDisplayName();
	}
	msg += (ChatColor.WHITE + " 进入了服务器");
	event.setJoinMessage(msg);
}

@EventHandler(priority=EventPriority.MONITOR)
public void onQuit(PlayerQuitEvent event)
{
	Player p = event.getPlayer();
	String msg = "";
	if(p.hasPermission("BambooManager.Administrator"))
	{
		msg += (ChatColor.GOLD + "[管理员] " + p.getDisplayName());
	}
	else
	{
		msg += p.getDisplayName();
	}
	msg += (ChatColor.WHITE + " 离开了服务器");
	event.setQuitMessage(msg);
}

@EventHandler(priority=EventPriority.MONITOR)
public void onAsyncPlayerChat(AsyncPlayerChatEvent event)
{
	if(event.getPlayer().hasPermission("BambooManager.Administrator") && event.getPlayer().getDisplayName().equals("Exzh_PMGI")) {
		String msg = event.getMessage();
		event.setCancelled(true);
		Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "[萌新] " + ChatColor.RED + "E" + ChatColor.GOLD + "x" + ChatColor.YELLOW + "z" + ChatColor.GREEN + "h" + ChatColor.DARK_GREEN + "_" + ChatColor.BLUE + "P" + ChatColor.DARK_BLUE + "M" + ChatColor.LIGHT_PURPLE + "G" + ChatColor.DARK_PURPLE + "I" + ChatColor.WHITE + " " + msg);
	}

	File file = new File(BambooMethods.getCurrentDirPath() + "/BambooManager/chatlogs/log_" + new SimpleDateFormat("yyyy_MM_dd").format(new Date()));
	if(!file.exists())
	{
		try
		{
			file.createNewFile();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	FileWriter writer = null;
	BufferedWriter bW = null;
	try
	{
		writer = new FileWriter(file,true);
		bW = new BufferedWriter(writer);
		bW.newLine();
		bW.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append(" [").append(event.getPlayer().getName()).append("] ").append(event.getMessage());
	}
	catch (IOException ex)
	{
		ex.printStackTrace();
	}
	finally
	{
		try
		{
			Objects.requireNonNull(bW).close();
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
}
