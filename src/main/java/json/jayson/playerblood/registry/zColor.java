package json.jayson.playerblood.registry;

import json.jayson.playerblood.object.zItemNBT;
import json.jayson.playerblood.registry.object.zItem;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class zColor {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerItemColourHandlers(ColorHandlerEvent.Item event) {
        ItemColors itemColors = event.getItemColors();
        itemColors.register((itemStack, index) -> {
            if(itemStack.hasTag()) {
                if(index == 0) {
                    return itemStack.getTag().getInt(zItemNBT.COLOR);
                }
                if(index == 1) {
                    if (itemStack.getTag().getInt(zItemNBT.BLOOD_AMOUNT) > 0) {
                        return itemStack.getTag().getInt(zItemNBT.BLOOD_COLOR);
                    }
                }
            }
            return 0;
        }, zItem.SYRINGE.get());
    }
}
