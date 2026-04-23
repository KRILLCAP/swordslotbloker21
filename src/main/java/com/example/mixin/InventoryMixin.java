package com.example.mixin;

import com.example.TemplateMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public abstract class InventoryMixin {

    private static boolean wasPressed = false;

    @Inject(method = "setSelectedSlot", at = @At("HEAD"), cancellable = true)
    private void lockSlotOnKey(int slot, CallbackInfo ci) {
        var client = MinecraftClient.getInstance();
        if (client.player == null) return;

        long window = client.getWindow().getHandle();
        boolean isPressed = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS;

        if (isPressed && !wasPressed) {
            client.player.sendMessage(Text.of("§a[Confuse Blocker] §fСлот заблокирован"), true);
        }
        if (!isPressed && wasPressed) {
            client.player.sendMessage(Text.of("§c[Confuse Blocker] §fСлот разблокирован"), true);
        }
        wasPressed = isPressed;

        if (isPressed) {
            ci.cancel();
        }
    }
}
