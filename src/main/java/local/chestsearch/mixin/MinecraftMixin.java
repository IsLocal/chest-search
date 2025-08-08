package local.chestsearch.mixin;

import local.chestsearch.ChestSearch;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, remap = false)
public class MinecraftMixin {

	@Inject(method = "startGame", at = @At("TAIL"))
	private void chestsearch$startGame(CallbackInfo ci) {
		ChestSearch.INSTANCE.start((Minecraft) (Object) this);
	}

	@Inject(method = "shutdownMinecraftApplet", at = @At("HEAD"))
	private void chestsearch$shutdownMinecraftApplet(CallbackInfo ci) {
		ChestSearch.INSTANCE.stop();
	}
}
