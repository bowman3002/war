package bukkit.tommytony.war.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import bukkit.tommytony.war.War;
import bukkit.tommytony.war.WarCommandHandler;

public abstract class AbstractWarCommand {

	private CommandSender sender;
	protected String[] args;
	protected WarCommandHandler handler;
	public AbstractWarCommand(WarCommandHandler handler, CommandSender sender, String[] args) {
		this.handler = handler;
		this.setSender(sender);
		this.args = args;
	}

	abstract public boolean handle();

	public void msg(String message) {
		War.war.msg(getSender(), message);
	}
	
	public void badMsg(String message) {
		War.war.badMsg(getSender(), message);
	}

	public void setSender(CommandSender sender) {
		this.sender = sender;
	}

	public CommandSender getSender() {
		return sender;
	}
}
