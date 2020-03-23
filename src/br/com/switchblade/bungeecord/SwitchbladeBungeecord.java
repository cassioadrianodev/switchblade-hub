package br.com.switchblade.bungeecord;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

import br.com.switchblade.bungeecord.events.GuildConnection;
import br.com.switchblade.bungeecord.utils.GuildCheck;
import br.com.switchblade.bungeecord.utils.Hub;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;

public class SwitchbladeBungeecord extends Plugin {

	public static SwitchbladeBungeecord plugin;
	public static HashMap<String, Integer> servers = new HashMap<>(); // name -> players
	public static HashMap<String, String> lobby = new HashMap<>();

	public void onEnable() {
		plugin = this;
		BungeeCord.getInstance().registerChannel("Lobby");
		BungeeCord.getInstance().getPluginManager().registerListener(this, new GuildConnection());
		GuildCheck sv = new GuildCheck();
		sv.start();
		registerAllServers();
		BungeeCord.getInstance().getPluginManager().registerCommand(this, new Hub());
	}

	public void registerAllServers() {
		lobby.put("lobby-01", "14464,true");
		lobby.put("lobby-02", "14465,true");
	}

	public void sendToBukkit(String channel, String message, ServerInfo server) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(stream);
		try {
			out.writeUTF(channel);
			out.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.sendData("Lobby", stream.toByteArray());
	}
}