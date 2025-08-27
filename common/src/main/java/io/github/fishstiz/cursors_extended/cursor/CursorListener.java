package io.github.fishstiz.cursors_extended.cursor;

import com.mojang.blaze3d.platform.cursor.CursorType;
import com.mojang.blaze3d.platform.cursor.CursorTypes;
import io.github.fishstiz.cursors_extended.mixin.WindowAccess;
import io.github.fishstiz.cursors_extended.util.CursorTypeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

public class CursorListener {
    public static final CursorListener INSTANCE = new CursorListener();
    private CursorType deferredCursorType = null;

    private CursorListener() {
    }

    public void afterTick(Minecraft minecraft) {
        if (minecraft.screen == null && this.deferredCursorType == null) {
            CursorManager.INSTANCE.setCurrentCursor(consumeTickCursors(minecraft));
        } else if (this.deferredCursorType == null && nonScreenCursorVisible(minecraft)) {
            CursorManager.INSTANCE.setCurrentCursor(this.deferredCursorType);
        }
    }

    public void afterScreenRender(Minecraft minecraft, Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (nonScreenCursorVisible(minecraft)) {
            CursorProviderInspector.INSTANCE.getInspector().render(minecraft, screen, guiGraphics, mouseX, mouseY);
            this.deferredCursorType = resolve(minecraft, screen, mouseX, mouseY);
        }
    }

    public void afterRenderTooltip(Minecraft minecraft, Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
        CursorProviderInspector.INSTANCE.getInspector().render(minecraft, screen, guiGraphics, mouseX, mouseY);
    }

    public void afterGameRender(Minecraft minecraft, Screen screen, int mouseX, int mouseY) {
        CursorManager.INSTANCE.setCurrentCursor(resolve(minecraft, screen, mouseX, mouseY));
        this.deferredCursorType = null;
    }

    private static CursorType resolve(Minecraft minecraft, Screen screen, int mouseX, int mouseY) {
        CursorType tickCursor = CursorTickController.INSTANCE.consumeTickCursor();
        CursorType fallbackTickCursor = CursorTickController.INSTANCE.consumeFallbackTickCursor();
        if (!((WindowAccess) (Object) minecraft.getWindow()).cursors_extended$isAdaptive()) {
            return CursorTypes.ARROW;
        }
        if (CursorTypeUtil.nonDefault(tickCursor)) {
            return tickCursor;
        }
        if (CursorTypeUtil.isGrabbing()) {
            return CursorTypes.RESIZE_ALL;
        }
        CursorType inspected = CursorProviderInspector.INSTANCE.inspect(screen, mouseX, mouseY);
        if (CursorTypeUtil.nonDefault(inspected)) {
            return inspected;
        }
        if (CursorTypeUtil.nonDefault(fallbackTickCursor)) {
            return fallbackTickCursor;
        }
        return CursorType.DEFAULT;
    }

    private static CursorType consumeTickCursors(Minecraft minecraft) {
        if (!((WindowAccess) (Object) minecraft.getWindow()).cursors_extended$isAdaptive()) {
            return CursorTypes.ARROW;
        }

        return CursorTypeUtil.firstNonDefault(
                CursorTickController.INSTANCE.consumeTickCursor(),
                CursorTickController.INSTANCE.consumeFallbackTickCursor()
        );
    }

    private static boolean nonScreenCursorVisible(Minecraft minecraft) {
        return minecraft.screen == null && !minecraft.mouseHandler.isMouseGrabbed();
    }
}
