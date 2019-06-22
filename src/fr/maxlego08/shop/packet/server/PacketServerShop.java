package fr.maxlego08.shop.packet.server;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.maxlego08.shop.Shop;
import fr.maxlego08.shop.item.ShopItem;
import fr.maxlego08.shop.utils.ShopAction;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketDataSerializer;
import net.minecraft.server.v1_7_R4.PacketListener;

public class PacketServerShop extends Packet{

	private ShopAction action;
	private int category;
	private Player player;
	private final Shop oldFight = Shop.s();
	private ItemStack item;
	private double buy;
	private double sell;
	private int quantity;
	
	@Override
	public void a(PacketDataSerializer r) throws IOException {
		this.player = Bukkit.getPlayer(r.c(32767));
		this.action = ShopAction.get(r.readInt());
		if (action.equals(ShopAction.SHOW_SHOP)){
			category = r.readInt();
		}else if (action.equals(ShopAction.BUY_ITEMS) || action.equals(ShopAction.SELL_ITEMS)){
			readItem(r);
		}
	}

	@Override
	public void b(PacketDataSerializer arg0) throws IOException { }

	@Override
	public void handle(PacketListener arg0) {
		switch (action) {
		case SHOW_SHOP:
			oldFight.getShopManager().showCategory(player, category);
			break;
		case BUY_ITEMS:
			oldFight.getShopManager().buyItem(item, buy, player, quantity);
			break;
		case SELL_ITEMS:
			oldFight.getShopManager().sellItem(item, sell, player, quantity);
			break;
		default:
			break;
		}
	}
	
	private void readItem(PacketDataSerializer r){
		item = CraftItemStack.asBukkitCopy(r.c());
		buy = r.readDouble();
		sell = r.readDouble();
		quantity = r.readInt();
	}
}
