package json.jayson.playerblood.registry;

import com.mojang.blaze3d.matrix.MatrixStack;
import json.jayson.playerblood.bMod;
import json.jayson.playerblood.capability.data.PlayerBlood;
import json.jayson.playerblood.event.DamageTakeEvent;
import json.jayson.playerblood.event.EnemyHitEvent;
import json.jayson.playerblood.event.EntitynteractEvent;
import json.jayson.playerblood.event.EnemyKillEvent;
import json.jayson.playerblood.zUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class zEvent {


    @SubscribeEvent
    public static void LivingAttackEvent (LivingAttackEvent event) {
        Entity source = event.getSource().getTrueSource();
        Entity target = event.getEntity();
        if(source instanceof PlayerEntity) {
            EnemyHitEvent.onEvent(event);
        }
        if(target instanceof PlayerEntity) {
            if(!((PlayerEntity) target).isCreative()) {
                DamageTakeEvent.onEvent(event);
            }
        }
    }

    @SuppressWarnings("unused")
	@SubscribeEvent
    public static void LivingDeathEvent(LivingDeathEvent event)  {
        Entity entity = event.getEntity();
        Entity source = event.getSource().getTrueSource();
        if(source instanceof PlayerEntity) {
            EnemyKillEvent.onEvent(event);
        }
    }

    @SubscribeEvent
    public static void PlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        PlayerBlood.get(player).ifPresent((playerBlood) -> {
            playerBlood.syncRemote(player);
            zUtility.setBloodHearts(player, playerBlood);
        });
    }

    @SubscribeEvent
    public static void PlayerInteractEvent(PlayerInteractEvent event) {
        EntitynteractEvent.onEvent(event);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void RenderGameOverlayEvent(RenderGameOverlayEvent.Post event) {
    	if(event.isCancelable() || event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fRender = minecraft.fontRenderer;
        PlayerEntity player = minecraft.player;
        PlayerBlood.get(player).ifPresent(playerBlood -> {
            String bloodText = TextFormatting.RED + "Blood: " + zUtility.formatNumber(playerBlood.getBlood()) + "bu / " + zUtility.formatNumber(playerBlood.getMaxBlood()) + "bu";
            fRender.drawString(new MatrixStack(), bloodText, 5, 5, 0);
            minecraft.getTextureManager().bindTexture(new ResourceLocation(bMod.MOD_ID,"textures/gui/blood_progress_bar.png"));
            minecraft.ingameGUI.blit(new MatrixStack(), 5,15,0,7, (int) (100 + (bloodText.length() * 1.2)),7);
            minecraft.getTextureManager().bindTexture(new ResourceLocation(bMod.MOD_ID,"textures/gui/blood_progress_bar_1.png"));
            minecraft.ingameGUI.blit(new MatrixStack(),5,15,0,7, (int) ((playerBlood.getBlood() / playerBlood.getMaxBlood()) * (100 + (bloodText.length() * 1.2))),7);
            //PBRender.renderVignette(minecraft.getRenderViewEntity());
            //minecraft.getTextureManager().close(); <-- wouldn't do that again lmao
        });
    }

}
