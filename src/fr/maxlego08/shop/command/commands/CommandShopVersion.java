package fr.maxlego08.shop.command.commands;

import org.bukkit.command.CommandSender;

import fr.maxlego08.shop.Shop;
import fr.maxlego08.shop.command.VCommand;
import fr.maxlego08.shop.config.Config;

public class CommandShopVersion extends VCommand {

	public CommandShopVersion(VCommand c) {
		super(c);
		this.addSubCommand("version");
		this.addSubCommand("ver");
		this.addSubCommand("v");
		this.addSubCommand("about");
	}
	
	@Override
	protected CommandType perform(Shop main, CommandSender sender, String... args) {

		sender.sendMessage(Config.prefix + " §ePlugin développé par Maxlego08");
		
		return CommandType.SUCCESS;
	}

	@Override
	public String getPermission() {
		return null;
	}

	@Override
	public String getSyntax() {
		return null;
	}

}
