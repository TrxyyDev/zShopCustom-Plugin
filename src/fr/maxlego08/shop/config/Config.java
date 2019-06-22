package fr.maxlego08.shop.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import fr.maxlego08.shop.Shop;
import fr.maxlego08.shop.item.ShopItem;
import fr.maxlego08.shop.utils.Saver;

public class Config implements Saver{

	public static String prefix = "&8(&bShop&8)";
	public static String noPermission = "&cYou don't have permission do to this command.";
	public static String syntaxeError = "&bYou have to order like this &e%command%";
	public static String unknowCommand = "&cThis command does not exist or your syntax contains an error";
	public static String buy = "&7You have just bought &ax%how% %type% &7to &a%price%$&7.";
	public static String sell = "&7You have just sold §ax%how% %type% &7to &a%price%&7$";
	public static String canBuy = "&cYou must have &b%price%&c to buy &bx%how% %type%&c.";
	public static String canSell = "&cYou do not have this item in your inventory";
	
	public static String shopUsePermission = "shop.use";
	
	public static List<ShopItem> items = new ArrayList<>();
			
	static{
		
		items.add(new ShopItem(0, Material.STONE, (byte)0, 1, 0.1, 10.0));
		items.add(new ShopItem(0, Material.DIRT, (byte)0, 1, 0.1, 10.0));
		items.add(new ShopItem(0, Material.GRASS, (byte)0, 1, 0.1, 10.0));
		items.add(new ShopItem(0, Material.GRAVEL, (byte)0, 1, 0.1, 10.0));
		items.add(new ShopItem(0, Material.SAND, (byte)0, 1, 0.1, 10.0));
		items.add(new ShopItem(0, Material.OBSIDIAN, (byte)0, 1, 1, 100.0));
		items.add(new ShopItem(0, Material.TNT, (byte)0, 1, 0.5, 50.0));
		
		items.add(new ShopItem(1, Material.POTATO_ITEM, (byte)0, 1, 0.25, 15.0));
		items.add(new ShopItem(1, Material.CARROT_ITEM, (byte)0, 1, 0.25, 15.0));
		items.add(new ShopItem(1, Material.CACTUS, (byte)0, 1, 0.25, 15.0));
		items.add(new ShopItem(1, Material.PUMPKIN, (byte)0, 1, 0.25, 15.0));
		items.add(new ShopItem(1, Material.MELON, (byte)0, 1, 0.25, 15.0));
		items.add(new ShopItem(1, Material.SUGAR_CANE, (byte)0, 1, 0.25, 15.0));
		
		items.add(new ShopItem(2, Material.COAL, (byte)0, 1, 0.25, 15.0));
		items.add(new ShopItem(2, Material.COAL, (byte)1, 1, 0.25, 15.0));
		items.add(new ShopItem(2, Material.IRON_INGOT, (byte)0, 1, 0.25, 15.0));
		items.add(new ShopItem(2, Material.GOLD_INGOT, (byte)0, 1, 0.25, 15.0));
		items.add(new ShopItem(2, Material.REDSTONE, (byte)0, 1, 0.25, 15.0));
		items.add(new ShopItem(2, Material.INK_SACK, (byte)4, 1, 0.25, 15.0));
		items.add(new ShopItem(2, Material.DIAMOND, (byte)0, 1, 0.25, 15.0));
		items.add(new ShopItem(2, Material.EMERALD, (byte)0, 1, 0.25, 15.0));
		
		items.add(new ShopItem(3, Material.COOKED_BEEF, (byte)0, 1, 0.25, 15.0));
		items.add(new ShopItem(3, Material.COOKED_CHICKEN, (byte)0, 1, 0.25, 15.0));
		items.add(new ShopItem(3, Material.COOKED_FISH, (byte)0, 1, 0.25, 15.0));
		items.add(new ShopItem(3, Material.APPLE, (byte)0, 1, 0.25, 15.0));
		items.add(new ShopItem(3, Material.GOLDEN_APPLE, (byte)0, 1, 0.25, 15.0));
		items.add(new ShopItem(3, Material.GOLDEN_CARROT, (byte)0, 1, 0.25, 15.0));
		
		items.add(new ShopItem(4, Material.EXP_BOTTLE, (byte)0, 1, 0.25, 15.0));
		items.add(new ShopItem(4, Material.MONSTER_EGG, (byte)50, 1, 0.25, 15.0));
		items.add(new ShopItem(4, Material.DIAMOND_AXE, (byte)0, 1, 0.25, 15.0, "§eThe Diamond Axe"));
		items.add(new ShopItem(4, Material.GOLD_BARDING, (byte)0, 1, 0.25, 15.0, Arrays.asList("line 1","line 2", "an other line :o")));
		Map<String, Integer> map = new HashMap<>();
		map.put(Enchantment.ARROW_FIRE.getName(), 1);
		map.put(Enchantment.PROTECTION_ENVIRONMENTAL.getName(), 0);
		items.add(new ShopItem(4, Material.IRON_AXE, (byte)0, 1, 0.25, 15.0, map));
		
		
	}
	
	public static transient Config i = new Config();
	
	@Override
	public void save() {
		Shop.s().getPersist().save(i);
	}

	@Override
	public void load() {
		Shop.s().getPersist().loadOrSaveDefault(i, Config.class, "config");
	}

}
