package br.com.switchblade.bukkit.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import br.com.switchblade.bukkit.structures.command.CommandStructure;

public class PingCommand extends CommandStructure {

	public PingCommand() {
		super("ping");
	}

	public boolean execute(CommandSender sender, String lb, String[] args) {
		if (!isPlayer(sender)) {
			sendExecutorMessage(sender);
			return true;
		}
		Player player = (Player) sender;

		if (args.length == 0) {
			int ping = ((CraftPlayer) player).getHandle().ping;

			player.sendMessage("§aSeu ping é de §f" + ping + "ms§a.");
		}

		if (args.length == 1) {
			Player target = Bukkit.getPlayer(args[0]);
			int ping = ((CraftPlayer) target).getHandle().ping;

			player.sendMessage("§aO ping do jogador §f" + target.getName() + " §aé de §f" + ping + "ms§a.");
		}
		return false;
	}
}
