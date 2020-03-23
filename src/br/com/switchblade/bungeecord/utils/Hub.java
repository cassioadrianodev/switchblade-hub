package br.com.switchblade.bungeecord.utils;

import br.com.switchblade.bungeecord.events.GuildConnection;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Hub extends Command {

	public Hub() {
		super("hub", "", "lobby");
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			if (!GuildConnection.nicks.contains(p.getName())) {
				GuildConnection.nicks.add(p.getName());
			}
			p.sendMessage("§aConectando ao lobby...");
			p.connect(BungeeCord.getInstance().getServerInfo("lobby-01"));
		}
	}
}
