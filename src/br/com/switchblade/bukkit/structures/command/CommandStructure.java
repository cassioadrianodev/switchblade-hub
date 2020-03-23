package br.com.switchblade.bukkit.structures.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class CommandStructure extends Command {

	public CommandStructure(String cmd) {
		super(cmd);
	}
	
	public CommandStructure(String name, List<String> aliases) {
		super(name, "", "", aliases);
	}

	protected boolean isPlayer(CommandSender sender) {
		return sender instanceof Player;
	}

	public abstract boolean execute(CommandSender sender, String lb, String[] args);

	protected void sendExecutorMessage(CommandSender sender) {
		sender.sendMessage("§cComando somente para jogadores.");
	}

	protected void sendPermissionMessage(CommandSender sender) {
		sender.sendMessage("§cVocê não tem permissão para executar este comando.");
	}
	
	protected void sendOfflinePlayerMessage(CommandSender sender) {
		sender.sendMessage("§cEste jogador não está online no momento.");
	}

	protected boolean hasPermission(CommandSender sender, String perm) {
		return sender.hasPermission("switchblade.hub." + perm);
	}

	protected boolean isInteger(String string) {
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	protected String getArgs(String[] args, int start) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = start; i < args.length; i++) {
			stringBuilder.append(args[i]).append(" ");
		}
		return stringBuilder.toString();
	}
}