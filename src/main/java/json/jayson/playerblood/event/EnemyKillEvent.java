package json.jayson.playerblood.event;

import json.jayson.playerblood.capability.data.PlayerBlood;
import json.jayson.playerblood.registry.blood.zBloodModifier;
import json.jayson.playerblood.zUtility;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.Random;

public class EnemyKillEvent {

    public static void onEvent(LivingDeathEvent event) {
        PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
        Entity target = event.getEntity();
        PlayerBlood.get(player).ifPresent(playerBlood -> {
            Random random = new Random();
            float maxBloodAdjustment = random.nextFloat() * 0.01f;
            if(zBloodModifier.blood_modifiers.containsKey(target.getType())) {
                maxBloodAdjustment *= (zBloodModifier.blood_modifiers.get(target.getType()) * 1.4);
            }
            playerBlood.adjustMaxBlood(maxBloodAdjustment);
            playerBlood.adjustBlood(maxBloodAdjustment * 0.1f);
            zUtility.setBloodHearts(player, playerBlood);
            playerBlood.syncRemote(player);
            zUtility.sendMessage(player, new TranslationTextComponent("max_blood_increase", String.format("%.04f", maxBloodAdjustment)), true);
        });
    }

}
