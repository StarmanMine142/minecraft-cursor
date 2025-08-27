package io.github.fishstiz.cursors_extended.mixin;

import com.mojang.blaze3d.platform.Window;
import io.github.fishstiz.cursors_extended.cursor.CursorManager;
import io.github.fishstiz.cursors_extended.util.SettingsUtil;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow
    @Final
    private Window window;

    @Unique
    private int cursors_extended$previousGuiScale;

    @Inject(method = "resizeDisplay", at = @At("TAIL"))
    public void reloadCursorsOnResize(CallbackInfo ci) {
        int guiScale = this.window.getGuiScale();

        if (this.cursors_extended$previousGuiScale != guiScale) {
            this.cursors_extended$previousGuiScale = guiScale;
            CursorManager.INSTANCE.getCursors().forEach(cursor -> {
                if (cursor.isEnabled() && SettingsUtil.isAutoScale(cursor.getScale())) {
                    cursor.reload();
                }
            });
        }
    }
}
