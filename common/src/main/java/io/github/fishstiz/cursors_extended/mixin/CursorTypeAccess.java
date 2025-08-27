package io.github.fishstiz.cursors_extended.mixin;

import com.mojang.blaze3d.platform.cursor.CursorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CursorType.class)
public interface CursorTypeAccess {
    @Invoker("<init>")
    static CursorType cursors_extended$createCursorType(String name, long handle) {
        throw new AssertionError();
    }
}
