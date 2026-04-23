package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class TemplateMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Сообщение при загрузке мода
        System.out.println("[Confuse Blocker] Мод загружен!");

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            long window = client.getWindow().getHandle();
            boolean isPressed = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS;

            if (isPressed) {
                client.player.sendMessage(Text.of("§a[Confuse Blocker] §fСлот ЗАБЛОКИРОВАН"), false);
            }
        });
    }
}
