package json.jayson.playerblood.registry;

import java.util.Objects;

import json.jayson.playerblood.bMod;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = bMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class zBlock {

    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, bMod.MOD_ID);
    public static final RegistryObject<Block> BLOODY_WOOD = BLOCKS.register("bloody_wood", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.25f,1.25f).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(1)));

	
    @SubscribeEvent
    public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();
        BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
                registry.register(new BlockItem(block, new Item.Properties().group(zItemGroup.PLAYERBLOOD)).setRegistryName(Objects.requireNonNull(block.getRegistryName())));
        });
    }
}
