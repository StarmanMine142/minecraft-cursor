package io.github.fishstiz.cursors_extended.mixin.cursorprovider;

import com.mojang.blaze3d.platform.cursor.CursorType;
import com.mojang.blaze3d.platform.cursor.CursorTypes;
import io.github.fishstiz.cursors_extended.CursorsExtended;
import io.github.fishstiz.cursors_extended.cursor.CursorProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(WorldSelectionList.class)
public abstract class WorldSelectionListMixin extends ObjectSelectionList<WorldSelectionList.Entry> implements CursorProvider {
    @Unique
    private static final int cursors_extended$ICON_WIDTH = 32;

    protected WorldSelectionListMixin(Minecraft minecraft, int width, int height, int y, int itemHeight) {
        super(minecraft, width, height, y, itemHeight);
    }

    @Override
    public CursorType cursors_extended$cursorType(double mouseX, double mouseY) {
        if (CursorsExtended.CONFIG.isWorldIconEnabled()
            && mouseX < this.getRowLeft() + cursors_extended$ICON_WIDTH
            && this.getEntryAtPosition(mouseX, mouseY) instanceof WorldSelectionList.WorldListEntry worldEntry
            && worldEntry.canInteract()
        ) {
            return CursorTypes.POINTING_HAND;
        }
        return CursorType.DEFAULT;
    }
}
