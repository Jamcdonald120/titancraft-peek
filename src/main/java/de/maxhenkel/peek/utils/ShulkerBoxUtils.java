package de.maxhenkel.peek.utils;

import de.maxhenkel.peek.interfaces.PeekItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;

import javax.annotation.Nullable;
import java.util.regex.Matcher;

public class ShulkerBoxUtils {

    public static NonNullList<ItemStack> getItems(ItemStack stack) {
        NonNullList<ItemStack> items = NonNullList.withSize(ShulkerBoxBlockEntity.CONTAINER_SIZE, ItemStack.EMPTY);
        ItemContainerContents contents = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        contents.copyInto(items);
        return items;
    }

    @Nullable
    public static Component getCustomName(ItemStack stack) {
        Component customName = stack.get(DataComponents.CUSTOM_NAME);
        if (customName != null) {
            return ((PeekItemStack) (Object) stack).peek$getRealHoverName();
        }
        return null;
    }

    @Nullable
    public static ItemStack getBulkItem(NonNullList<ItemStack> contents) {
        boolean exactSame = true;
        ItemStack renderItem = null;
        for (ItemStack itemStack : contents) {
            if (itemStack.isEmpty()) {
                continue;
            }
            if (renderItem == null) {
                renderItem = itemStack;
                continue;
            }
            if (exactSame && ItemStack.isSameItemSameComponents(renderItem, itemStack)) {
                continue;
            }
            if (ItemStack.isSameItem(renderItem, itemStack)) {
                exactSame = false;
            } else {
                return null;
            }
        }
        return exactSame ? renderItem : new ItemStack(renderItem.getItem());
    }

    public static String getStringFromComponent(@Nullable Component component) {
        if (component == null) {
            return null;
        }
        return component.getString();
    }

    @Nullable
    public static Item byId(String id) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return null;
        }
        ResourceLocation resourceLocation = ResourceLocation.tryParse(id);
        if (resourceLocation == null) {
            return null;
        }

        return level.registryAccess().lookupOrThrow(Registries.ITEM).get(resourceLocation).map(Holder.Reference::value).orElse(null);

    }

    public static Component cleanName(Component component) {
        Matcher matcher = ShulkerHintData.DATA_PATTERN.matcher(component.getString());
        if (matcher.find()) {
            String replaced = matcher.replaceAll("").trim();
            return replaced.isEmpty() ? Component.empty() : Component.literal(replaced);
        }
        return component;
    }

    public static boolean isShulkerBox(ItemStack stack) {
        return stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock() instanceof ShulkerBoxBlock;
    }

}
