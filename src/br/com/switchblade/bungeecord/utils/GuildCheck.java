package br.com.switchblade.bungeecord.utils;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import br.com.switchblade.bungeecord.SwitchbladeBungeecord;
import net.md_5.bungee.BungeeCord;

public class GuildCheck extends Thread {

	public void run() {
		SwitchbladeBungeecord.plugin.getProxy().getScheduler().schedule(SwitchbladeBungeecord.plugin, new Runnable() {

			@Override
			public void run() {
				for (String server : SwitchbladeBungeecord.plugin.getProxy().getServers().keySet()) {

					int on = SwitchbladeBungeecord.plugin.getProxy().getServerInfo(server).getPlayers().size();
					SwitchbladeBungeecord.servers.put(server, on);

					if (server.contains("lobby")) {
						if (SwitchbladeBungeecord.lobby.containsKey(server)) {
							int port = Integer.parseInt(SwitchbladeBungeecord.lobby.get(server).split(",")[0]);

							try {
								Socket s = new Socket("127.0.0.1", port);
								s.close();
								SwitchbladeBungeecord.lobby.put(server, port + ",true");
							} catch (IOException e) {
								SwitchbladeBungeecord.lobby.put(server, port + ",false");
							}

						}
					}
					SwitchbladeBungeecord.plugin.sendToBukkit("online", server + ";" + on,
							BungeeCord.getInstance().getServerInfo("lobby-01"));
					SwitchbladeBungeecord.plugin.sendToBukkit("online", server + ";" + on,
							BungeeCord.getInstance().getServerInfo("lobby-02"));
					SwitchbladeBungeecord.plugin.sendToBukkit("online", server + ";" + on,
							BungeeCord.getInstance().getServerInfo("KitPvP"));

					SwitchbladeBungeecord.plugin.sendToBukkit("all", BungeeCord.getInstance().getPlayers().size() + "",
							BungeeCord.getInstance().getServerInfo("lobby-01"));
					SwitchbladeBungeecord.plugin.sendToBukkit("all", BungeeCord.getInstance().getPlayers().size() + "",
							BungeeCord.getInstance().getServerInfo("lobby-02"));

				}
			}
		}, 0, 4, TimeUnit.SECONDS);
	}

}