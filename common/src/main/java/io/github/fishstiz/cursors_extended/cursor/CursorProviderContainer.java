package io.github.fishstiz.cursors_extended.cursor;

import com.mojang.blaze3d.platform.cursor.CursorType;
import io.github.fishstiz.cursors_extended.util.CursorTypeUtil;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;

public interface CursorProviderContainer extends CursorProvider, ContainerEventHandler {
    @Override
    default CursorType cursors_extended$cursorType(double mouseX, double mouseY) {
        CursorType cursorType = cursors_extended$delegateCursorType(this, mouseX, mouseY);
        if (CursorTypeUtil.nonDefault(cursorType)) return cursorType;
        return CursorProvider.super.cursors_extended$cursorType(mouseX, mouseY);
    }

    static <T extends CursorProvider & ContainerEventHandler> CursorType cursors_extended$delegateCursorType(
            T provider,
            double mouseX,
            double mouseY
    ) {
        for (GuiEventListener child : provider.children()) {
            if (child instanceof CursorProvider cursorProvider && CursorTypeUtil.isHovered(child, mouseX, mouseY)) {
                CursorType cursorType = cursorProvider.cursors_extended$cursorType(mouseX, mouseY);
                if (CursorTypeUtil.nonDefault(cursorType)) {
                    return cursorType;
                }
                break;
            }
        }

        return CursorType.DEFAULT;
    }
}
