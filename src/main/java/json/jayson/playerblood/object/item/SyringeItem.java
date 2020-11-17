package json.jayson.playerblood.object.item;

import json.jayson.playerblood.object.zItemNBT;
import json.jayson.playerblood.object.blood.HasBlood;
import json.jayson.playerblood.object.fluid.blood.FluidBloodBlock;
import json.jayson.playerblood.object.fluid.blood.FluidBloodBlockTileEntity;
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


//Geportet von 1.15. --> Sehr Alt
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
            nbt.putInt(zItemNBT.COLOR, new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());
            nbt.putInt(zItemNBT.BLOOD_COLOR, new Color(ThreadLocalRandom.current().nextInt(155, 254 + 1), 66, 66).getRGB());
            nbt.putString(zItemNBT.ENTITY, "Unknown");
            stack.setTag(nbt);
        }
        if(nbt != null) {
            stack.setTag(nbt);
            if (!nbt.getString(zItemNBT.ENTITY).equalsIgnoreCase("Unknown")) {
                if (!entityExists(nbt.getString(zItemNBT.ENTITY))) {
                    resetEntity(nbt);
                }
            }
        }
        super.inventoryTick(stack, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
       
    	RayTraceResult raytraceresult = rayTrace(world, player, RayTraceContext.FluidMode.SOURCE_ONLY);
       
    	ItemStack item = player.getHeldItemMainhand();
        CompoundNBT nbt = item.getTag();

        if(nbt != null) {
            float amount = nbt.getFloat(zItemNBT.BLOOD_AMOUNT);
            String entity = nbt.getString(zItemNBT.ENTITY);
            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {

                BlockPos blockpos = new BlockPos(raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
                RayTraceResult rayTrace = rayTrace(world, player, RayTraceContext.FluidMode.SOURCE_ONLY);
                BlockPos blockposIn = getPlacementPosition(world.getBlockState(blockpos), blockpos, rayTrace);
                BlockState clickedBlockState = world.getBlockState(blockposIn);

                if (clickedBlockState.getBlock() instanceof FluidBloodBlock && clickedBlockState.getFluidState().isSource()) {
                    FluidBloodBlockTileEntity tileEntity = (FluidBloodBlockTileEntity) world.getTileEntity(blockposIn);
                    if (tileEntity.owner.equalsIgnoreCase(entity) || entity.equalsIgnoreCase("Unknown")) {
                        amount = amount + zUtility.convertToMBU(tileEntity.amount);
                        entity = tileEntity.owner;
                        world.setBlockState(blockposIn, Blocks.AIR.getDefaultState());
                        nbt.putString(zItemNBT.ENTITY, entity);
                        nbt.putFloat(zItemNBT.BLOOD_AMOUNT, amount);
                        item.setTag(nbt);
                    } else {
                        zUtility.sendMessage(player, new TranslationTextComponent("syringe_wrong_owner"), true);
                    }
                }
            } else {
                if (amount > 0) {
                    zUtility.injectBlood(player, new HasBlood(amount, entity, null));
                }
            }
        }
        return super.onItemRightClick(world, player, hand);
    }

    private BlockPos getPlacementPosition(BlockState p_210768_1_, BlockPos p_210768_2_, RayTraceResult p_210768_3_) {
        return p_210768_1_.getBlock() instanceof ILiquidContainer ? p_210768_2_ : new BlockPos(p_210768_3_.getHitVec().x, p_210768_3_.getHitVec().y, p_210768_3_.getHitVec().z);
    }

    private boolean entityExists(String entity) {
        return ForgeRegistries.ENTITIES.containsKey(new ResourceLocation(entity.toLowerCase()));
    }

    private void resetEntity(CompoundNBT nbt) {
        nbt.putString(zItemNBT.ENTITY, "Unknown");
        nbt.putInt(zItemNBT.BLOOD_AMOUNT, 0);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World p_77624_2_, List<ITextComponent> tooltip, ITooltipFlag p_77624_4_) {
        String entity = "EMPTY";
        float bloodAmount = 0;
        if (stack.hasTag() && stack.getTag().contains(zItemNBT.ENTITY) && stack.getTag().contains(zItemNBT.BLOOD_AMOUNT)) {
            CompoundNBT nbt = stack.getTag();
            if(entityExists(nbt.getString(zItemNBT.ENTITY))) {
                entity = new TranslationTextComponent(ForgeRegistries.ENTITIES.getValue(new ResourceLocation(nbt.getString(zItemNBT.ENTITY))).getTranslationKey()).getString();
                bloodAmount = nbt.getFloat(zItemNBT.BLOOD_AMOUNT);
            }
        }
        tooltip.add(new TranslationTextComponent("Owner: " + "\247c" + entity));
        tooltip.add(new TranslationTextComponent("Amount: " + "\247c" + String.format("%.02f", bloodAmount) + "\247rmbu (" + String.format("%.04f", zUtility.convertToBU(bloodAmount)) + "bu)"));
        super.addInformation(stack, p_77624_2_, tooltip, p_77624_4_);
    }
}
