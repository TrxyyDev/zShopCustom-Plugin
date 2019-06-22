package fr.maxlego08.shop.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.maxlego08.shop.Shop;
import fr.maxlego08.shop.command.VCommand.CommandType;
import fr.maxlego08.shop.command.commands.CommandShop;
import fr.maxlego08.shop.command.commands.CommandShopVersion;
import fr.maxlego08.shop.config.Config;
import fr.maxlego08.shop.utils.TextUtil;

public class CommandManager implements CommandExecutor{

	private final Shop main;
	private List<VCommand> commands = new ArrayList<>();
	
	public CommandManager(Shop main) {
		this.main = main;
		
		/* Register command from plugin.yml */
		
		main.getCommand("shop").setExecutor(this);
		
		/* Register VCommand */
		
		VCommand command = addCommand(new CommandShop());
		addCommand(new CommandShopVersion(command));
		
	}
	
	private VCommand addCommand(VCommand command){
		commands.add(command);
		return command;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] strings){
		
		for(VCommand vcommand : commands){
			if (vcommand.getSubCommands().contains(command.getName().toLowerCase())){
				if (strings.length == 0 && vcommand.getParent() == null){
					processRequirements(vcommand, sender, strings);
					return true;
				}
			}else if (strings.length != 0 && vcommand.getParent() != null && vcommand.getParent().getSubCommands().contains(command.getName().toLowerCase())){
				String cmd = strings[0].toLowerCase();
				if (vcommand.getSubCommands().contains(cmd)){
					processRequirements(vcommand, sender, strings);
					return true;
				}
			}
		}
        sender.sendMessage(TextUtil.color(Config.prefix + " " + Config.unknowCommand));	
		return true;
	}
	
	 private void processRequirements(VCommand command, CommandSender sender, String[] strings) {
        if (!(sender instanceof Player) && command.isNoConsole()) {
            sender.sendMessage(TextUtil.color(Config.prefix + " &eonly player can be use this command"));
            return;
        }
        if (command.getPermission() == null || sender.hasPermission(command.getPermission())) {
        	command.setSender(sender);
        	CommandType returnType = command.perform(main, sender, strings);
             if (returnType == CommandType.SYNTAX_ERROR) {
                 sender.sendMessage(TextUtil.color(Config.prefix + " " + Config.syntaxeError.replace("%command%", command.getSyntax())));
             }
            return;
        }
        sender.sendMessage(TextUtil.color(Config.prefix + " " + Config.noPermission));	
	    
	 }
	
	public List<VCommand> getCommands() {
		return commands;
	}

	
}
