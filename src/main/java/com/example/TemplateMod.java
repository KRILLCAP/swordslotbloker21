package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;

public class TemplateMod implements ClientModInitializer {

    // Счётчик тиков, чтобы не спамить сообщениями
    private int tickCounter = 0;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            // Каждые 20 тиков (раз в секунду) будем показывать сообщение
            tickCounter++;
            if (tickCounter >= 20) {
                tickCounter = 0;
                // Выводим сообщение прямо в чат
                client.player.sendMessage(Text.of("§a[МОД] §fЯ работаю!"), false);
            }
        });
    }
}
