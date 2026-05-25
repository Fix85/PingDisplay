package dev.fix85.pingdisplay.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.ChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {
    @Inject(method = "getNameTag", at = @At("RETURN"), cancellable = true)
    private void modifyNameTag(T entity, CallbackInfoReturnable<Component> cir) {
        Component displayName = cir.getReturnValue();
        if (displayName != null && entity instanceof Player player) {
            Minecraft client = Minecraft.getInstance();
            if (client.getConnection() != null) {
                PlayerInfo info = client.getConnection().getPlayerInfo(player.getUUID());
                if (info != null) {
                    int ping = info.getLatency();
                    
                    ChatFormatting color;
                    if (ping <= 80) {
                        color = ChatFormatting.GREEN;
                    } else if (ping <= 150) {
                        color = ChatFormatting.YELLOW;
                    } else {
                        color = ChatFormatting.RED;
                    }
                    
                    MutableComponent pingComponent = Component.literal(" [" + ping + "ms]").withStyle(color);
                    boolean before = dev.fix85.pingdisplay.PingConfig.get().pingBeforeName;
                    if (before) {
                        cir.setReturnValue(Component.empty().append(pingComponent).append(" ").append(displayName));
                    } else {
                        cir.setReturnValue(Component.empty().append(displayName).append(pingComponent));
                    }
                }
            }
        }
    }
}
