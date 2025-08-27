package io.github.fishstiz.cursors_extended.compat.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.github.fishstiz.cursors_extended.gui.screen.ConfigurationScreen;

public class CursorsExtendedModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<ConfigurationScreen> getModConfigScreenFactory() {
        return ConfigurationScreen::new;
    }
}
