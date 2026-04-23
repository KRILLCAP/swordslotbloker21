package com.example.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public abstract class InventoryMixin {

    @Inject(method = "setSelectedSlot", at = @At("HEAD"), cancellable = true)
    private void lockSlotOnKey(int slot, CallbackInfo ci) {
        var client = MinecraftClient.getInstance();
        if (client.player == null) return;

        long window = client.getWindow().getHandle();
        boolean isCtrlPressed = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS;

        if (isCtrlPressed) {
            ci.cancel(); // Блокируем смену слота
        }
    }
}
