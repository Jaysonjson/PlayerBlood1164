package json.jayson.playerblood.object.fluid.blood;

import json.jayson.playerblood.registry.zFluid;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.fluids.FluidAttributes;

public class FluidBlood extends FlowingFluid {

    @Override
    public Fluid getFlowingFluid() {
        return zFluid.BLOOD_FLOWING.get();
    }

    @Override
    public Fluid getStillFluid() {
        return zFluid.BLOOD.get();
    }

    @Override
    protected boolean canSourcesMultiply() {
        return true;
    }

    @Override
    protected void beforeReplacingBlock(IWorld iWorld, BlockPos blockPos, BlockState blockState) {

    }
    @Override
    protected int getSlopeFindDistance(IWorldReader iWorldReader) {
        return 2;
    }

    @Override
    protected int getLevelDecreasePerBlock(IWorldReader iWorldReader) {
        return 3;
    }

    @Override
    public Item getFilledBucket() {
        return zFluid.BLOOD_BUCKET.get();
        // return Items.BUCKET;
    }

    @Override
    protected boolean canDisplace(FluidState fluidState, IBlockReader blockReader, BlockPos blockPos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN;
    }

    @Override
    public int getTickRate(IWorldReader p_205569_1_) {
        return 35;
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0f;
    }

    @Override
    protected BlockState getBlockState(FluidState state) {
        return zFluid.BLOOD_BLOCK.get().getDefaultState().with(FlowingFluidBlock.LEVEL, Integer.valueOf(getLevelFromState(state)));
    }

    @Override
    public boolean isSource(FluidState state) {
        return false;
    }

    @Override
    public int getLevel(FluidState p_207192_1_) {
        return 0;
    }

    @Override
    public boolean isEquivalentTo(Fluid fluid) {
        return fluid == zFluid.BLOOD.get() || fluid == zFluid.BLOOD_FLOWING.get();
    }

    @Override
    protected FluidAttributes createAttributes() {
        return FluidAttributes.builder(new ResourceLocation("minecraft", "block/water_still"), new ResourceLocation("minecraft", "block/water_flow"))
                .density(10000)
                .viscosity(10000)
                .temperature(24)
                .color(0xb30b0b)
                .luminosity(15)
                .translationKey("block.playerblood.blood")
                .build(this);
    }

    public static class Flowing extends FluidBlood {


        @Override
        protected void fillStateContainer(StateContainer.Builder<Fluid, FluidState> p_207184_1_) {
            super.fillStateContainer(p_207184_1_);
            p_207184_1_.add(LEVEL_1_8);
        }

        @Override
        public boolean isSource(FluidState state) {
            return false;
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(FluidBlood.LEVEL_1_8);
        }

        @Override
        protected FluidAttributes createAttributes() {
            return FluidAttributes.builder(new ResourceLocation("minecraft", "block/water_still"), new ResourceLocation("minecraft", "block/water_flow"))
                    .density(10000)
                    .viscosity(10000)
                    .temperature(24)
                    .color(0xb30b0b)
                    .luminosity(15)
                    .translationKey("block.playerblood.blood")
                    .build(this);
        }
    }
    public static class Source extends FluidBlood {

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }

        @Override
        public int getLevel(FluidState state) {
            return 8;
        }

        @Override
        protected FluidAttributes createAttributes() {
            return FluidAttributes.builder(new ResourceLocation("minecraft", "block/water_still"), new ResourceLocation("minecraft", "block/water_flow"))
                    .density(10000)
                    .viscosity(10000)
                    .temperature(24)
                    .color(0xb30b0b)
                    .luminosity(15)
                    .translationKey("block.playerblood.blood")
                    .build(this);
        }

    }
}
