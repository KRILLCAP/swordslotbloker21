package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class TemplateMod implements ClientModInitializer {
    private int previousSlot = -1;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            boolean isTakingDamage = client.player.hurtTime > 0;
            int currentSlot = client.player.getInventory().selectedSlot;

            if (isTakingDamage && previousSlot != -1 && previousSlot != currentSlot) {
                client.player.getInventory().selectedSlot = previousSlot;
            }

            previousSlot = client.player.getInventory().selectedSlot;
        });
    }
}
