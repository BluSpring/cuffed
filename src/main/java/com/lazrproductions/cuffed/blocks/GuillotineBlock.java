package com.lazrproductions.cuffed.blocks;

import com.lazrproductions.cuffed.blocks.entity.GuillotineBlockEntity;
import com.lazrproductions.cuffed.init.ModBlockEntities;
import com.lazrproductions.cuffed.init.ModBlocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GuillotineBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    protected static final VoxelShape SHAPE_NS_BASE = Shapes.or(
            Block.box(16 * -0.0625, 0, 16 * 0.375, 16 * 0.125, 16, 16 * 0.625),
            Block.box(16 * 0.875, 0, 16 * 0.375, 16 * 1.0625, 16, 16 * 0.625),
            Block.box(16 * 0.125, 16 * 0.625, 16 * 0.4375, 16 * 0.875, 16 * 0.9375, 16 * 0.5625));

    protected static final VoxelShape SHAPE_EW_BASE = Shapes.or(
            Block.box(16 * 0.375, 0, 16 * 0.875, 16 * 0.625, 16, 16 * 1.0625),
            Block.box(16 * 0.375, 0, 16 * -0.0625, 16 * 0.625, 16, 16 * 0.125),
            Block.box(16 * 0.4375, 16 * 0.625, 16 * 0.125, 16 * 0.5625, 16 * 0.9375, 16 * 0.875));

    public GuillotineBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    
    @Override
    public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos,
            @NotNull CollisionContext ctx) {
        return (state.getValue(FACING) == Direction.NORTH || state.getValue(FACING) == Direction.SOUTH)
                ? SHAPE_NS_BASE
                : SHAPE_EW_BASE;
    }

    @Override
    public VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos,
            @NotNull CollisionContext ctx) {
        return getShape(state, getter, pos, ctx);
    }


    @Override
    @Nullable
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext ctx) {
        BlockPos blockpos = ctx.getClickedPos();
        Level level = ctx.getLevel();
        BlockState stateBelow = level.getBlockState(blockpos.below()); 
        if (stateBelow.is(ModBlocks.PILLORY))
            return this.defaultBlockState().setValue(FACING, stateBelow.getValue(PilloryBlock.FACING));
        else
            return null;
    }

    @Override
    public BlockState rotate(@NotNull BlockState state, @NotNull Rotation r) {
        return state.setValue(FACING, r.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(@NotNull BlockState state, @NotNull Mirror m) {
        return state.rotate(m.getRotation(state.getValue(FACING)));
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = level.getBlockState(blockpos);
        return blockstate.is(ModBlocks.PILLORY);
    }

   @Override
   public BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction,  @NotNull BlockState otherState, @NotNull LevelAccessor level,
           @NotNull BlockPos pos, @NotNull BlockPos otherPos) {
       if(!state.canSurvive(level, pos))
            return Blocks.AIR.defaultBlockState();
       return state;
   }

    @Override
    protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public RenderShape getRenderShape(@NotNull BlockState p_49232_) {
        return RenderShape.MODEL;
    }


    @Override
    public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player,
            @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if(hand == InteractionHand.MAIN_HAND)
            if(!level.isClientSide()) {
                BlockEntity entity = level.getBlockEntity(pos);
                if(entity instanceof GuillotineBlockEntity bl) {
                    bl.interact(level, pos, state);
                    return InteractionResult.SUCCESS;
                }
            }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new GuillotineBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()) {
            return null;
        }
        if(state.is(ModBlocks.GUILLOTINE))
            return createTickerHelper(blockEntityType, ModBlockEntities.GUILLOTINE,
                    (l, pos, s, blockEntity) -> blockEntity.tick(l, pos, s));
        
        return null;
    }
}
