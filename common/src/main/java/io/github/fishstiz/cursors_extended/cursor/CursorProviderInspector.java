package io.github.fishstiz.cursors_extended.cursor;

import com.mojang.blaze3d.platform.cursor.CursorType;
import net.minecraft.client.gui.components.events.GuiEventListener;
import org.jetbrains.annotations.Nullable;

public class CursorProviderInspector {
    public static final CursorProviderInspector INSTANCE = new CursorProviderInspector();
    private ElementInspector inspector = ElementInspector.NO_OP;

    private CursorProviderInspector() {
    }

    public CursorType inspect(@Nullable GuiEventListener element, double mouseX, double mouseY) {
        if (element instanceof CursorProvider cursorProvider) {
            CursorType providedCursorType = cursorProvider.cursors_extended$cursorType(mouseX, mouseY);
            inspector.setInspected(element, false);
            return providedCursorType;
        }
        return CursorType.DEFAULT;
    }

    public ElementInspector getInspector() {
        return inspector;
    }

    public void toggleInspector() {
        inspector.destroy();
        inspector = this.inspector == ElementInspector.NO_OP ? new ElementInspectorImpl() : ElementInspector.NO_OP;
    }
}
