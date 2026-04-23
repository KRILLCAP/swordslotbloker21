package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.network.v1.ClientPlayNetworking;
import net.minecraft.network.packet.s2c.play.UpdateSelectedSlotS2CPacket;

public class TemplateMod implements ClientModInitializer {

    private int lastSlot = -1;
    private boolean wasHurt = false;

    @Override
    public void onInitializeClient() {
        // Перехватываем пакет принудительной смены слота СЕРВЕРОМ
        ClientPlayNetworking.registerGlobalReceiver(UpdateSelectedSlotS2CPacket.class, (packet, player, responseSender) -> {
            // Игнорируем пакет — слот не меняется
        });

        // Дополнительная страховка: если сервер меняет слот в обход пакета
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            int currentSlot = client.player.getInventory().selectedSlot;
            boolean isHurt = client.player.hurtTime > 0;

            if (isHurt && lastSlot != -1 && lastSlot != currentSlot) {
                client.player.getInventory().selectedSlot = lastSlot;
            }

            lastSlot = client.player.getInventory().selectedSlot;
        });
    }
}
