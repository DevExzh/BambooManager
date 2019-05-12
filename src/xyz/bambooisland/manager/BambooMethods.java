package xyz.bambooisland.manager;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.net.URL;

class BambooMethods {
static String getCurrentDirPath()
{
	URL url = BambooManager.class.getProtectionDomain().getCodeSource().getLocation();
	String path = url.getPath();
	if(path.startsWith("file:"))
	{
		path = path.replace("file:", "");
	}
	if(path.contains(".jar!/"))
	{
		path = path.substring(0,path.indexOf(".jar/") + 4);
	}
	
	File file = new File(path);
	path = file.getParentFile().getAbsolutePath();
	return path;
}

static void addNullSectionToInventory(Inventory inventory, int count, int start_pos) {
	for(int i=0;i<count;++i) {
		ItemStack nullsec = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		ItemMeta m = nullsec.getItemMeta();
		m.setUnbreakable(true);
		nullsec.setItemMeta(m);
		inventory.setItem(start_pos+i,nullsec);
	}
}

static boolean converseBoolean(boolean bool) {
	if(bool) return false;
	else return true;
}

static Location getRandomLocation(Player player) {
	int random_x = (int)(-500+Math.random()*1001);
	int random_z = (int)(-500+Math.random()*1001);
	Location lo = player.getLocation();
	lo.setX(lo.getX() + random_x);
	lo.setZ(lo.getZ() + random_z);
	return lo;
}

static boolean isDangerous(Location location) {
	if(location.getBlock().getType() == Material.LAVA) return true;
	for(int i=0;i<255;++i) {
		Location _new = new Location(location.getWorld(),location.getX(),location.getY()-i,location.getZ());
		if(location.getBlock().getType() == Material.WATER || location.getBlock().getType() == Material.COBWEB) return true;
		if(location.getBlock().getType() != Material.LAVA && i<14) return false;
		else if((location.getBlock().getType() != Material.WATER || location.getBlock().getType() != Material.COBWEB) && i>14) return true;
	}
	return false;
}

static boolean isTallEnough(Location location) {
	boolean flg = true;
	for(int i=0;i<3;++i) {
		Location _new = new Location(location.getWorld(),location.getX(),location.getY()+i,location.getZ());
		if(_new.getBlock().getType() != Material.AIR) flg = false;
	}
	return flg;
}

static Location findSafeYLocation(Location location) {
	Location _pre = new Location(location.getWorld(),location.getX(),256d,location.getZ());
	Location _current = location;
	// 二分法查找 Y 轴上最安全的地方
	while (isDangerous(_current)) {
		_current = new Location(_pre.getWorld(),_pre.getX(),(_pre.getY() - _current.getY())/2d,_pre.getZ());
	}
	return _current;
}
}
