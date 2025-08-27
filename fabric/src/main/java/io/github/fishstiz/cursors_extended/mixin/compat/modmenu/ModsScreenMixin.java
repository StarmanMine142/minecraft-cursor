package io.github.fishstiz.cursors_extended.mixin.compat.modmenu;

import com.mojang.blaze3d.platform.cursor.CursorType;
import com.mojang.blaze3d.platform.cursor.CursorTypes;
import com.terraformersmc.modmenu.config.ModMenuConfig;
import com.terraformersmc.modmenu.gui.ModsScreen;
import com.terraformersmc.modmenu.gui.widget.ModListWidget;
import com.terraformersmc.modmenu.gui.widget.entries.ModListEntry;
import io.github.fishstiz.cursors_extended.cursor.CursorProvider;
import io.github.fishstiz.cursors_extended.util.CursorTypeUtil;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;

@Pseudo
@Mixin(value = ModsScreen.class, remap = false)
public abstract class ModsScreenMixin extends Screen implements CursorProvider {
    @Unique
    private static final int COMPACT_ICON_SIZE = 19;
    @Unique
    private static final int ICON_SIZE = 32;

    @Shadow
    public abstract boolean getModHasConfigScreen(String modId);

    protected ModsScreenMixin(Component title) {
        super(title);
    }

    @Override
    public CursorType cursors_extended$cursorType(double mouseX, double mouseY) {
        CursorType cursorType = CursorProvider.super.cursors_extended$cursorType(mouseX, mouseY);
        if (CursorTypeUtil.nonDefault(cursorType)) {
            return cursorType;
        }

        Optional<GuiEventListener> hoveredElementOpt = this.getChildAt(mouseX, mouseY);
        if (hoveredElementOpt.isEmpty()) {
            return CursorType.DEFAULT;
        }
        GuiEventListener hoveredElement = hoveredElementOpt.get();
        if (!(hoveredElement instanceof ModListWidget modListWidget)) {
            return CursorType.DEFAULT;
        }
        if (ModMenuConfig.QUICK_CONFIGURE.getValue()) {
            int iconSize = ModMenuConfig.COMPACT_LIST.getValue() ? COMPACT_ICON_SIZE : ICON_SIZE;
            for (ModListEntry entry : modListWidget.children()) {
                if (entry.isMouseOver(mouseX, mouseY)
                    && this.getModHasConfigScreen(entry.mod.getId())
                    && mouseX >= modListWidget.getX()
                    && mouseX - modListWidget.getRowLeft() <= iconSize)
                    return CursorTypes.POINTING_HAND;
            }
        }

        return CursorType.DEFAULT;
    }
}
