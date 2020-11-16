package json.jayson.playerblood.event;

import json.jayson.playerblood.capability.data.EntityBlood;
import json.jayson.playerblood.object.item.SyringeItem;
import json.jayson.playerblood.object.zItemNBT;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.registries.ForgeRegistries;

public class EntitynteractEvent {
	
	public static void onEvent(PlayerInteractEvent event) {
		PlayerEntity player = event.getPlayer();
		ItemStack itemStack = event.getItemStack();
		CompoundNBT nbt = itemStack.getTag();
		if(event instanceof EntityInteract) {
			Entity target = ((EntityInteract) event).getTarget();
			if(itemStack.getItem() instanceof SyringeItem) {
				if(nbt.getString(zItemNBT.ENTITY).equalsIgnoreCase(ForgeRegistries.ENTITIES.getKey(target.getType()).toString())) {
					EntityBlood.get(target).ifPresent((entityBlood -> {
						if(entityBlood.getBlood() < 1) {
							target.remove();
						} else {
							if(entityBlood.getBlood() > 9) {
								nbt.putFloat(zItemNBT.BLOOD_AMOUNT, nbt.getFloat(zItemNBT.BLOOD_AMOUNT) + 10);
							} else {
								nbt.putFloat(zItemNBT.BLOOD_AMOUNT, nbt.getFloat(zItemNBT.BLOOD_AMOUNT) + entityBlood.getBlood());
							}
						}
					}));
				} else {
					player.sendStatusMessage(new TranslationTextComponent("Wrong Owner!"), true);
				}
			}
		}
	}

}
