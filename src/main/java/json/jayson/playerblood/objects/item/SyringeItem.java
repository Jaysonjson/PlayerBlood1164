package json.jayson.playerblood.objects.item;

import json.jayson.playerblood.capability.data.PlayerBlood;
import json.jayson.playerblood.objects.HasBlood;
import json.jayson.playerblood.objects.IHasBlood;
import json.jayson.playerblood.objects.fluid.blood.FluidBloodBlock;
import json.jayson.playerblood.objects.fluid.blood.FluidBloodBlockTileEntity;
import json.jayson.playerblood.zUtility;
import net.minecraft.block.*;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SyringeItem extends Item {

    public SyringeItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }
    public int capacity = 10000;
    public HasBlood blood = new HasBlood(0, "Unknown", null);

    @Override
    public void inventoryTick(ItemStack stack, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        CompoundNBT nbt;
        if (stack.hasTag()) {
            nbt = stack.getTag();
        } else {
            nbt = new CompoundNBT();
            blood.setNBT(nbt, blood);
            nbt.putInt("Color", new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());
            nbt.putInt("BloodColor", new Color(ThreadLocalRandom.current().nextInt(155, 254 + 1), 66, 66).getRGB());
            stack.setTag(nbt);
        }
        if(nbt != null) {
            stack.setTag(nbt);
            if (nbt.getString("Entity") != "Unknown") {
                if (!entityExists(nbt.getString("Entity"))) {
                    resetEntity(nbt);
                }
            }
        }
        super.inventoryTick(stack, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        RayTraceResult raytraceresult = rayTrace(p_77659_1_, p_77659_2_, RayTraceContext.FluidMode.SOURCE_ONLY);
        ItemStack item = p_77659_2_.getHeldItemMainhand();
        CompoundNBT nbt = item.getTag();
        float amount = nbt.getFloat("Amount");
        String entity = nbt.getString("Entity");
        if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos = new BlockPos(raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
            RayTraceResult rayTrace = rayTrace(p_77659_1_, p_77659_2_, RayTraceContext.FluidMode.SOURCE_ONLY);
            BlockPos blockposIn = getPlacementPosition(p_77659_1_.getBlockState(blockpos), blockpos, rayTrace);
            if (p_77659_1_.getBlockState(blockposIn).getBlock() instanceof FluidBloodBlock && p_77659_1_.getBlockState(blockpos).getFluidState().isSource()) {
                FluidBloodBlockTileEntity tileEntity = (FluidBloodBlockTileEntity) p_77659_1_.getTileEntity(blockposIn);
                if (tileEntity.owner.equalsIgnoreCase(entity) || entity.equalsIgnoreCase("Unknown")) {
                    amount = amount + zUtility.convertToMBU(tileEntity.amount);
                    entity = tileEntity.owner;
                    p_77659_1_.setBlockState(blockposIn, Blocks.AIR.getDefaultState());
                    nbt.putString("Entity", entity);
                    nbt.putFloat("Amount", amount);
                } else {
                    zUtility.sendMessage(p_77659_2_, new TranslationTextComponent("syringe_wrong_owner"), true);
                }
            }
        } else {
            if(amount > 0) {
                injectBlood(p_77659_2_, new HasBlood(amount, entity, null));
            }
        }
        return super.onItemRightClick(p_77659_1_, p_77659_2_, p_77659_3_);
    }


    private void injectBlood(PlayerEntity player, IHasBlood hasBlood) {
        PlayerBlood.get(player).ifPresent(playerBlood -> {
            if(playerBlood.isOverflow(hasBlood.getAmount())) {
                playerBlood.adjustBlood(hasBlood.getAmount(), true);
                hasBlood.decreaseAmount(hasBlood.getAmount() - playerBlood.getOverflow(hasBlood.getAmount()));
            } else {
                playerBlood.adjustBlood(hasBlood.getAmount());
                hasBlood.setAmount(0);
            }
            player.sendStatusMessage(new TranslationTextComponent("Adjusted Blood by " + hasBlood.getAmount()), true);
            playerBlood.syncRemote(player);
        });
    }

    private BlockPos getPlacementPosition(BlockState p_210768_1_, BlockPos p_210768_2_, RayTraceResult p_210768_3_) {
        return p_210768_1_.getBlock() instanceof ILiquidContainer ? p_210768_2_ : new BlockPos(p_210768_3_.getHitVec().x, p_210768_3_.getHitVec().y, p_210768_3_.getHitVec().z);
    }

    private boolean entityExists(String entity) {
        return ForgeRegistries.ENTITIES.containsKey(new ResourceLocation(entity.toLowerCase()));
    }

    private void resetEntity(CompoundNBT nbt) {
        nbt.putString("Entity", "Unknown");
        nbt.putInt("Amount", 0);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World p_77624_2_, List<ITextComponent> tooltip, ITooltipFlag p_77624_4_) {
        String entity = "EMPTY";
        float bloodAmount = 0;
        if (stack.hasTag() && stack.getTag().contains("Entity") && stack.getTag().contains("Amount")) {
            CompoundNBT nbt = stack.getTag();
            if(entityExists(nbt.getString("Entity"))) {
                entity = new TranslationTextComponent(ForgeRegistries.ENTITIES.getValue(new ResourceLocation(nbt.getString("Entity"))).getTranslationKey()).getUnformattedComponentText();
                bloodAmount = nbt.getFloat("Amount");
            }
        }
        tooltip.add(new TranslationTextComponent("Owner: " + "\247c" + entity));
        tooltip.add(new TranslationTextComponent("Amount: " + "\247c" + String.format("%.02f", bloodAmount) + "\247rmbu (" + String.format("%.04f", zUtility.convertToBU(bloodAmount)) + "bu)"));
        super.addInformation(stack, p_77624_2_, tooltip, p_77624_4_);
    }
}
