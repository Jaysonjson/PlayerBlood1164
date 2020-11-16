package json.jayson.playerblood.object.item;

import json.jayson.playerblood.capability.data.PlayerBlood;
import json.jayson.playerblood.zUtility;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;


//Geportet von 1.15. --> Sehr Alt
@Deprecated
public class BloodOrbItem extends Item {

    public BloodOrbItem(Properties properties) {
        super(properties);
    }
    @Override
    public void inventoryTick(ItemStack stack, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        CompoundNBT nbt;
        if (stack.hasTag()) {
            nbt = stack.getTag();
        } else {
            nbt = new CompoundNBT();
            nbt.putInt("Type", random.nextInt(2));
            nbt.putFloat("Amount", random.nextInt(27) + random.nextFloat());
            stack.setTag(nbt);
        }
        stack.setTag(nbt);
        if(stack.hasTag() && nbt.getFloat("Amount") < 0.0001) {
            stack.shrink(1);
        }
        super.inventoryTick(stack, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        PlayerBlood.get(playerIn).ifPresent(playerBlood -> {
            ItemStack stack = playerIn.getHeldItem(handIn);
            if(stack.hasTag()) {
                CompoundNBT nbt = stack.getTag();
                float amount = nbt.getFloat("Amount");
                int type = nbt.getInt("Type");
                if(type == 0) {
                    playerBlood.adjustMaxBlood(amount);
                    stack.shrink(1);
                    zUtility.sendMessage(playerIn, new TranslationTextComponent("max_blood_increase", String.format("%.02f", amount)), true);
                } else if (type == 1){
                    float realAmount = amount * 1.5f;
                    if(playerBlood.isOverflow(amount)) {
                        // realAmount = playerBlood.getBlood() - playerBlood.getMaxBlood();
                        //nbt.putFloat("Amount", amount - realAmount);
                    }
                    playerBlood.adjustBlood(realAmount, true);
                    stack.shrink(1);
                    zUtility.sendMessage(playerIn, new TranslationTextComponent("blood_increase", String.format("%.02f", realAmount)), true);
                }
                zUtility.setBloodHearts(playerIn, playerBlood);
                playerBlood.syncRemote(playerIn);
            }
        });
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World p_77624_2_, List<ITextComponent> tooltip, ITooltipFlag p_77624_4_) {
        int type = -1;
        float bloodAmount = 0;
        if (stack.hasTag() && stack.getTag().contains("Type") && stack.getTag().contains("Amount")) {
            CompoundNBT nbt = stack.getTag();
            type = nbt.getInt("Type");
            bloodAmount = nbt.getFloat("Amount");
        }
        float finalBloodAmount = bloodAmount;
        if(type == 0) {
            tooltip.add(new TranslationTextComponent("Increases your Max Blood by " + String.format("%.02f", finalBloodAmount)));
        }
        if(type == 1) {
            tooltip.add(new TranslationTextComponent("Increases your Blood by " + String.format("%.02f", finalBloodAmount * 1.5f)));
        }
        super.addInformation(stack, p_77624_2_, tooltip, p_77624_4_);
    }
}
