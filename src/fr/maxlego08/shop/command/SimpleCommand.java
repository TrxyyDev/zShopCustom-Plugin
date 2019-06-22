package fr.maxlego08.shop.command;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.maxlego08.shop.utils.TextUtil;

public abstract class SimpleCommand{

	private CommandSender sender;
	
	public void setSender(CommandSender sender) {
		this.sender = sender;
	}
	
	public CommandSender getSender() {
		return sender;
	}
	
	public void msg(String str, Object... args)
	{
		sender.sendMessage(TextUtil.color(TextUtil.parse(str, args)));
	}
	
	public void sendMessage(String msg)
	{
		sender.sendMessage(TextUtil.color(msg));
	}
	
	public void sendMessage(List<String> msgs)
	{
		for(String msg : msgs) this.sendMessage(msg);		
	}
	
	public Player getPlayer(){
		if (sender instanceof Player) return (Player)sender;		
		throw new IllegalArgumentException("Sender is not a player !");
	}

	
}
