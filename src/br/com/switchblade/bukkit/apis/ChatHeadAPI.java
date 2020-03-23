package br.com.switchblade.bukkit.apis;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.UUID;

/**
 * Um c�digo simples para mandar a cabe�a de jogadores pelo chat.
 * 
 * @author Mateus F
 * @version 1.1
 */
public class ChatHeadAPI {

	private BufferedImage head;
	private HashMap<Integer, String[]> linesPrefixSuffix = new HashMap<>();
	private String mainPrefix;
	private String mainSuffix;
	private final Color[] COLORS = { new Color(0, 0, 0), new Color(0, 0, 170), new Color(0, 170, 0),
			new Color(0, 170, 170), new Color(170, 0, 0), new Color(170, 0, 170), new Color(255, 170, 0),
			new Color(170, 170, 170), new Color(85, 85, 85), new Color(85, 85, 255), new Color(85, 255, 85),
			new Color(85, 255, 255), new Color(255, 85, 85), new Color(255, 85, 255), new Color(255, 255, 85),
			new Color(255, 255, 255), };

	/**
	 * Cria uma inst�ncia de ChatHead usando um UUID em string.
	 * 
	 * @param uuidString
	 *            Coloca a UUID em string.
	 * @param size
	 *            O tamanho que voc� quer no chat.
	 */
	public ChatHeadAPI(String uuidString, int size) {
		this(UUID.fromString(uuidString), size);
	}

	/**
	 * Cria uma inst�ncia de ChatHead usando um UUID em string.
	 * 
	 * @param uuidString
	 *            Coloca a UUID em string.
	 */
	public ChatHeadAPI(String uuidString) {
		this(UUID.fromString(uuidString), 8);
	}

	/**
	 * Cria uma inst�ncia de ChatHead
	 * 
	 * @param uuid
	 *            Coloca o UUID que voc� quer pegar a cabe�a.
	 */
	public ChatHeadAPI(UUID uuid) {
		this(uuid, 8);
	}

	/**
	 * Cria uma inst�ncia de ChatHead
	 * 
	 * @param player
	 *            Coloca o jogador que voc� quer pegar a cabe�a.
	 */
	public ChatHeadAPI(Player player) {
		this(player.getUniqueId(), 8);
	}

	/**
	 * Cria uma inst�ncia de ChatHead
	 * 
	 * @param player
	 *            Coloca o jogador que voc� quer pegar a cabe�a.
	 * @param size
	 *            O tamanho que voc� quer no chat.
	 */
	public ChatHeadAPI(Player player, int size) {
		this(player.getUniqueId(), size);
	}

	/**
	 * Cria uma inst�ncia de ChatHead
	 * 
	 * @param uuid
	 *            Coloca o UUID que voc� quer pegar a cabe�a.
	 * @param size
	 *            O tamanho que voc� quer no chat.
	 */
	public ChatHeadAPI(UUID uuid, int size) {
		try {
			URLConnection urlConnection = new URL(
					"https://minotar.net/avatar/" + uuid.toString().replaceAll("-", "") + "/" + size).openConnection();
			head = ImageIO.read(urlConnection.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Coloca o sufixo da cabe�a e em que linha ele deve ficar.
	 * 
	 * @param line
	 *            A linha que o prefixo e sufixo devem ficar.
	 * @param prefix
	 *            O prefixo.
	 * @return A inst�ncia.
	 */
	public ChatHeadAPI setPrefix(int line, String prefix) {
		setPrefixSuffix(line, prefix, "");
		return this;
	}

	/**
	 * Coloca o sufixo da cabe�a e em que linha ele deve ficar.
	 * 
	 * @param line
	 *            A linha que o prefixo e sufixo devem ficar.
	 * @param suffix
	 *            O sufixo.
	 * @return A inst�ncia.
	 */
	public ChatHeadAPI setSuffix(int line, String suffix) {
		setPrefixSuffix(line, "", suffix);
		return this;
	}

	/**
	 * Coloca o sufixo e prefixo da cabe�a e em que linha ele deve ficar.
	 * 
	 * @param line
	 *            A linha que o prefixo e sufixo devem ficar.
	 * @param prefix
	 *            O prefixo.
	 * @param suffix
	 *            O sufixo.
	 * @return A inst�ncia.
	 */
	public ChatHeadAPI setPrefixSuffix(int line, String prefix, String suffix) {
		if (line < 0 || line > head.getHeight()) {
			throw new InvalidParameterException("O par�metro de linha ultrapassou o n�mero de linhas suportadas");
		}
		linesPrefixSuffix.put(line, new String[] { prefix, suffix });
		return this;
	}

	/**
	 * Coloca o prefixo principal, que vai ser usado em toda linha.
	 * 
	 * @param mainPrefix
	 *            O prefixo.
	 * @return A inst�ncia.
	 */
	public ChatHeadAPI setMainPrefix(String mainPrefix) {
		this.mainPrefix = mainPrefix;
		return this;
	}

	/**
	 * Coloca o sufixo principal, que vai ser usado em toda linha.
	 * 
	 * @param mainSuffix
	 *            O sufixo.
	 * @return A inst�ncia.
	 */
	public ChatHeadAPI setMainSuffix(String mainSuffix) {
		this.mainSuffix = mainSuffix;
		return this;
	}

	/**
	 * Pega a mensagem da inst�ncia.
	 * 
	 * @return a mensagem
	 */
	public String getMessage() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int y = 0; y < head.getHeight(); y++) {
			if (mainPrefix != null)
				stringBuilder.append(mainPrefix);
			if (linesPrefixSuffix.containsKey(y)) {
				stringBuilder.append(linesPrefixSuffix.get(y)[0]);
			}
			for (int x = 0; x < head.getWidth(); x++) {
				ChatColor chatColor = getClosestColor(new Color(head.getRGB(x, y)));
				stringBuilder.append(chatColor).append('\u2588');
			}
			if (mainSuffix != null)
				stringBuilder.append(mainSuffix);
			if (linesPrefixSuffix.containsKey(y)) {
				stringBuilder.append(linesPrefixSuffix.get(y)[1]);
			}
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}

	private ChatColor getClosestColor(Color color) {
		double bestMatch = -1;
		int idx = 0;
		for (int i = 0; i < COLORS.length; i++) {
			if (isEqual(COLORS[i], color)) {
				return ChatColor.values()[i];
			}
			double distance = getDistance(color, COLORS[i]);
			if (distance < bestMatch || bestMatch == -1) {
				bestMatch = distance;
				idx = i;
			}
		}
		return ChatColor.values()[idx];
	}

	private double getDistance(Color c1, Color c2) {
		// Isso usa uma f�rmula para saber qual cor est� mais pr�xima da outra, �
		// poss�vel achar ela s� pesquisando no google.
		double r = (c1.getRed() + c2.getRed()) / 2.0;
		double red = c1.getRed() - c2.getRed();
		double green = c1.getGreen() - c2.getGreen();
		double blue = c1.getBlue() - c2.getBlue();
		double w1 = 2 + r / 256.0;
		double w2 = 2 + (255 - r) / 256.0;
		return w1 * red * red + 4 * green * green + w2 * blue * blue;
	}

	private boolean isEqual(Color c1, Color c2) {
		// Serve apenas para checar se a cor � exatamente igual a outra.
		return Math.abs(c1.getRed() - c2.getRed()) <= 5 && Math.abs(c1.getGreen() - c2.getGreen()) <= 5
				&& Math.abs(c1.getBlue() - c2.getBlue()) <= 5;
	}
}