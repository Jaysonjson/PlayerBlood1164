package json.jayson.playerblood.capability;

import json.jayson.playerblood.bMod;
import json.jayson.playerblood.capability.cap.EntityBloodCapability;
import json.jayson.playerblood.capability.cap.PlayerBloodCapability;
import json.jayson.playerblood.capability.data.EntityBlood;
import json.jayson.playerblood.capability.data.PlayerBlood;
import json.jayson.playerblood.capability.interfaces.IEntityBlood;
import json.jayson.playerblood.capability.interfaces.IPlayerBlood;
import json.jayson.playerblood.capability.storage.EntityBloodStorage;
import json.jayson.playerblood.capability.storage.PlayerBloodStorage;
import json.jayson.playerblood.registry.blood.zBloodModifier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class zCapability {
   
	@CapabilityInject(IPlayerBlood.class)
    public static Capability<IPlayerBlood> PLAYER_BLOOD = null;
    @CapabilityInject(IEntityBlood.class)
    public static Capability<IEntityBlood> ENTITY_BLOOD = null;

    @SubscribeEvent
    public static void CapabilityRegistry(FMLCommonSetupEvent e) {
        CapabilityManager.INSTANCE.register(IPlayerBlood.class, new PlayerBloodStorage(), PlayerBlood::new);
        CapabilityManager.INSTANCE.register(IEntityBlood.class, new EntityBloodStorage(), EntityBlood::new);
    }

    @SubscribeEvent
    public static void AttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(bMod.MOD_ID, "playerblood"), new PlayerBloodCapability());
        }

        if(event.getObject() instanceof LivingEntity) {
            if(zBloodModifier.blood_modifiers.containsKey(event.getObject().getType())) {
                //System.out.println("Found Entity " + event.getObject().getType().getRegistryName().toString() + "; attaching Capability");
                event.addCapability(new ResourceLocation(bMod.MOD_ID, "entityblood"), new EntityBloodCapability());
            }
        }
    }
    
}
