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
        itemColors.register((p_getColor_1_, p_getColor_2_) -> {
            if(p_getColor_1_.hasTag()) {
                if(p_getColor_2_ == 0) {
                    return p_getColor_1_.getTag().getInt("Color");
                }
                if(p_getColor_2_ == 1) {
                    if (p_getColor_1_.getTag().getInt("Amount") > 0) {
                        return p_getColor_1_.getTag().getInt("BloodColor");
                    }
                }
            }
            return 0;
        }, zItem.SYRINGE.get());
    }
}
