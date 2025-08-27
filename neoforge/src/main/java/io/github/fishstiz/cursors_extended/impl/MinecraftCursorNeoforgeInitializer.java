package io.github.fishstiz.cursors_extended.impl;

import io.github.fishstiz.cursors_extended.api.CursorTypeRegistrar;
import io.github.fishstiz.cursors_extended.api.ElementRegistrar;
import io.github.fishstiz.cursors_extended.api.MinecraftCursorInitializer;
import io.github.fishstiz.cursors_extended.cursor.handler.TradeOfferButtonCursorHandler;

public class MinecraftCursorNeoforgeInitializer implements MinecraftCursorInitializer {
    @Override
    public void init(CursorTypeRegistrar cursorRegistrar, ElementRegistrar elementRegistrar) {
        elementRegistrar.register(new TradeOfferButtonCursorHandler("net.minecraft.client.gui.screens.inventory.MerchantScreen$TradeOfferButton"));
    }
}
