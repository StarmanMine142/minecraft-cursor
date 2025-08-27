package io.github.fishstiz.cursors_extended.mixin.cursorprovider;

import com.mojang.blaze3d.platform.cursor.CursorType;
import io.github.fishstiz.cursors_extended.cursor.CursorProvider;
import io.github.fishstiz.cursors_extended.cursor.CursorProviderContainer;
import io.github.fishstiz.cursors_extended.util.CursorTypeUtil;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ContainerEventHandler.class)
public interface ContainerEventHandlerMixin<T extends ContainerEventHandler & CursorProvider> extends CursorProvider {
    @Override
    default CursorType cursors_extended$cursorType(double mouseX, double mouseY) {
        @SuppressWarnings("unchecked")
        CursorType cursorType = CursorProviderContainer.cursors_extended$delegateCursorType((T) this, mouseX, mouseY);
        if (CursorTypeUtil.nonDefault(cursorType)) return cursorType;
        return CursorProvider.super.cursors_extended$cursorType(mouseX, mouseY);
    }
}
