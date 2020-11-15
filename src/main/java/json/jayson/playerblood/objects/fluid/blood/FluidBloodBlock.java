package json.jayson.playerblood.objects.fluid.blood;

import json.jayson.playerblood.registry.zTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

public class FluidBloodBlock extends FlowingFluidBlock {
    public FluidBloodBlock(Supplier<? extends FlowingFluid> p_i230067_1_, Properties p_i230067_2_) {
        super(p_i230067_1_, p_i230067_2_);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        FluidBloodBlockTileEntity tileEntity = (FluidBloodBlockTileEntity) worldIn.getTileEntity(pos);
        if(tileEntity.amount <= 0 || tileEntity.lifetime <= -500) {
            if(worldIn.getBlockState(pos).getFluidState().isSource()) {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
                System.out.println("Blut entfernt --> Lifetime");
            }
        }
        tileEntity.lifetime--;
        super.animateTick(stateIn, worldIn, pos, rand);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return zTileEntity.BLOOD.get().create();
    }
}
