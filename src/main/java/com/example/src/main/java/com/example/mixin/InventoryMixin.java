package com.example.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.lwjgl.glfw.GLFW;

@Mixin(PlayerInventory.class)
public abstract class InventoryMixin {

    private static final KeyBinding LOCK_KEY = new KeyBinding(
            "key.lockslot",
            InputUtil.Type.MOUSE,
            GLFW.GLFW_MOUSE_BUTTON_5,
            "category.lockslot"
    );

    @Inject(method = "setSelectedSlot", at = @At("HEAD"), cancellable = true)
    private void lockSlotOnKey(int slot, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player == null) return;

        if (LOCK_KEY.isPressed()) {
            ci.cancel();
        }
    }
}
