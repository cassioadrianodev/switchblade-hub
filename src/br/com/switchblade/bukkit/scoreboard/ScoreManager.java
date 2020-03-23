package br.com.switchblade.bukkit.scoreboard;

import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import br.com.switchblade.bukkit.Switchblade;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Map;
import java.util.UUID;

@SuppressWarnings("all")
public class ScoreManager {

	private Scoreboard scoreboard;
	private Player player;
	private Objective objective;

	public ScoreManager(Player player, String objective) {
		this.player = player;
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		this.objective = scoreboard.registerNewObjective("score_" + objective, "dummy");
	}

	public ScoreManager() {
	}

	public void simpleUpdater() {
		new BukkitRunnable() {

			@Override
			public void run() {
				for (Player x : Bukkit.getOnlinePlayers()) {
					x.setScoreboard(scoreboard);
				}

			}
		}.runTaskTimer(Switchblade.getPlugin(Switchblade.class), 20L, 60L);
	}

	public Scoreboard getScoreboard() {
		return scoreboard;
	}

	public Player getPlayer() {
		return player;
	}

	public Objective getObjective() {
		return objective;
	}

	public void setTitle(String title) {
		objective.setDisplayName(title);
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	public void setLine(String prefix, String suffix, int number) {
		FastOfflinePlayer fast = new FastOfflinePlayer(prefix);
		Team team = scoreboard.registerNewTeam("team-" + number);

		team.setSuffix(suffix);
		team.addPlayer(fast);

		objective.getScore(fast).setScore(number);
	}

	public void updateLine(Player player, int number, String suffix) {
		if (player.getScoreboard() != null) {
			Team team = player.getScoreboard().getTeam("team-" + number);

			team.setSuffix(suffix);
		}
	}

	public void setScoreboard() {
		player.setScoreboard(scoreboard);
	}

	public void resetScoreboard(Player player) {
		ScoreManager score = new ScoreManager(player, "blank");

		score.setTitle(null);
		score.setScoreboard();
	}

	private class FastOfflinePlayer implements OfflinePlayer {

		private final String playerName;

		private FastOfflinePlayer(String playerName) {
			this.playerName = playerName;
		}

		@Override
		public boolean isOnline() {
			return false;
		}

		@Override
		public String getName() {
			return playerName;
		}

		@Override
		public UUID getUniqueId() {
			return UUID.nameUUIDFromBytes(playerName.getBytes(Charsets.UTF_8));
		}

		@Override
		public boolean isBanned() {
			return false;
		}

		@Override
		public void setBanned(boolean banned) {
		}

		@Override
		public boolean isWhitelisted() {
			return false;
		}

		@Override
		public void setWhitelisted(boolean value) {
		}

		@Override
		public Player getPlayer() {
			return null;
		}

		@Override
		public long getFirstPlayed() {
			return System.currentTimeMillis();
		}

		@Override
		public long getLastPlayed() {
			return System.currentTimeMillis();
		}

		@Override
		public boolean hasPlayedBefore() {
			return false;
		}

		@Override
		public Location getBedSpawnLocation() {
			return new Location(Bukkit.getWorlds().get(0), 0, 0, 0);
		}

		@Override
		public boolean isOp() {
			return false;
		}

		@Override
		public void setOp(boolean value) {
		}

		@Override
		public Map<String, Object> serialize() {
			Map<String, Object> result = Maps.newLinkedHashMap();

			result.put("UUID", getUniqueId().toString());
			result.put("name", playerName);

			return result;
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(playerName);
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof FastOfflinePlayer)) {
				return false;
			}

			FastOfflinePlayer other = (FastOfflinePlayer) obj;
			return Objects.equal(playerName, other.playerName);
		}

		@Override
		public String toString() {
			return Objects.toStringHelper(this).add("playerName", playerName).toString();
		}

	}

}