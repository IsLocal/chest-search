package local.chestsearch.mixin;

import local.chestsearch.ChestSearch;
import local.chestsearch.SearchFieldAccessor;
import local.chestsearch.util.Utils;
import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.TextFieldElement;
import net.minecraft.client.gui.container.ScreenContainerAbstract;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ScreenContainerAbstract.class, remap = false)
public abstract class ScreenContainerMixin extends Screen {

	@Shadow
	public int xSize;
	@Shadow
	public int ySize;
	@Unique
	private TextFieldElement searchField;


	@Inject(method = "init", at=@At("TAIL"))
	public void chestsearch$init(CallbackInfo ci) {
		if (Utils.isNotChest(this)) return;

		this.searchField = SearchFieldAccessor.INSTANCE.getSearchFieldRendering();

		assert this.searchField != null;
		int centerX = (this.width - this.xSize) / 2;
		int centerY = (this.height - this.ySize) / 2;
		centerY -= 15;

		boolean isAstoriaDiamondChest = Utils.isAstoriaDiamondChest(this);
		boolean isExtendedReinforcedChest = Utils.isExtendedReinforcedChest(this);

		if (isAstoriaDiamondChest || isExtendedReinforcedChest) {
			centerX -= 15;
			searchField.width = 80;
		} else {
			searchField.width = 68;
		}

		searchField.xPosition = centerX + this.xSize - 74;
		searchField.yPosition = centerY + 5;

		searchField.drawBackground = true;
		searchField.setFocused(false);
		searchField.parent = this;
		searchField.isEnabled = true;

		if (!ChestSearch.preserveSearch.value) {
			searchField.setText("");
		}

		if (ChestSearch.clearButton.value) {
			ButtonElement clearButton = SearchFieldAccessor.INSTANCE.getClearButtonRendering();
			assert clearButton != null;

			clearButton.xPosition = centerX + this.xSize - 88;
			clearButton.yPosition = centerY + 3;
			clearButton.visible = true;
			this.buttons.add(clearButton);
		}
	}


	@Inject(method = "tick", at=@At("TAIL"))
	public void chestsearch$tick(CallbackInfo ci) {
		if (Utils.isNotChest(this)) return;

		if (ChestSearch.clearButton.value && !this.buttons.contains(SearchFieldAccessor.INSTANCE.getClearButtonRendering())) {
			this.buttons.add(SearchFieldAccessor.INSTANCE.getClearButtonRendering());
		}

		searchField.updateCursorCounter();
	}


	@Inject(method = "mouseClicked", at=@At("HEAD"))
	public void chestsearch$mouseClicked(int mx, int my, int buttonNum, CallbackInfo ci) {
		if (Utils.isNotChest(this)) return;

		searchField.mouseClicked(mx, my, buttonNum);
	}

	@Inject(method = "keyPressed", at=@At("HEAD"), cancellable = true)
	public void keyPressed(char eventCharacter, int eventKey, int mx, int my, CallbackInfo ci) {
		if (Utils.isNotChest(this)) return;
		searchField.textboxKeyTyped(eventCharacter, eventKey);

		if (searchField.isFocused) {
			if (eventKey == Keyboard.KEY_ESCAPE) {
				this.mc.thePlayer.closeScreen();
			}
			ci.cancel();
		}
	}


	@Inject(method = "render", at=@At("TAIL"))
	public void chestSearch$drawGuiContainerForegroundLayer(CallbackInfo ci) {
		if (Utils.isNotChest(this)) return;
		searchField.drawTextBox();

	}


}
