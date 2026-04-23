package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;

public class TemplateMod implements ClientModInitializer {

    private int tickCounter = 0;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            tickCounter++;
            if (tickCounter >= 20) {
                tickCounter = 0;
                client.player.sendMessage(Text.of("§a[МОД] §fЯ работаю!"), false);
            }
        });
    }
}
