package json.jayson.playerblood;

import json.jayson.playerblood.capability.data.PlayerBlood;
import json.jayson.playerblood.capability.interfaces.IPlayerBlood;
import json.jayson.playerblood.object.blood.IHasBlood;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.List;

public class zUtility {

    public static void sendMessage(PlayerEntity player, TranslationTextComponent translation, boolean hotBar) {
        if (!player.world.isRemote) {
            player.sendStatusMessage(translation, hotBar);
        }
    }

    public static void sendMessageToAll(TranslationTextComponent translation) {
        List<ServerPlayerEntity> players = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers();
        players.forEach(playerMP -> sendMessage(playerMP, translation, false));
    }

    public static float convertToBU(float mbu) {
        return mbu * 0.001f;
    }

    public static float convertToMBU(float bu) {
        return bu * 1000;
    }

    public static void setBloodHearts(PlayerEntity player, IPlayerBlood playerBlood) {
        player.setHealth(player.getHealth() + (playerBlood.getBlood()) * 0.01f);
        player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(19 + ((playerBlood.getBlood() * 0.01f) * 2));
    }

    public static String formatNumber(Object number) {
        String string = String.format("%.2f", number);
        if((float)number > 1000) string = String.format("%.2fk", (float)number / 1000.0);
        if((float)number > 1000000) string = String.format("%.2fM", (float)number / 1000000.0);
        if((float)number > 1000000000) string = String.format("%.2fG", (float)number / 1000000000.0);
        return string;
    }
    
    public static void injectBlood(PlayerEntity player, IHasBlood hasBlood) {
        PlayerBlood.get(player).ifPresent(playerBlood -> {
            if(playerBlood.isOverflow(hasBlood.getAmount())) {
                playerBlood.adjustBlood(hasBlood.getAmount(), true);
                hasBlood.decreaseAmount(hasBlood.getAmount() - playerBlood.getOverflow(hasBlood.getAmount()));
            } else {
                playerBlood.adjustBlood(hasBlood.getAmount());
                hasBlood.setAmount(0);
            }
            player.sendStatusMessage(new TranslationTextComponent("Adjusted Blood by " + hasBlood.getAmount()), true);
            playerBlood.syncRemote(player);
        });
    }
    
}
