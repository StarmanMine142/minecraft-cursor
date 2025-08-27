package io.github.fishstiz.cursors_extended.cursor;

import com.mojang.blaze3d.platform.cursor.CursorType;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import org.jetbrains.annotations.Nullable;

public class CursorTickController {
    public static final CursorTickController INSTANCE = new CursorTickController();
    private final TickCursor tickCursor = new TickCursor();
    private final TickCursor fallbackTickCursor = new TickCursor();

    public @Nullable CursorType consumeTickCursor() {
        return this.tickCursor.consume();
    }

    public @Nullable CursorType consumeFallbackTickCursor() {
        return this.fallbackTickCursor.consume();
    }

    public void setTickCursor(CursorType cursorType) {
        this.tickCursor.set(cursorType);
    }

    public void setFallbackTickCursor(CursorType cursorType) {
        this.fallbackTickCursor.set(cursorType);
    }

    private static class TickCursor {
        private static final long EXPIRE_MS = SharedConstants.MILLIS_PER_TICK * 2L; // 2 ticks
        private @Nullable CursorType cursorType;
        private long timestamp = 0;

        private boolean isValid() {
            return this.cursorType != null && Util.getMillis() - this.timestamp < EXPIRE_MS;
        }

        private @Nullable CursorType consume() {
            CursorType consumed = this.isValid() ? this.cursorType : null;
            this.cursorType = null;
            return consumed;
        }

        private void set(CursorType cursorType) {
            this.cursorType = cursorType;
            this.timestamp = Util.getMillis();
        }
    }
}
