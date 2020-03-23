package br.com.switchblade.bungeecord.events;

import java.util.HashSet;

import br.com.switchblade.bungeecord.SwitchbladeBungeecord;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class GuildConnection implements Listener {

	public static HashSet<String> nicks = new HashSet<>();

	@EventHandler
	public void onConnect(ServerConnectEvent e) {
		if (nicks.contains(e.getPlayer().getName())) {
			String server = "lobby-01";
			boolean on = Boolean.parseBoolean(SwitchbladeBungeecord.lobby.get("lobby-01").split(",")[1]);
			for (String a : SwitchbladeBungeecord.servers.keySet()) {
				if (a.contains("lobby")) {
					boolean b = Boolean.parseBoolean(SwitchbladeBungeecord.lobby.get(a).split(",")[1]);
					if (b) {
						if (SwitchbladeBungeecord.servers.get(a) < SwitchbladeBungeecord.servers.get(server)) {
							server = a;
						}
					}
				}
			}
			if (server.equalsIgnoreCase("lobby-01")) {
				if (!on) {
					for (String a : SwitchbladeBungeecord.servers.keySet()) {
						if (a.contains("lobby")) {
							boolean b = Boolean.parseBoolean(SwitchbladeBungeecord.lobby.get(a).split(",")[1]);
							if (b) {
								e.setTarget(BungeeCord.getInstance().getServerInfo(a));
								System.out.println(e.getPlayer().getName() + " foi conectado a '" + a
										+ "', devido ao servidor principal estar offline.");
								break;
							}
						}
					}
				} else {
					// e.getPlayer().setReconnectServer(BungeeCord.getInstance().getServerInfo(server));
					System.out.println(e.getPlayer().getName() + " enviado á " + server);
				}
			} else {
				e.setTarget(BungeeCord.getInstance().getServerInfo(server));
			}
			nicks.remove(e.getPlayer().getName());
		}
	}

	@EventHandler
	public void onPreLogin(PreLoginEvent e) {
		System.out.println("Connection of " + e.getConnection().getName());
		if (!nicks.contains(e.getConnection().getName()))
			nicks.add(e.getConnection().getName());
	}

}