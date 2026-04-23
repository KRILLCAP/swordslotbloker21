package com.example.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.UpdateSelectedSlotS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class UpdateSlotMixin {

    @Inject(method = "onUpdateSelectedSlot", at = @At("HEAD"), cancellable = true)
    private void onSlotPacket(UpdateSelectedSlotS2CPacket packet, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        // Блокируем принудительную смену слота, если висит урон
        if (client.player.hurtTime > 0) {
            ci.cancel();
        }
    }
}
