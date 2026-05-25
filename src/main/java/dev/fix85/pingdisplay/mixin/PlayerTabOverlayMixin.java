package dev.fix85.pingdisplay.mixin;

import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.ChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerTabOverlay.class)
public class PlayerTabOverlayMixin {
    @Inject(method = "getNameForDisplay", at = @At("RETURN"), cancellable = true)
    private void appendPingToName(PlayerInfo playerInfo, CallbackInfoReturnable<Component> cir) {
        Component original = cir.getReturnValue();
        int ping = playerInfo.getLatency();
        
        ChatFormatting color;
        if (ping <= 80) {
            color = ChatFormatting.GREEN;
        } else if (ping <= 150) {
            color = ChatFormatting.YELLOW;
        } else {
            color = ChatFormatting.RED;
        }
        
        MutableComponent pingComponent = Component.literal(" [" + ping + "ms]").withStyle(color);
        
        MutableComponent finalComponent = Component.empty();
        boolean before = dev.fix85.pingdisplay.PingConfig.get().pingBeforeName;
        
        if (before) {
            finalComponent.append(pingComponent).append(" ");
            if (original != null) {
                finalComponent.append(original);
            } else {
                finalComponent.append(Component.literal(playerInfo.getProfile().getName()));
            }
        } else {
            if (original != null) {
                finalComponent.append(original);
            } else {
                finalComponent.append(Component.literal(playerInfo.getProfile().getName()));
            }
            finalComponent.append(pingComponent);
        }
        cir.setReturnValue(finalComponent);
    }
}
