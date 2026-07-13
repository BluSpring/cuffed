package com.lazrproductions.cuffed.blocks;

import com.lazrproductions.cuffed.blocks.entity.ToiletBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ToiletBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;


    protected static final VoxelShape SHAPE_NORTH = Shapes.or(
            Shapes.box(0, 0, 0.75, 1, 1.125, 1),
            Shapes.box(0.3125, 0, 0.3125, 0.6875, 0.25, 0.75),
            Shapes.box(0.125, 0.25, 0.125, 0.875, 0.4375, 0.75));
    protected static final VoxelShape SHAPE_EAST = Shapes.or(
            Shapes.box(0, 0, 0, 0.25, 1.125, 1),
            Shapes.box(0.25, 0, 0.3125, 0.6875, 0.25, 0.6875),
            Shapes.box(0.25, 0.25, 0.125, 0.875, 0.4375, 0.875));
    protected static final VoxelShape SHAPE_SOUTH = Shapes.or(
            Shapes.box(0, 0, 0, 1, 1.125, 0.25),
            Shapes.box(0.3125, 0, 0.25, 0.6875, 0.25, 0.6875),
            Shapes.box(0.125, 0.25, 0.25, 0.875, 0.4375, 0.875));
    protected static final VoxelShape SHAPE_WEST = Shapes.or(
            Shapes.box(0.75, 0, 0, 1, 1.125, 1),
            Shapes.box(0.3125, 0, 0.3125, 0.75, 0.25, 0.6875),
            Shapes.box(0.125, 0.25, 0.125, 0.75, 0.4375, 0.875));

    public ToiletBlock(Properties p) {
        super(p);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }

    protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos,
            @NotNull CollisionContext ctx) {
        switch (state.getValue(FACING)) {
            case NORTH:
                return SHAPE_NORTH;
            case EAST:
                return SHAPE_EAST;
            case SOUTH:
                return SHAPE_SOUTH;
            default:
                return SHAPE_WEST;
        }
    }

    @Override
    public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
            @NotNull Player interacting,
            @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if(!interacting.isCrouching()) {
            if (level.getBlockEntity(pos) instanceof ToiletBlockEntity entity) {
                return entity.use(state, level, pos, interacting, hand, hitResult);
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new ToiletBlockEntity(pos, state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState,
            boolean f) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ToiletBlockEntity toilet) {
            toilet.getDrops(level, pos).forEach((c) -> {
                double d0 = EntityType.ITEM.getWidth();
                double d1 = 0.2D;
                double d2 = d0 / 2.0D;
                double d3 = Math.floor(pos.getX()) + level.random.nextDouble() * d1 + d2;
                double d4 = Math.floor(pos.getY()) + level.random.nextDouble() * d1;
                double d5 = Math.floor(pos.getZ()) + level.random.nextDouble() * d1 + d2;

                ItemEntity e = new ItemEntity(level, d3, d4, d5, c);

                e.setDeltaMovement(
                        level.random.triangle(0.0D, 0.11485000171139836D),
                        level.random.triangle(0.2D, 0.11485000171139836D),
                        level.random.triangle(0.0D, 0.11485000171139836D));
                e.setDefaultPickUpDelay();

                level.addFreshEntity(e);
            });
        }

        super.onRemove(state, level, pos, newState, f);
        
    }

    public RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos,
            @NotNull PathComputationType path) {
        return false;
    }

    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @SuppressWarnings("deprecation")
    public FluidState getFluidState(@NotNull BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    @Deprecated
    public BlockState updateShape(@NotNull BlockState blockState, @NotNull Direction direction,
            @NotNull BlockState facingState,
            @NotNull LevelAccessor levelAccessor, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }

        return super.updateShape(blockState, direction, facingState, levelAccessor, currentPos, facingPos);
    }
}
