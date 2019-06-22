package fr.maxlego08.shop.packet.client;

import java.io.IOException;

import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;

import fr.maxlego08.shop.item.ShopItem;
import fr.maxlego08.shop.utils.ShopAction;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketDataSerializer;
import net.minecraft.server.v1_7_R4.PacketListener;

public class PacketClientShop extends Packet{

	private final ShopAction action;
	private ShopItem item;
	private int id;
	private String errorMessage;
	
	public PacketClientShop(ShopAction action, int i) {
		this.action = action;
		this.id = i;
	}

	public PacketClientShop(ShopAction action, ShopItem item) {
		this.action = action;
		this.item = item;
	}

	public PacketClientShop(ShopAction action, String errorMessage) {
		super();
		this.action = action;
		this.errorMessage = errorMessage;
	}
	
	@Override
	public void a(PacketDataSerializer arg0) throws IOException { }

	@Override
	public void b(PacketDataSerializer r) throws IOException {
		r.writeInt(action.getId());
		if (action.equals(ShopAction.ADD_ITEMS)){
			writeItem(r);
		}else if (action.equals(ShopAction.SHOW_SHOP)){
			r.writeInt(id);
		}else if (action.equals(ShopAction.SEND_ERROR)){
			r.a(errorMessage);
		}
	}

	@Override
	public void handle(PacketListener arg0) {
		
	}

	private void writeItem(PacketDataSerializer r){
		net.minecraft.server.v1_7_R4.ItemStack stack = CraftItemStack.asNMSCopy(this.item.getItem());				
		r.a(stack);
		r.writeDouble(this.item.getBuyPrice());
		r.writeDouble(this.item.getSellPrice());
		r.writeInt(this.item.getCategoryId());
	}
	
}
