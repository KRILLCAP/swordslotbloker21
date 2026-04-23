package com.example.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Inject(method = "doItemUse", at = @At("HEAD"), cancellable = true)
    private void blockSlotChangeDuringHurt(CallbackInfo ci) {
        MinecraftClient client = (MinecraftClient) (Object) this;
        if (client.player == null) return;

        // Блокируем любую попытку смены слота (включая пакеты сервера), если игроку нанесён урон
        if (client.player.hurtTime > 0) {
            ci.cancel();
        }
    }
}
