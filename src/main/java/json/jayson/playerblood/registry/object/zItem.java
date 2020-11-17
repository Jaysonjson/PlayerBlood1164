package json.jayson.playerblood.registry.object;

import json.jayson.playerblood.bMod;
import json.jayson.playerblood.object.item.*;
import json.jayson.playerblood.registry.zItemGroup;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class zItem {
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, bMod.MOD_ID);
    public static RegistryObject<Item> SYRINGE = ITEMS.register("syringe", () -> new SyringeItem(new Item.Properties().group(zItemGroup.PLAYERBLOOD)));
    public static RegistryObject<Item> BLOOD_ORB = ITEMS.register("blood_orb", () -> new BloodOrbItem(new Item.Properties().group(zItemGroup.PLAYERBLOOD)));
    public static RegistryObject<Item> RESET = ITEMS.register("blood_reset", () -> new ResetItem(new Item.Properties().group(zItemGroup.PLAYERBLOOD)));
    public static RegistryObject<Item> BLOOD_SCANNER = ITEMS.register("blood_scanner", () -> new BloodScannerItem(new Item.Properties().group(zItemGroup.PLAYERBLOOD)));
}
