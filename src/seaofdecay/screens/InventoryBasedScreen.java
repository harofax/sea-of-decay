package seaofdecay.screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.StringJoiner;

import seaofdecay.Creature;
import seaofdecay.Item;
import seaofdecay.util.asciipanel.AsciiPanel;


/**
 * A general screen for Inventory based screens, such as drink, drop, etc.
 */
public abstract class InventoryBasedScreen implements Screen {

	protected Creature player;
	private String letters;

	protected abstract String getVerb();
	protected abstract boolean isAcceptable(Item item);
	protected abstract Screen use(Item item);

	public InventoryBasedScreen(Creature player) {
		this.player = player;
		this.letters = "abcdefghijklmnopqrstuvwxyz";
	}

	public void displayOutput(AsciiPanel terminal) {
		ArrayList<String> lines = getList();

		int y = 18 - lines.size();
		int x = 4;

		if (lines.size() > 0)
			terminal.clear(' ', x, y, 20, lines.size());

		for (String line : lines) {
			terminal.write(line, x, y++);
		}

		terminal.clear(' ', 0, 18, 80, 1);
		terminal.write("What would you like to " + getVerb() + "?", 2, 18);

		terminal.repaint();
	}

	private ArrayList<String> getList() {
		ArrayList<String> lines = new ArrayList<>();
		Item[] inventory = player.getInventory().getItems();

		for (int i = 0; i < inventory.length; i++) {
			Item item = inventory[i];

			if (item == null || !isAcceptable(item))
				continue;

			String line = letters.charAt(i) + " - " + item.getGlyph() + " " + item.getName();

			lines.add(line);
		}

		return lines;
	}

	public Screen respondToUserInput(KeyEvent key) {
		char c = key.getKeyChar();

		Item[] items = player.getInventory().getItems();

		if (letters.indexOf(c) > -1
				&& items.length > letters.indexOf(c)
				&& items[letters.indexOf(c)] != null
				&& isAcceptable(items[letters.indexOf(c)]))
			return use(items[letters.indexOf(c)]);
		else if (key.getKeyCode() == KeyEvent.VK_ESCAPE)
			return null;
		else
			return this;
	}

}