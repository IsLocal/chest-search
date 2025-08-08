package local.chestsearch;

import local.chestsearch.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.TextFieldElement;
import net.minecraft.client.gui.options.OptionsButtonElement;
import net.minecraft.core.util.helper.Listener;

public class SearchFieldAccessor implements Listener<ButtonElement> {
	public static SearchFieldAccessor INSTANCE = new SearchFieldAccessor();
	private static boolean initialized = false;

	private TextFieldElement searchField;
	private ButtonElement clearButton;



	public void init() {
		if (initialized) return;
		initialized = true;

		searchField = new TextFieldElement(null, Minecraft.getMinecraft().font, 99, 5, 68, 10, "", "Search");

		clearButton = new OptionsButtonElement(0, 45, 5, 13, 13, "").setTextures("minecraft:gui/misc/button_reset", "minecraft:gui/misc/button_reset_highlighted", null);
		clearButton.setListener(this);
	}
	public TextFieldElement getSearchFieldRendering() {
		if (!Utils.isNotChest(Minecraft.getMinecraft().currentScreen)) {
			return searchField;
		}

		return null;
	}

	public ButtonElement getClearButtonRendering() {
		if (!Utils.isNotChest(Minecraft.getMinecraft().currentScreen)) {
			return clearButton;
		}
		return null;
	}

	@Override
	public void listen(ButtonElement object) {
		searchField.setText("");
	}
}
