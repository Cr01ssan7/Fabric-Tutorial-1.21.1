package net.xander.tutorialmod.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xander.tutorialmod.item.ModItems;

import java.util.List;
import java.util.Random;

public class MagicBlock extends Block {
    public MagicBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
                                 BlockHitResult hit) {

        world.playSound(player, pos, SoundEvents.BLOCK_BEACON_POWER_SELECT, SoundCategory.BLOCKS, 1f, 1f);
        return ActionResult.SUCCESS;
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if(entity instanceof ItemEntity itemEntity) {

            List<Item> randomItemT1 = List.of(
                    Items.DIAMOND,
                    Items.NETHERITE_SCRAP,
                    Items.SADDLE,
                    Items.ENDER_EYE,
                    Items.EMERALD,
                    Items.GOLDEN_APPLE
            );

            List<Item> randomItemT2 = List.of(
                    Items.DIAMOND_BLOCK,
                    Items.NETHERITE_BLOCK,
                    Items.ENCHANTED_GOLDEN_APPLE
            );

            Item ranIteT1Chosen = randomItemT1.get(new Random().nextInt(randomItemT1.size()));
            Item ranIteT2Chosen = randomItemT2.get(new Random().nextInt(randomItemT2.size()));

            if(itemEntity.getStack().getItem() == ModItems.PINK_GARNET) {
                itemEntity.setStack(new ItemStack(ranIteT1Chosen, itemEntity.getStack().getCount()));
            }

            if(itemEntity.getStack().getItem() == Items.NETHER_STAR) {
                itemEntity.setStack(new ItemStack(ranIteT2Chosen, itemEntity.getStack().getCount()));
            }
        }

        super.onSteppedOn(world, pos, state, entity);
    }
}
