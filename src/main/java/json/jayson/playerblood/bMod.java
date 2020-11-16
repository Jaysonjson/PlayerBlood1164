package json.jayson.playerblood;

import json.jayson.playerblood.capability.zCapability;
import json.jayson.playerblood.network.zNetwork;
import json.jayson.playerblood.registry.*;
import json.jayson.playerblood.registry.blood.zBloodModifier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod("playerblood")
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class bMod {
    public static final String MOD_ID = "playerblood";
    public static final String BLOOD_MODIFIER_DIR = FMLPaths.MODSDIR.get().toString() + "/1.16.3/playerblood/";
    public static final String BLOOD_MODIFIER_JSON = BLOOD_MODIFIER_DIR + "bloodmodify.json";
    public bMod() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        zBloodModifier.init();
        MinecraftForge.EVENT_BUS.register(zEvent.class);
        modEventBus.addListener(zCapability::CapabilityRegistry);
        MinecraftForge.EVENT_BUS.register(zCapability.class);

        modEventBus.addListener(this::onCommonSetup);
        modEventBus.register(zColor.class);

        zFluid.BLOCKS.register(modEventBus);
        zFluid.ITEMS.register(modEventBus);
        zFluid.FLUIDS.register(modEventBus);

        zItem.ITEMS.register(modEventBus);
        zBlock.BLOCKS.register(modEventBus);
        
        zTileEntity.TILE_ENTITIES.register(modEventBus);

    }

    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent event) {
        zNetwork.init();
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
       //ClientRegistry.bindTileEntityRenderer(PBTileEntity.BLOOD.get(), FluidBloodBlockTileEntityRenderer::new);
    }

    @SubscribeEvent
    public void onCommandRegistry(RegisterCommandsEvent event) {
    }
}
