package io.github.fishstiz.cursors_extended.mixin.cursorprovider;

import com.mojang.blaze3d.platform.cursor.CursorType;
import io.github.fishstiz.cursors_extended.cursor.CursorProvider;
import io.github.fishstiz.cursors_extended.cursor.CursorTypesExt;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LevelLoadingScreen.class)
public abstract class LevelLoadingScreenMixin implements CursorProvider {
    @Override
    public CursorType cursors_extended$cursorType(double mouseX, double mouseY) {
        return CursorTypesExt.BUSY;
    }
}
