package fr.maxlego08.shop.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import fr.maxlego08.shop.Shop;
import fr.maxlego08.shop.config.Config;
import fr.maxlego08.shop.item.ShopItem;
import fr.maxlego08.shop.packet.client.PacketClientShop;
import fr.maxlego08.shop.utils.ShopAction;
import fr.maxlego08.shop.utils.TextUtil;
import net.minecraft.server.v1_7_R4.PlayerConnection;

public class ShopManager implements Listener {

	private final Shop shop;
	private List<Player> hasItem = new ArrayList<>();

	public ShopManager(Shop shop) {
		this.shop = shop;
	}

	@EventHandler
	protected void onQuit(PlayerQuitEvent event) {
		if (hasItem.contains(event.getPlayer()))
			hasItem.remove(event.getPlayer());
	}

	public void show(Player player) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		connection.sendPacket(new PacketClientShop(ShopAction.SHOW_CATEGORIES, 0));
	}

	public void showCategory(Player player, int category) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		if (!hasItem.contains(player)) {
			Config.items.forEach(item -> {
				connection.sendPacket(new PacketClientShop(ShopAction.ADD_ITEMS, item));
			});
		}
		connection.sendPacket(new PacketClientShop(ShopAction.SHOW_SHOP, category));
	}

	public void buyItem(ItemStack item, double buy, Player player, int quantity) {
		if (!existeItem(item, buy, true))
			return;

		if (hasInventoryFull(player)) {
			player.sendMessage("§eVotre inventaire est plein ! Vous ne pouvez pas acheter d'item");
			return;
		}

		if (shop.getEconomy().getBalance(player.getName()) < (buy * (double) quantity)) {
			player.sendMessage(TextUtil.color(Config.prefix + " "
					+ Config.canBuy.replace("%how%", String.valueOf(quantity))
							.replace("%type%", TextUtil.getMaterialLowerAndMajAndSpace(item.getType()))
							.replace("%price%", String.valueOf((buy * (double) quantity)))));
			return;
		}

		item.setAmount(quantity);
		player.getInventory().addItem(item);
		player.sendMessage(TextUtil.color(Config.prefix + " "
				+ Config.buy.replace("%how%", String.valueOf(quantity))
						.replace("%type%", TextUtil.getMaterialLowerAndMajAndSpace(item.getType()))
						.replace("%price%", String.valueOf((buy * (double) quantity)))));
		shop.getEconomy().withdrawPlayer(player.getName(), (buy * (double) quantity));
	}

	public void sellItem(ItemStack itemStack, double sell, Player player, int quantity) {

		int item = 0;
		int re = 0;
		ItemStack[] arrayOfItemStack;
		int x = (arrayOfItemStack = player.getInventory().getContents()).length;
		for (int i = 0; i < x; i++) {
			ItemStack contents = arrayOfItemStack[i];

			if ((contents != null) && (contents.getType() != Material.AIR) && (contents.getType() == itemStack.getType())
					&& contents.getData().getData() == itemStack.getData().getData()) {
				item = item + contents.getAmount();
			}
		}
		if (item == 0) {
			player.sendMessage(TextUtil.color(Config.prefix + " "
					+ Config.canSell.replace("%how%", String.valueOf(quantity))
							.replace("%type%", TextUtil.getMaterialLowerAndMajAndSpace(itemStack.getType()))
							.replace("%price%", String.valueOf((sell * (double) quantity)))));
			return;
		}
		for (int i = 0; i < x; i++) {
			ItemStack contents = arrayOfItemStack[i];

			if ((contents != null) && (contents.getType() != Material.AIR)
					&& (contents.getType() == itemStack.getType())
					&& contents.getData().getData() == itemStack.getData().getData()) {
				if (re < item) {
					re++;
					player.getInventory().remove(contents.getType());

				}
			}
		}
		double prix = item * sell;
		player.sendMessage(TextUtil.color(Config.prefix + " "
				+ Config.sell.replace("%how%", String.valueOf(item))
						.replace("%type%", TextUtil.getMaterialLowerAndMajAndSpace(itemStack.getType()))
						.replace("%price%", String.valueOf(prix))));
		shop.getEconomy().depositPlayer(player.getName(), prix);
	}

	private boolean existeItem(ItemStack item, double sell, boolean b) {
		for (ShopItem ictem : Config.items) {
			if (ictem.getItem().equals(item)) {
				if (b && ictem.getBuyPrice() == sell)
					return true;
				else if (!b && ictem.getSellPrice() == sell)
					return true;
			}
		}
		return false;
	}

	public boolean hasInventoryFull(Player p) {
		int slot = 0;
		ItemStack[] arrayOfItemStack;
		int x = (arrayOfItemStack = p.getInventory().getContents()).length;
		for (int i = 0; i < x; i++) {
			ItemStack contents = arrayOfItemStack[i];
			if ((contents == null)) {
				slot++;
			}
		}
		return slot == 0;
	}

	@SuppressWarnings("deprecation")
	private boolean hasItem(Material material, byte data, int quantity, Player p) {
		int item = 0;
		ItemStack[] arrayOfItemStack;
		int x = (arrayOfItemStack = p.getInventory().getContents()).length;
		for (int i = 0; i < x; i++) {
			ItemStack contents = arrayOfItemStack[i];
			if ((contents != null) && (contents.getType() != Material.AIR) && (contents.getType() == material)
					&& contents.getData().getData() == data) {
				item = item + contents.getAmount();
			}
		}
		if (item < quantity)
			return false;
		return true;
	}

	@SuppressWarnings("deprecation")
	private void removeItem(int q, Material material, byte data, Player p) {
		int item = q;
		ItemStack[] arrayOfItemStack;
		int x = (arrayOfItemStack = p.getInventory().getContents()).length;
		for (int i = 0; i < x; i++) {
			ItemStack contents = arrayOfItemStack[i];
			if ((contents != null) && (contents.getType() != Material.AIR) && (contents.getType() == material)
					&& contents.getData().getData() == data) {
				if (item >= 64 && contents.getAmount() == 64 || item == contents.getAmount()) {
					p.getInventory().remove(contents);
				} else {
					ItemStack n = new ItemStack(contents.getType(), contents.getAmount() - item);
					p.getInventory().remove(contents);
					p.getInventory().addItem(n);
				}
			}
		}
	}

}
