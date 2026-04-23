package com.example;

import net.fabricmc.api.ClientModInitializer;

public class TemplateMod implements ClientModInitializer {

    public static boolean lockSlot = false;

    @Override
    public void onInitializeClient() {
        // Пусто, Mixin сам обрабатывает
    }
}
