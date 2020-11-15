package json.jayson.playerblood.objects.item;

import json.jayson.playerblood.capability.data.PlayerBlood;
import json.jayson.playerblood.zUtility;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ResetItem extends Item {
    public ResetItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        PlayerBlood.get(playerIn).ifPresent(playerBlood -> {
            playerBlood.resetBlood();
            playerBlood.resetMaxBlood();
            zUtility.setBloodHearts(playerIn, playerBlood);
            playerBlood.syncRemote(playerIn);
        });
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World p_77624_2_, List<ITextComponent> tooltip, ITooltipFlag p_77624_4_) {
        tooltip.add(new TranslationTextComponent("Resets Blood and Maxblood"));
        super.addInformation(stack, p_77624_2_, tooltip, p_77624_4_);
    }
}
