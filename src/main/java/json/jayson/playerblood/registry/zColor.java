package json.jayson.playerblood.registry;

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
                    return itemStack.getTag().getInt("Color");
                }
                if(index == 1) {
                    if (itemStack.getTag().getInt("Amount") > 0) {
                        return itemStack.getTag().getInt("BloodColor");
                    }
                }
            }
            return 0;
        }, zItem.SYRINGE.get());
    }
}
