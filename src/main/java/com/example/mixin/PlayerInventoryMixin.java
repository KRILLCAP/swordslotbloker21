package com.example.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {

    @Inject(method = "setSelectedSlot", at = @At("HEAD"), cancellable = true)
    private void blockSlotChangeOnHurt(int slot, CallbackInfo ci) {
        var client = MinecraftClient.getInstance();
        if (client.player == null) return;

        // Блокируем смену слота, если игрок получил урон
        if (client.player.hurtTime > 0) {
            ci.cancel();
        }
    }
}
