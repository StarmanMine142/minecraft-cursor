package io.github.fishstiz.cursors_extended.cursor;

import com.mojang.blaze3d.platform.cursor.CursorType;

public interface CursorProvider {
    default CursorType cursors_extended$cursorType(double mouseX, double mouseY) {
        return null;
    }
}
