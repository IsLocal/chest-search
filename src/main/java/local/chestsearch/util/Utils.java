package local.chestsearch.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.container.ScreenContainer;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.item.ItemStack;

import java.util.Comparator;

/**
 * @author rootEnginear
 * This class contains various utility methods.
 * <a href="https://github.com/rootEnginear/bta-rootenginear-mods/blob/sort-chest/src/main/java/rootenginear/sortchest/utils/Utils.java">...</a>
 */
@Environment(EnvType.CLIENT)
public class Utils {
	public static boolean isNotChest(Object object) {
		return !(object instanceof ScreenContainer || isAstoriaIronChest(object) || isAstoriaDiamondChest(object) || isExtendedReinforcedChest(object));
	}

	public static boolean isAstoriaIronChest(Object object) {
		return object.getClass().getSimpleName().equals("ScreenIronChest");
	}

	public static boolean isAstoriaDiamondChest(Object object) {
		return object.getClass().getSimpleName().equals("ScreenDiamondChest");
	}

	public static boolean isExtendedReinforcedChest(Object object) {
		return object.getClass().getSimpleName().equals("ScreenReinforcedChest");
	}

	public static Comparator<ItemStack> compareItemStacks() {
		return (a, z) -> {
			if (a == null && z == null) return 0;
			if (a == null) return 1;
			if (z == null) return -1;

			int aId = a.itemID;
			int zId = z.itemID;
			if (aId != zId) return aId - zId;

			int aMeta = a.getMetadata();
			int zMeta = z.getMetadata();
			if (aMeta != zMeta) return aMeta - zMeta;

			return z.stackSize - a.stackSize;
		};
	}

	public static void swap(Minecraft mc, int windowId, int x, int i) {
		mc.playerController.handleInventoryMouseClick(windowId, InventoryAction.CLICK_LEFT, new int[]{x, 0}, mc.thePlayer);
		mc.playerController.handleInventoryMouseClick(windowId, InventoryAction.CLICK_LEFT, new int[]{i, 0}, mc.thePlayer);
		mc.playerController.handleInventoryMouseClick(windowId, InventoryAction.CLICK_LEFT, new int[]{x, 0}, mc.thePlayer);
	}
}
