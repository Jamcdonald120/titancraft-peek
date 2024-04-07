package de.maxhenkel.peek.events;

import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.data.DataStore;
import de.maxhenkel.peek.tooltips.ContainerTooltip;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;

import java.util.Optional;

public class TooltipImageEvents {

    private static final String ITEMS = "Items";

    public static Optional<TooltipComponent> getTooltipImage(ItemStack stack, Block block) {
        if (block instanceof ShulkerBoxBlock) {
            return getShulkerBoxTooltipImage(stack);
        } else if (block instanceof EnderChestBlock) {
            return getEnderChestTooltipImage();
        }

        return Optional.empty();
    }

    private static Optional<TooltipComponent> getEnderChestTooltipImage() {

        if (!Peek.CONFIG.peekEnderChests.get()) {
            return Optional.empty();
        }

        if (DataStore.enderChestInventory == null) {
            return Optional.empty();
        }

        return Optional.of(new ContainerTooltip(9, 3, DataStore.enderChestInventory));
    }

    private static Optional<TooltipComponent> getShulkerBoxTooltipImage(ItemStack stack) {
        if (!Peek.CONFIG.peekShulkerBoxes.get()) {
            return Optional.empty();
        }

        CompoundTag blockEntityData = BlockItem.getBlockEntityData(stack);
        if (blockEntityData == null) {
            return Optional.empty();
        }

        if (!blockEntityData.contains(ShulkerBoxBlockEntity.ITEMS_TAG, Tag.TAG_LIST)) {
            return Optional.empty();
        }

        NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(blockEntityData, items);

        if (!Peek.CONFIG.showEmptyContainers.get() && items.stream().allMatch(ItemStack::isEmpty)) {
            return Optional.empty();
        }

        return Optional.of(new ContainerTooltip(9, 3, items));
    }


}
