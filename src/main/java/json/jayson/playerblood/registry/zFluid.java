package json.jayson.playerblood.registry;

import json.jayson.playerblood.bMod;
import json.jayson.playerblood.object.fluid.blood.FluidBlood;
import json.jayson.playerblood.object.fluid.blood.FluidBloodBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class zFluid {

    public static DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, bMod.MOD_ID);
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, bMod.MOD_ID);
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, bMod.MOD_ID);

    public static final RegistryObject<Fluid> BLOOD = FLUIDS.register("blood", FluidBlood.Source::new);
    public static final RegistryObject<Fluid> BLOOD_FLOWING = FLUIDS.register("blood_flow", FluidBlood.Flowing::new);

    public static final RegistryObject<Block> BLOOD_BLOCK = BLOCKS.register("blood", () -> new FluidBloodBlock(() -> (FlowingFluid) BLOOD.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));
    public static final RegistryObject<Item> BLOOD_BUCKET = ITEMS.register("blood_bucket", () -> new BucketItem(BLOOD, new Item.Properties().maxStackSize(1)));


}
