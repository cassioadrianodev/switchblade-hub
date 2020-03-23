package br.com.switchblade.bukkit.managers;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import br.com.switchblade.bukkit.Switchblade;
import br.com.switchblade.bukkit.database.MySQL;
import br.com.switchblade.bukkit.scoreboard.ScoreManager;
import br.com.switchblade.bukkit.structures.ClassGetter;
import br.com.switchblade.bukkit.structures.TimeSecond;
import br.com.switchblade.bukkit.structures.command.CommandStructure;
import br.com.switchblade.bukkit.utils.MessageUtils;

@SuppressWarnings("all")
public class Manager {

	private Switchblade switchblade;
	private Loaders loaders;
	private MySQL mysql;

	public Manager(Switchblade switchblade) {
		this.switchblade = switchblade;
		this.switchblade.saveDefaultConfig();
		this.mysql = new MySQL();
		this.loaders = new Loaders();
	}

	public Plugin getPlugin() {
		return switchblade;
	}

	public MySQL getMySQL() {
		return mysql;
	}

	public void enable() {
		mysql.getConnection();
		mysql.createTables(new String[] {
				"CREATE TABLE IF NOT EXISTS `global_accounts` (`uuid` VARCHAR(64), `nick` VARCHAR(16), `kills` INT(100), `death` INT(100), `killstreak` INT(100), `coins` VARCHAR(100));" });

		/* TimeSecond register */

		Bukkit.getScheduler().scheduleSyncRepeatingTask(Switchblade.getPlugin(Switchblade.class), new Runnable() {
			public void run() {
				Bukkit.getPluginManager().callEvent(new TimeSecond());
			}
		}, 20L, 0L);

		ScoreManager scoreManager = new ScoreManager();
		scoreManager.simpleUpdater(); // Updater Scoreboard
		this.loaders.loadCommands();
		this.loaders.loadListeners();
	}

	public void disable() {
		mysql.close();
		HandlerList.unregisterAll();

		for (Player players : Bukkit.getOnlinePlayers()) {
			players.kickPlayer(MessageUtils.PREFIX + "\n§cServidor fechado/ou reiniciado.");
		}
		for (World world : Bukkit.getWorlds()) {
			for (Entity entity : world.getEntities()) {
				entity.remove();
			}
		}
	}

	class Loaders {

		private void loadCommands() {
			for (Class<?> classes : ClassGetter.getClassesForPackage(Switchblade.getPlugin(Switchblade.class),
					"br.com.switchblade.bukkit")) {
				if (CommandStructure.class.isAssignableFrom(classes) && classes != CommandStructure.class) {
					try {
						CommandStructure classCmd = (CommandStructure) classes.newInstance();
						((CraftServer) Bukkit.getServer()).getCommandMap().register(classCmd.getName().toLowerCase(),
								classCmd);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		private void loadListeners() {
			for (Class<?> classes : ClassGetter.getClassesForPackage(Switchblade.getPlugin(Switchblade.class),
					"br.com.switchblade.bukkit")) {
				if (Listener.class.isAssignableFrom(classes)) {
					try {
						Listener classEvents = (Listener) classes.newInstance();
						Bukkit.getPluginManager().registerEvents(classEvents, Switchblade.getPlugin(Switchblade.class));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
