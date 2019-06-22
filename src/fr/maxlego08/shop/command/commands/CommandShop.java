package fr.maxlego08.shop.command.commands;

import org.bukkit.command.CommandSender;

import fr.maxlego08.shop.Shop;
import fr.maxlego08.shop.command.VCommand;
import fr.maxlego08.shop.config.Config;

public class CommandShop extends VCommand {

	public CommandShop() {
		super(null , true);
		this.addSubCommand("shop");
	}

	@Override
	protected CommandType perform(Shop main, CommandSender sender, String... args) {
		main.getShopManager().show(getPlayer());
		return CommandType.SUCCESS;
	}

	@Override
	public String getPermission() {
		return Config.shopUsePermission;
	}

	@Override
	public String getSyntax() {
		return null;
	}

}
