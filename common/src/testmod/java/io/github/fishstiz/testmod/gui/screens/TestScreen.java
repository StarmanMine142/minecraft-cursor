package io.github.fishstiz.testmod.gui.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class TestScreen extends Screen {
    public TestScreen() {
        super(Component.literal("Test Screen"));
    }

    @Override
    protected void init() {
        this.addRenderableWidget(testButton().build());
    }

    private static Button.Builder testButton() {
        return testButton("");
    }

    private static Button.Builder testButton(String text) {
        return Button.builder(Component.literal(text), btn -> {
        });
    }

    public static void open() {
        Minecraft.getInstance().setScreen(new TestScreen());
    }
}
