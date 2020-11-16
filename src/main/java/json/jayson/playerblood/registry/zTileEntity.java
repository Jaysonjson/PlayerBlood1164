package json.jayson.playerblood.registry;

import json.jayson.playerblood.bMod;
import json.jayson.playerblood.object.fluid.blood.FluidBloodBlockTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class zTileEntity {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, bMod.MOD_ID);
    public static final RegistryObject<TileEntityType<FluidBloodBlockTileEntity>> BLOOD = TILE_ENTITIES.register("blood", () -> TileEntityType.Builder.create(FluidBloodBlockTileEntity::new, zFluid.BLOOD_BLOCK.get()).build(null));
}
