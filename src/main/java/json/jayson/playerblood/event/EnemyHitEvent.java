package json.jayson.playerblood.event;

import json.jayson.playerblood.capability.data.PlayerBlood;
import json.jayson.playerblood.registry.blood.zBloodModifier;
import json.jayson.playerblood.zUtility;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import java.util.Random;

public class EnemyHitEvent {

    public static void onEvent(LivingAttackEvent event) {
        PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
        Entity target = event.getEntity();
        PlayerBlood.get(player).ifPresent((playerBlood -> {
            Random random = new Random();
            if (random.nextInt(35) == 1) {
                player.addPotionEffect(new EffectInstance(Effects.HUNGER, 125));
            }
            if (random.nextInt(85) == 1) {
                player.addPotionEffect(new EffectInstance(Effects.NAUSEA, 225));
            }
            float bloodAdjustment = (random.nextFloat() * 0.001f) * (event.getAmount() * 10);
            if (zBloodModifier.blood_modifiers.containsKey(target.getType())) {
                bloodAdjustment *= zBloodModifier.blood_modifiers.get(target.getType());
            }
            playerBlood.adjustBlood(bloodAdjustment, true);
            playerBlood.adjustMaxBlood(bloodAdjustment * 0.001f);
            if (!playerBlood.isOverflow(bloodAdjustment)) {
                zUtility.sendMessage(player, new TranslationTextComponent("blood_increase", String.format("%.03f", bloodAdjustment)), true);
            } else {
                zUtility.sendMessage(player, new TranslationTextComponent("blood_overflow", String.format("%.03f", playerBlood.getOverflow(bloodAdjustment))), true);
            }
            zUtility.setBloodHearts(player, playerBlood);
            playerBlood.syncRemote(player);
            /*if (playerBlood.isOverflow(bloodAdjustment)) {
                player.getEntityWorld().setBlockState(target.getPosition(), zFluid.BLOOD.get().getDefaultState().getBlockState());
                FluidBloodBlockTileEntity tileEntity = (FluidBloodBlockTileEntity) player.getEntityWorld().getTileEntity(target.getPosition());
                tileEntity.amount = playerBlood.getOverflow(bloodAdjustment);
                tileEntity.owner = ForgeRegistries.ENTITIES.getKey(target.getType()).getPath();
            }
            */
            for (int i = 0; i < random.nextInt(15); i++) {
                player.getEntityWorld().addParticle(ParticleTypes.FALLING_LAVA, target.getPosX(), target.getPosY(), target.getPosZ(), random.nextDouble() * 0.025, random.nextDouble() * 0.025, random.nextDouble() * 0.025);
            }
        }));
    }

}
