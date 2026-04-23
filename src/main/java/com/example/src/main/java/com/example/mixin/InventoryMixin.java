package com.example.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.lwjgl.glfw.GLFW;

@Mixin(PlayerInventory.class)
public abstract class InventoryMixin {

    // Используем Mouse Button 4 (нижняя боковая кнопка)
    private static final KeyBinding LOCK_KEY = new KeyBinding(
            "key.lockslot",
            InputUtil.Type.MOUSE,
            GLFW.GLFW_MOUSE_BUTTON_4,
            "category.lockslot"
    );

    // Флаг для сообщения
    private static boolean wasPressed = false;

    @Inject(method = "setSelectedSlot", at = @At("HEAD"), cancellable = true)
    private void lockSlotOnKey(int slot, CallbackInfo ci) {
        var client = MinecraftClient.getInstance();
        if (client.player == null) return;

        boolean isPressed = LOCK_KEY.isPressed();

        // Показываем сообщение при зажатии кнопки
        if (isPressed && !wasPressed) {
            client.player.sendMessage(Text.of("§a[Confuse Blocker] §fСлот заблокирован"), true);
        }
        // Показываем сообщение при отпускании
        if (!isPressed && wasPressed) {
            client.player.sendMessage(Text.of("§c[Confuse Blocker] §fСлот разблокирован"), true);
        }
        
        wasPressed = isPressed;

        // Блокируем смену слота, если кнопка зажата
        if (isPressed) {
            ci.cancel();
        }
    }
}
