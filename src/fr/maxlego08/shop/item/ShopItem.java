package fr.maxlego08.shop.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopItem {

	private final int categoryId;
	private final Material material;
	private final byte data;
	private final int how;
	private final double sellPrice;
	private final double buyPrice;
	private String customName;
	private Map<String, Integer> enchants = new HashMap<>();
	private List<String> lore = new ArrayList<>();

	public ShopItem(int categoryId, Material material, byte data, int how, double sellPrice, double buyPrice,
			String customName, List<String> lore) {
		super();
		this.categoryId = categoryId;
		this.material = material;
		this.data = data;
		this.how = how;
		this.sellPrice = sellPrice;
		this.buyPrice = buyPrice;
		this.customName = customName;
		this.lore = lore;
	}

	public ShopItem(int categoryId, Material material, byte data, int how, double sellPrice, double buyPrice,
			Map<String, Integer> enchants, List<String> lore) {
		super();
		this.categoryId = categoryId;
		this.material = material;
		this.data = data;
		this.how = how;
		this.sellPrice = sellPrice;
		this.buyPrice = buyPrice;
		this.enchants = enchants;
		this.lore = lore;
	}

	public ShopItem(int categoryId, Material material, byte data, int how, double sellPrice, double buyPrice,
			String customName, Map<String, Integer> enchants) {
		super();
		this.categoryId = categoryId;
		this.material = material;
		this.data = data;
		this.how = how;
		this.sellPrice = sellPrice;
		this.buyPrice = buyPrice;
		this.customName = customName;
		this.enchants = enchants;
	}

	public ShopItem(int categoryId, Material material, byte data, int how, double sellPrice, double buyPrice,
			List<String> lore) {
		super();
		this.categoryId = categoryId;
		this.material = material;
		this.data = data;
		this.how = how;
		this.sellPrice = sellPrice;
		this.buyPrice = buyPrice;
		this.lore = lore;
	}

	public ShopItem(int categoryId, Material material, byte data, int how, double sellPrice, double buyPrice,
			Map<String, Integer> enchants) {
		super();
		this.categoryId = categoryId;
		this.material = material;
		this.data = data;
		this.how = how;
		this.sellPrice = sellPrice;
		this.buyPrice = buyPrice;
		this.enchants = enchants;
	}

	public ShopItem(int categoryId, Material material, byte data, int how, double sellPrice, double buyPrice,
			String customName) {
		super();
		this.categoryId = categoryId;
		this.material = material;
		this.data = data;
		this.how = how;
		this.sellPrice = sellPrice;
		this.buyPrice = buyPrice;
		this.customName = customName;
	}

	public ShopItem(int categoryId, Material material, byte data, int how, double sellPrice, double buyPrice) {
		super();
		this.categoryId = categoryId;
		this.material = material;
		this.data = data;
		this.how = how;
		this.sellPrice = sellPrice;
		this.buyPrice = buyPrice;
	}

	public ShopItem(int categoryId, Material material, byte data, int how, double sellPrice, double buyPrice,
			String customName, Map<String, Integer> enchants, List<String> lore) {
		super();
		this.categoryId = categoryId;
		this.material = material;
		this.data = data;
		this.how = how;
		this.sellPrice = sellPrice;
		this.buyPrice = buyPrice;
		this.customName = customName;
		this.enchants = enchants;
		this.lore = lore;
	}

	public Material getMaterial() {
		return material;
	}

	public int getCategoryId() {
		return categoryId;
	}
	
	public byte getData() {
		return data;
	}

	public int getHow() {
		return how;
	}

	public double getSellPrice() {
		return sellPrice;
	}

	public double getBuyPrice() {
		return buyPrice;
	}

	public String getCustomName() {
		return customName;
	}

	public Map<String, Integer> getEnchants() {
		return enchants;
	}
	
	public boolean hasEnchant(){
		return enchants.size() != 0;
	}
	
	public boolean hasName(){
		return customName != null;
	}
	
	public boolean hasLore(){
		return lore.size() != 0;
	}
	
	public List<String> getLore() {
		return lore;
	}
	
	public ItemStack getItem(){
		ItemStack item = new ItemStack(getMaterial(), getHow(), getData());
		if (hasName() || hasEnchant() || hasLore()){
			ItemMeta itemMeta = item.getItemMeta();
			if (hasName()) itemMeta.setDisplayName(customName);
			if (hasEnchant()) enchants.forEach((enchant, power) -> itemMeta.addEnchant(Enchantment.getByName(enchant), power, true));
			if (hasLore()) itemMeta.setLore(lore);
			item.setItemMeta(itemMeta);
		}
		return item;
	}
	
	
}
