package xyz.bambooisland.manager;

import java.util.Arrays;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.bambooisland.utils.*;

public class BambooUuid {
	public static String prefix()
	  {
		return ChatColor.BLUE + "[Bamboo Island Provider]";
	  }
	  
	  public static String noPermMsg()
	  {
		  return ChatColor.RED + "您的权限不足以执行此命令";
	  }
	  
	  public static String getName(String uuid)
	  {
		  String name;
	    try
	    {
	      name = (String)new NameFetcher(Arrays.asList(new UUID[] { UUID.fromString(uuid) })).call().get(UUID.fromString(uuid));
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      return null;
	    }
	    return name;
	  }
	  
	  public static String getUUID(Player player)
	  {
		  String uuid;
	    try
	    {
	      uuid = ((UUID)new UUIDFetcher(Arrays.asList(new String[] { player.getName() })).call().get(player.getName())).toString();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      return null;
	    }
	    return uuid;
	  }
	  
	  public static String getUUID(String name)
	  {
		  String uuid;
	    try
	    {
	      uuid = ((UUID)new UUIDFetcher(Arrays.asList(new String[] { name })).call().get(name)).toString();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      return null;
	    }
	    return uuid;
	  }
}
