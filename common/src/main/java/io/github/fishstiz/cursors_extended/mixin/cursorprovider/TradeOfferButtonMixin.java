package io.github.fishstiz.cursors_extended.mixin.cursorprovider;

import com.mojang.blaze3d.platform.cursor.CursorType;
import com.mojang.blaze3d.platform.cursor.CursorTypes;
import io.github.fishstiz.cursors_extended.cursor.CursorProvider;
import io.github.fishstiz.cursors_extended.cursor.CursorTypesExt;
import io.github.fishstiz.cursors_extended.util.CursorTypeUtil;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "net.minecraft.client.gui.screens.inventory.MerchantScreen.TradeOfferButton")
public abstract class TradeOfferButtonMixin implements CursorProvider {
    @Override
    public CursorType cursors_extended$cursorType(double mouseX, double mouseY) {
        return CursorTypeUtil.canShift() ? CursorTypesExt.SHIFT : CursorTypes.POINTING_HAND;
    }
}
