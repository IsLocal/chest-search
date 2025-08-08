package local.chestsearch.mixin;

import local.chestsearch.SearchFieldAccessor;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ItemElement;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.slot.Slot;
import net.minecraft.core.util.helper.Color;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemElement.class, remap = false)
public class ItemElementMixin extends Gui {

	@Inject(method = "render(Lnet/minecraft/core/item/ItemStack;IIZLnet/minecraft/core/player/inventory/slot/Slot;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Lighting;disable()V", shift = At.Shift.BEFORE, by = 2))
	public void chestSearch$render(ItemStack itemStack, int x, int y, boolean isSelected, Slot slot, CallbackInfo ci) {
		if (SearchFieldAccessor.INSTANCE.getSearchFieldRendering() == null) {
			return;
		}
		String search = SearchFieldAccessor.INSTANCE.getSearchFieldRendering().getText();

		if (search == null || search.isEmpty()) {
			return;
		}
		if (itemStack == null || !itemStack.getDisplayName().toLowerCase().contains(search.toLowerCase())) {
			GL11.glDisable(2896);
			GL11.glDisable(2929);

			this.drawRect(x, y, x + 16, y + 16, Color.intToIntARGB(128, 0, 0,0));

			GL11.glEnable(2896);
			GL11.glEnable(2929);
		} else {
			GL11.glDisable(2896);
			GL11.glDisable(2929);

			this.drawBox(x, y, x + 16, y + 16, Color.intToIntARGB(150, 39, 245, 82), 1);

			GL11.glEnable(2896);
			GL11.glEnable(2929);
		}


	}
}
