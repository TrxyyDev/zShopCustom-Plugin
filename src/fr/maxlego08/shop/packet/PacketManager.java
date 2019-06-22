package fr.maxlego08.shop.packet;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import fr.maxlego08.shop.packet.client.PacketClientShop;
import fr.maxlego08.shop.packet.server.PacketServerShop;
import net.minecraft.server.v1_7_R4.EnumProtocol;
import net.minecraft.util.com.google.common.collect.BiMap;

public class PacketManager {

	public PacketManager() {
		try {
			setUp();
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	private void setUp() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Class<EnumProtocol> clazz = EnumProtocol.class;
		Field f = null;
		BiMap<Integer, Class<?>> packetsMap = null;
		
		f = clazz.getDeclaredField("i");
		f.setAccessible(true);
		packetsMap = (BiMap<Integer, Class<?>>) f.get(EnumProtocol.PLAY);
		packetsMap.put(103, PacketClientShop.class);
		setAsPlayPacket(PacketClientShop.class);
		
		f = clazz.getDeclaredField("h");
		f.setAccessible(true);
		packetsMap = (BiMap<Integer, Class<?>>) f.get(EnumProtocol.PLAY);
		packetsMap.put(103, PacketServerShop.class);
		setAsPlayPacket(PacketServerShop.class);
		
	}
	
	private void setAsPlayPacket(Class<?> clazz) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		Field f = EnumProtocol.class.getDeclaredField("f");
		f.setAccessible(true);
		@SuppressWarnings("unchecked")
		Map<Class<?>, EnumProtocol> map = (Map<Class<?>, EnumProtocol>) f.get(EnumProtocol.PLAY);
		map.put(clazz, EnumProtocol.PLAY);
	}
}
