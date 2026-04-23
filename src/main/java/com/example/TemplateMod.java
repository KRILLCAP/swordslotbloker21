package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class TemplateMod implements ClientModInitializer {
    private int lastSlot = -1;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            int currentSlot = client.player.getInventory().selectedSlot;
            boolean isHurt = client.player.hurtTime > 0;

            // Если получили урон и слот изменился - откатываем
            if (isHurt && lastSlot != -1 && lastSlot != currentSlot) {
                client.player.getInventory().selectedSlot = lastSlot;
            }

            // Всегда запоминаем слот ДО возможной смены
            if (!isHurt) {
                lastSlot = client.player.getInventory().selectedSlot;
            }
        });
    }
}
