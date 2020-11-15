package json.jayson.playerblood.event;

import json.jayson.playerblood.capability.data.PlayerBlood;
import json.jayson.playerblood.zUtility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import java.util.Random;

public class DamageTakeEvent {

    public static void onEvent(LivingAttackEvent event) {
        PlayerEntity player = (PlayerEntity) event.getEntity();
        PlayerBlood.get(player).ifPresent(playerBlood -> {
            Random random = new Random();
            float bloodDecreasement = random.nextFloat();
            bloodDecreasement /= 1.25;
            playerBlood.decreaseBlood(bloodDecreasement,true);
            playerBlood.decreaseMaxBlood(random.nextFloat() * 0.0001f);
            zUtility.setBloodHearts(player, playerBlood);
            playerBlood.syncRemote(player);
            zUtility.sendMessage(player, new TranslationTextComponent("blood_decrease", String.format("%.03f", bloodDecreasement)), true);
        });
    }

}
