package xyz.bambooisland.manager;
import com.google.common.collect.Multimap;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.*;
import java.util.stream.Collectors;

public class BambooManager extends JavaPlugin {

@Override
public void onEnable()
{
	Bukkit.getPluginManager().registerEvents(new BambooEventHandler(), this);
	getCommand("bamboo").setTabCompleter(this);
/*
	this.tablist = new BambooTablist(this);
	this.packet = new PacketHandler(this);
	this.tablist.getTablist().clear();
	BambooTablist.getInstance().initialize();
	for (Player player : Bukkit.getOnlinePlayers())
	{
		BambooTablist.getInstance().refreshTablist(player);
	}
	Bukkit.getScheduler().scheduleSyncRepeatingTask(this, startRefresh(), 1L,  20L);
*/
	getLogger().info("Bamboo Manager has been loaded.");

}

@Override
public void onDisable()
{
	getLogger().info("Bamboo Manager is quit.");
}

@Override
public List<String> onTabComplete(CommandSender sender,Command command,String alias,String[] args)
{
	final String[] subCmds = {"remove","serverinfo","platform","broadcast","playerinfo","plugins","info","playerlist","task","adminkits"};
	if(args.length == 2)
	{
		if(args[0].equalsIgnoreCase("remove") && alias.equalsIgnoreCase("bamboo")) {
			final String[] removeCmdSubs = {"monsters", "items"};
			return Arrays.asList(removeCmdSubs);
		} else if(args[0].equalsIgnoreCase("movie") && alias.equalsIgnoreCase("bamboo")) {
			final String[] movieCmdSubs = {"speed", "view"};
			return Arrays.asList(movieCmdSubs);
		} else return getOnlinePlayersName();
	}
	if(args.length == 1) {
		if(alias.equalsIgnoreCase("bamboo"))
		{
			return Arrays.stream(subCmds).sorted().filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
		}
	}
	return getOnlinePlayersName();
}

private List<String> getOnlinePlayersName()
{
	List<String> nameList = new ArrayList<>();
	for(Player p : Bukkit.getOnlinePlayers())
	{
		nameList.add(p.getName());
	}
	return nameList;
}

@Override
public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args)
{
	if(label.equalsIgnoreCase("point"))
	{
		if(sender instanceof Player)
		{
			Player _player = (Player)sender;
			if(args.length == 0)
			{
				Location _loc = _player.getLocation();
				Bukkit.broadcastMessage("��2[Bamboo Island �㱨��] ��f"+_player.getName()+" ��λ���� X="+Location.locToBlock(_loc.getX())+" Y="+Location.locToBlock(_loc.getY())+" Z="+Location.locToBlock(_loc.getZ()));
				return true;
			}
			else if(args.length == 1)
			{
				Player __player = (Player)sender;
				Player _target = Bukkit.getServer().getPlayer(UUID.fromString(Objects.requireNonNull(BambooUuid.getUUID(args[0]))));
				if(!Bukkit.getServer().getOnlinePlayers().contains(_target))
				{
					__player.sendMessage(ChatColor.RED + "��� "+args[0]+" ��ǰ�����ߣ�");
				} else {
					Location _loc = _player.getLocation();
					if(__player != _target)
					{
						if(__player.getWorld() == _target.getWorld())
							Bukkit.broadcastMessage("��2[Bamboo Island �㱨��] ��f"+_player.getName()+" ��λ���� X="+Location.locToBlock(_loc.getX())+" Y="+Location.locToBlock(_loc.getY())+" Z="+Location.locToBlock(_loc.getZ())+ "," + ChatColor.AQUA + args[0] + " ���� "+ __player.getName() + " �� " + (int)__player.getLocation().distance(_target.getLocation()) + " ��Զ");
						else sender.sendMessage("��2[Bamboo Island "+ChatColor.GREEN+"���ϵͳ"+ChatColor.DARK_GREEN+"] "+ChatColor.WHITE+"Ŀ��������㲻��һ�������");
					}
				}
				return true;
			}
		}
	}
	if(label.equalsIgnoreCase("bamboo"))
	{
		if(args[0].equalsIgnoreCase("remove"))
		{
			if(sender.hasPermission("BambooManager.Administrator")) {
				for (World w : Bukkit.getWorlds()) {
					List<Entity> entityList = w.getEntities();
					if (args[1].equalsIgnoreCase("monsters")) {
						long count = 0;
						for (Entity entity : entityList) {
							if ((entity instanceof Zombie) || (entity instanceof Skeleton)
									|| (entity instanceof Spider) || (entity instanceof Ghast)
									|| (entity instanceof Guardian) || (entity instanceof Witch)
									|| (entity instanceof Vex) || (entity instanceof Silverfish)
									|| (entity instanceof MagmaCube) || (entity instanceof Blaze)
									|| (entity instanceof Creeper) || (entity instanceof Enderman)
							) {
								entity.remove();
								count++;
							}
						}
						sender.sendMessage(ChatColor.GREEN + "[Bamboo Manager] " + ChatColor.WHITE + "���� " + w.getName() + " ����� " + count + " ������");
					} else if (args[1].equalsIgnoreCase("items")) {
						long count = 0;
						for (Entity entity : w.getEntities()) {
							if (entity instanceof Item) {
								entity.remove();
								count++;
							}
						}
						sender.sendMessage(ChatColor.GREEN + "[Bamboo Manager] " + ChatColor.WHITE + "���� " + w.getName() + " ����� " + count + " ��������");
					}
				}
				return true;
			} else
			{
				sender.sendMessage(ChatColor.GREEN+"[Bamboo Island] "+ChatColor.RED+"�ܱ�Ǹ������Ȩִ�д����");
				return true;
			}
		} else if(args[0].equalsIgnoreCase("serverinfo"))
		{
            Runtime r = Runtime.getRuntime();
            sender.sendMessage("��7----------------------------------");
            sender.sendMessage("��7��ʹ�õ��ڴ�: ��e" + (r.totalMemory() - r.freeMemory()) / 1048576L + "MB");
            sender.sendMessage("��7���õ��ڴ�: ��e" + (r.maxMemory() / 1048576L - (r.totalMemory() - r.freeMemory()) / 1048576L) + "MB");
            sender.sendMessage("��7����ʹ���ڴ�: ��e" + r.maxMemory() / 1048576L + "MB");
            sender.sendMessage("��7δʹ�õ�����: ��e" + loadedChunks());
            sender.sendMessage("��7----------------------------------");
            return true;
		} else if(args[0].equalsIgnoreCase("platform") || args[0].equalsIgnoreCase("pf"))
		{
			if(sender.hasPermission("BambooManager.Administrator"))
			{
				if(args.length == 1)
				{
					if(sender instanceof Player)
					{
						Location location = ((Player)sender).getLocation();
						for(int i = location.getBlockX() - 4; i <= location.getBlockX() + 4; i++)
						{
							for(int j = location.getBlockZ() - 4; j < location.getBlockZ() + 4; j++)
							{
								((Player)sender).getWorld().getBlockAt(i, ((Player)sender).getLocation().getBlockY(), j).setType(Material.OBSIDIAN);
							}
						}
					} else sender.sendMessage(ChatColor.RED + "[Bamboo Manager] " + ChatColor.WHITE + "������ҵ����ִ��ָ�");
				}
				return true;
			} else
			{
				sender.sendMessage(ChatColor.GREEN+"[Bamboo Island] "+ChatColor.RED+"�ܱ�Ǹ������Ȩִ�д����");
				return true;
			}
		} else if(args[0].equalsIgnoreCase("broadcast"))
		{
			if(sender.hasPermission("BambooManager.Administrator"))
			{
				StringBuilder msg = new StringBuilder();
				for(String s : args)
				{
					if(!s.equalsIgnoreCase("broadcast"))
					{
						msg.append(s).append(" ");
					}
				}
				Bukkit.broadcastMessage(msg.toString());
				return true;
			} else
			{
				sender.sendMessage(ChatColor.GREEN+"[Bamboo Island] "+ChatColor.RED+"�ܱ�Ǹ������Ȩִ�д����");
				return true;
			}
		} else if(args[0].equalsIgnoreCase("playerinfo"))
		{
			if(sender.hasPermission("BambooManager.Administrator"))
			{
				Player obj = Bukkit.getServer().getPlayer(UUID.fromString(Objects.requireNonNull(BambooUuid.getUUID(args[1]))));
				if(!obj.isOnline())
				{
					sender.sendMessage(ChatColor.RED +"��ҵ�ǰ������");
					return true;
				}
				sender.sendMessage(ChatColor.GRAY + "----------------------------------");
				sender.sendMessage(ChatColor.WHITE + "DisplayName: " + ChatColor.AQUA + obj.getDisplayName());
				sender.sendMessage(ChatColor.WHITE + "EntityID: " + ChatColor.AQUA + obj.getEntityId());
				sender.sendMessage(ChatColor.WHITE + "Exp percent to next level: " + ChatColor.AQUA + obj.getExp()*100 + "%");
				sender.sendMessage(ChatColor.WHITE + "Exp needed to next level: " + ChatColor.AQUA + obj.getExpToLevel());
				if(obj.getFirstPlayed() != 0)
				{
					sender.sendMessage(ChatColor.WHITE + "First played time: " + ChatColor.AQUA + new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date(obj.getFirstPlayed())));
				} else sender.sendMessage(ChatColor.WHITE + "First played time: " + ChatColor.AQUA + "Never played!");
				if(obj.getLastPlayed() != 0)
				{
					sender.sendMessage(ChatColor.WHITE + "Last played time: " + ChatColor.AQUA + new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date(obj.getLastPlayed())));
				} else sender.sendMessage(ChatColor.WHITE + "Last played time: " + ChatColor.AQUA + "Never played!");
				sender.sendMessage(ChatColor.WHITE + "Exp level: " + ChatColor.AQUA + obj.getLevel());
				sender.sendMessage(ChatColor.WHITE + "Maximum air ticks: " + ChatColor.AQUA + obj.getMaximumAir());
				sender.sendMessage(ChatColor.WHITE + "Remaining air ticks: " + ChatColor.AQUA + obj.getRemainingAir());
				sender.sendMessage(ChatColor.WHITE + "Hostname: " + ChatColor.AQUA + obj.getAddress().getHostString());
				sender.sendMessage(ChatColor.WHITE + "Port: " + ChatColor.AQUA + obj.getAddress().getPort());
				sender.sendMessage(ChatColor.WHITE + "Position: " + ChatColor.AQUA + "X=" + obj.getLocation().getBlockX() + " Y=" + obj.getLocation().getBlockY() + " Z=" + obj.getLocation().getBlockZ());
				sender.sendMessage(ChatColor.GRAY + "----------------------------------");
				return true;
			} else
			{
				sender.sendMessage(ChatColor.GREEN+"[Bamboo Island] "+ChatColor.RED+"�ܱ�Ǹ������Ȩִ�д����");
				return true;
			}
		} else if(args[0].equalsIgnoreCase("plugins"))
		{
			if(sender.hasPermission("BambooManager.Administrator"))
			{
				StringBuilder pluginList = new StringBuilder();
				for(Plugin plug:Bukkit.getPluginManager().getPlugins())
				{
					StringBuilder permissions = new StringBuilder();
					for(Permission per : plug.getDescription().getPermissions())
					{
						permissions.append(ChatColor.YELLOW + "  Ȩ�޽ڵ�: " + ChatColor.GOLD).append(per.getName()).append("\n").append(ChatColor.YELLOW).append("  Ȩ������: ").append(ChatColor.GOLD).append(per.getDescription()).append("\n").append(ChatColor.YELLOW).append("  Ĭ��Ȩ��ӵ����: ").append(ChatColor.GOLD).append(per.getDefault().toString().replace("true", "�������")
								.replace("false", "����ӵ��").replace("op", "����������Ա").replace("not op", "�Ƿ���������Ա")).append("\n  ----------------------------------\n");
					}
					pluginList.append("\n").append(autoListedString(ChatColor.WHITE + "�����: " + ChatColor.AQUA, (plug.getName() + "\n"))).append(autoListedString(ChatColor.WHITE + "�������: " + ChatColor.AQUA, (plug.getDescription().getDescription() + "\n"))).append(autoListedString(ChatColor.WHITE + "�������: " + ChatColor.AQUA, (stringListToString(plug.getDescription().getAuthors()) + "\n"))).append(autoListedString(ChatColor.WHITE + "API �汾: " + ChatColor.AQUA, (plug.getDescription().getAPIVersion() + "\n"))).append(autoListedString(ChatColor.WHITE + "����汾: " + ChatColor.AQUA, (plug.getDescription().getVersion() + "\n"))).append(autoListedString(ChatColor.WHITE + "�������: " + ChatColor.AQUA, (stringListToString(plug.getDescription().getDepend()) + "\n"))).append(autoListedString(ChatColor.WHITE + "���Ȩ��: \n" + ChatColor.GOLD + "  ----------------------------------\n", (permissions + "\n")));
				}
				sender.sendMessage("��2[Bamboo Island ���ϵͳ] ��f�Ѱ�װ�ķ��������: \n"+pluginList);
				return true;
			} else
			{
				sender.sendMessage(ChatColor.GREEN+"[Bamboo Island] "+ChatColor.RED+"�ܱ�Ǹ������Ȩִ�д����");
				return true;
			}
		} else if(args[0].equalsIgnoreCase("info"))
		{
			sender.sendMessage("��2[Bamboo Island ���ϵͳ] ��f�Ѱ�װ�� Bamboo Manager:");
			sender.sendMessage(ChatColor.BLUE + "���� Author: " + ChatColor.WHITE + "Exzh_PMGI");
			sender.sendMessage(ChatColor.BLUE + "�汾 Version: " + ChatColor.WHITE + "1.2.4.1 [Release]");
			return true;
		} else if(args[0].equalsIgnoreCase("playerlist"))
		{
			StringBuilder msg = new StringBuilder(ChatColor.GREEN + "[Bamboo Island] " + ChatColor.WHITE + "��ǰ���ߵ����: ");
			for(Player pl : Bukkit.getOnlinePlayers())
			{
				msg.append(pl.getName()).append(", ");
			}
			msg = new StringBuilder(StringUtils.substringBeforeLast(msg.toString(), ","));
			sender.sendMessage(msg.toString());
			return true;
		} else if(args[0].equalsIgnoreCase("adminkits"))
		{
			if(sender.hasPermission("BambooManager.Administrator") && sender instanceof Player) {
				Player ply = (Player)sender;
				// �����Ʒ��
				for(int i=0;i<9;++i) {
					ply.getInventory().setItem(i,new ItemStack(Material.AIR));
				}
				BambooItems items = new BambooItems();
				ply.getInventory().setItem(0, items.getWorldEditorTool());
				ply.getInventory().setItem(1, items.getEntityKillerTool());
				ply.getInventory().setItem(2, items.getGamemodeSwitcherTool());
				ply.getInventory().setItem(3, items.getKickTool());
				ply.getInventory().setItem(4, items.getRandomPlaceTeleportTool());
				ply.getInventory().setItem(5, items.getRandomPlayerTeleportTool());
				ply.getInventory().setItem(6, items.getFlyModeTool());
				ply.getInventory().setItem(7, items.getEffectManagerTool());
				ply.getInventory().setItem(8,items.getTeleportReel(true));
			}
			return true;
    	} else {
			sender.sendMessage(ChatColor.GREEN+"[Bamboo Island] "+ChatColor.RED+"�ܱ�Ǹ������Ȩִ�д����");
			return true;
		}
	}
	return false;
}

private String autoListedString(String key,String value)
{
	if(isNullEmptyString(value)) return "";
	else return (key + value);
}

private boolean isNullEmptyString(String input)
{
	return input.replace("\n", "").isEmpty();
}

private String stringListToString(List<String> list)
{
	StringBuilder outBuilder = new StringBuilder();
	for(String s : list)
	{
		outBuilder.append(s).append(", ");
	}
	String out = outBuilder.toString();
	out = StringUtils.substringBeforeLast(out,",");
	return out;
}

private int loadedChunks()
{
  int _length = 0;
	for (World world : Bukkit.getWorlds()) {
		_length += world.getLoadedChunks().length;
	}
  return _length;
}

public static void broadcastToOnlinePlayers(String msg)
{
	for(Player player : Bukkit.getServer().getOnlinePlayers())
	{
		player.sendMessage(msg);
	}
}
}