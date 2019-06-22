package fr.maxlego08.shop;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import fr.maxlego08.shop.command.CommandManager;
import fr.maxlego08.shop.config.Config;
import fr.maxlego08.shop.manager.ShopManager;
import fr.maxlego08.shop.packet.PacketManager;
import fr.maxlego08.shop.utils.Persist;
import fr.maxlego08.shop.utils.Saver;
import fr.maxlego08.shop.utils.logger.Logger;
import fr.maxlego08.shop.utils.logger.Logger.LogType;
import net.milkbowl.vault.economy.Economy;
import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.GsonBuilder;

public class Shop extends JavaPlugin{

	private Gson gson;
	private final Logger logger;
	private long enableTime;
	private Persist persist;
	private List<Saver> savers = new ArrayList<>();
	private static Shop shop;
	private ShopManager shopManager;
	private CommandManager commandManager;
	private Economy economy;
	
	public Shop() {
		logger = new Logger(getDescription().getFullName());
		shop = this;
	}
	
	@Override
	public void onEnable() {

		enableTime = System.currentTimeMillis();
		
		logger.log("=== ENABLE START ===");
		logger.log("Plugin Version V<&>c" + getDescription().getVersion(), LogType.INFO);
		
		getDataFolder().mkdirs();
		
		gson = getGsonBuilder().create();
		persist = new Persist(this);
		new PacketManager();
		
		setupEconomy();
		
		addSave(new Config());
		
		this.shopManager = new ShopManager(this);
		this.commandManager = new CommandManager(this);

		addListener(shopManager);
		
		savers.forEach(saver -> saver.load());
		
		logger.log(Config.items.size() + " item loaded", LogType.INFO);
		
		logger.log("=== ENABLE DONE <&>7(<&>6" + Math.abs(enableTime - System.currentTimeMillis()) + "ms<&>7) <&>e===");
		
	}
	
	public GsonBuilder getGsonBuilder() {
		return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().serializeNulls()
				.excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.VOLATILE);
	}
	
	private boolean setupEconomy() {

		if (getServer().getPluginManager().getPlugin("Vault") == null) {

			return false;
		}

		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);

		if (rsp == null) {

			return false;
		}

		this.economy = rsp.getProvider();

		return this.economy != null;
	}
	
	@Override
	public void onDisable() {
		savers.forEach(saver -> saver.save());
	}
	
	public void addListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, this);
	}
	
	public void addSave(Saver saver){
		this.savers.add(saver);
	}
	
	public Logger getLog(){
		return logger;
	}
	
	public Gson getGson() {
		return gson;
	}
	
	public Persist getPersist() {
		return persist;
	}
	
	public static Shop s() {
		return shop;
	}
	
	public CommandManager getCommandManager() {
		return commandManager;
	}
	
	public ShopManager getShopManager() {
		return shopManager;
	}
	
	public Economy getEconomy() {
		return economy;
	}
	
}
