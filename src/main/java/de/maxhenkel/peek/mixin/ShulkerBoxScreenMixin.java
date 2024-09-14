package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.utils.ShulkerBoxUtils;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ShulkerBoxScreen;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBoxScreen.class)
public abstract class ShulkerBoxScreenMixin extends AbstractContainerScreen<ShulkerBoxMenu> {

    public ShulkerBoxScreenMixin(ShulkerBoxMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void init(ShulkerBoxMenu shulkerBoxMenu, Inventory inventory, Component component, CallbackInfo ci) {
        if (Peek.CONFIG.hideShulkerBoxDataStrings.get()) {
            title = ShulkerBoxUtils.cleanName(title);
        }

        if (!Peek.CONFIG.showShulkerBoxItemHint.get() && !Peek.CONFIG.showShulkerBoxBlockHint.get()) {
            return;
        }

        if (!Peek.CONFIG.showShulkerBoxBlockHint.get()) {
            return;
        }

        if (!(shulkerBoxMenu.container instanceof ShulkerBoxBlockEntity shulkerBoxBlockEntity)) {
            return;
        }

        if (component.getContents() instanceof TranslatableContents translatableContents) {
            if (translatableContents.getKey().equals("container.shulkerBox")) {
                return;
            }
        }

        DataComponentMap components = shulkerBoxBlockEntity.collectComponents();
        DataComponentMap newComponents = DataComponentMap
                .builder()
                .addAll(components)
                .set(DataComponents.CUSTOM_NAME, component)
                .build();
        shulkerBoxBlockEntity.setComponents(newComponents);
    }
}
