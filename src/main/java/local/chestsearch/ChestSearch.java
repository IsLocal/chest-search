package local.chestsearch;

import local.chestsearch.util.OptionStore;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Option;
import net.minecraft.client.option.OptionBoolean;
import net.minecraft.core.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;


public class ChestSearch implements ModInitializer {
    public static final String MOD_ID = "chestsearch";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ChestSearch INSTANCE;
    @Override
    public void onInitialize() {
		optionFilePath = FabricLoader.getInstance().getConfigDir().resolve("chestsearch.properties");

		LOGGER.info("Chest Search initialized.");
		INSTANCE = this;
    }

	public static OptionBoolean clearButton;
	public static OptionBoolean preserveSearch;
	public static OptionBoolean addOptionPage;
	public Option<?>[] options;
	public Path optionFilePath;


	public void start(Minecraft mc	) {

		preserveSearch = new OptionBoolean(mc.gameSettings, "preserve_search", true);
		clearButton = new OptionBoolean(mc.gameSettings, "clear_button", true);
		addOptionPage = new OptionBoolean(mc.gameSettings, "add_option_page", true);
		options = new Option[] {preserveSearch, clearButton, addOptionPage};

		OptionStore.loadOptions(optionFilePath, options, new KeyBinding[]{});

		if (addOptionPage.value) {
			OptionsPages.register(new OptionsPage("gui.options.page.chestsearch.title", Blocks.CHEST_PLANKS_OAK.getDefaultStack()))
				.withComponent(new BooleanOptionComponent(preserveSearch))
				.withComponent(new BooleanOptionComponent(clearButton));

		}

		SearchFieldAccessor.INSTANCE.init();

		LOGGER.info("Chest search config loaded.");

	}

	public void stop() {
		try {
			OptionStore.saveOptions(optionFilePath, options, new KeyBinding[]{});
		} catch (Exception e) {
			LOGGER.error("Failed to save chest search config.", e);
		}
	}


}
