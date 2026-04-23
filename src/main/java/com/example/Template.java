package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class TemplateMod implements ClientModInitializer {
    private int lastSlot = -1;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            var player = client.player;
            if (player == null) return;

            int currentSlot = player.getInventory().selectedSlot;
            boolean isHurt = player.hurtTime > 0;

            // Если игрока ударили и слот изменился — откатываем
            if (isHurt && lastSlot != -1 && lastSlot != currentSlot) {
                player.getInventory().selectedSlot = lastSlot;
            }

            lastSlot = player.getInventory().selectedSlot;
        });
    }
}
