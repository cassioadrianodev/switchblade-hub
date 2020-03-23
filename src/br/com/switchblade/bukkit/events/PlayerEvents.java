package br.com.switchblade.bukkit.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import br.com.switchblade.bukkit.apis.ChatHeadAPI;
import br.com.switchblade.bukkit.scoreboard.ScoreManager;
import br.com.switchblade.bukkit.utils.MessageUtils;

public class PlayerEvents implements Listener{
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.setJoinMessage(null);
		
		Player player = event.getPlayer();
		
		for (int i = 0; i < 100; i++) {
			player.sendMessage("");
		}
		
		ChatHeadAPI chatHeadAPI = new ChatHeadAPI(player)
		 .setSuffix(5, "§7Bem-vindo ao servidor §a" + player.getDisplayName())
		 .setMainPrefix("§8>");
		
		player.sendMessage(chatHeadAPI.getMessage());
		
		ScoreManager scoreManager = new ScoreManager(player, "lobby");
		
		scoreManager.setTitle(MessageUtils.PREFIX);
		scoreManager.setLine("§1", "", 3);
		scoreManager.setLine("§aBem vindo ao servidor!", "", 2);
		scoreManager.setLine("§1", "", 1);
		scoreManager.setLine("§eswitchblade.com.br", "", 0);
		
		scoreManager.setScoreboard();
	}
}
