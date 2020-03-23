package br.com.switchblade.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import br.com.switchblade.bukkit.managers.Manager;

public class Switchblade extends JavaPlugin{

	private static Manager manager;
	
	public void onEnable() {
		Manager manager = new Manager(this);
		manager.enable();
	}
	
	public void onDisable() { manager.disable(); }
	public static Manager getManager() { return manager; }
}
