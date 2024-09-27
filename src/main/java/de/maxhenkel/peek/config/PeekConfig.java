package de.maxhenkel.peek.config;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.configbuilder.entry.ConfigEntry;

import java.math.BigInteger;

public class PeekConfig {

    public final ConfigEntry<Boolean> showEmptyContainers;

    public final ConfigEntry<Boolean> peekShulkerBoxes;
    public final ConfigEntry<Boolean> peekEnderChests;

    public final ConfigEntry<Boolean> showShulkerBoxItemHint;
    public final ConfigEntry<Boolean> showShulkerBoxBlockHint;
    public final ConfigEntry<Boolean> showShulkerBoxItems;
    public final ConfigEntry<Boolean> showShulkerBoxLabels;
    public final ConfigEntry<Boolean> useShulkerBoxDataStrings;
    public final ConfigEntry<Boolean> useShulkerBoxItemNames;
    public final ConfigEntry<Boolean> hideShulkerBoxDataStrings;

    public final ConfigEntry<Boolean> showDecoratedPotHint;

    public final ConfigEntry<Boolean> showHud;
    public final ConfigEntry<String> hudBackgroundColor;
    public final ConfigEntry<String> hudTextColor;

    public final int hudBackgroundColorValue;
    public final int hudTextColorValue;

    public PeekConfig(ConfigBuilder builder) {
        showEmptyContainers = builder.booleanEntry(
                "show_empty_containers",
                false,
                "If empty containers should show the item slots in the tooltip"
        );

        peekShulkerBoxes = builder.booleanEntry(
                "peek_shulker_boxes",
                true,
                "Displays the items inside shulker boxes"
        );
        peekEnderChests = builder.booleanEntry(
                "peek_ender_chests",
                false,
                "Displays the cached contents of ender chests"
        );

        showShulkerBoxItemHint = builder.booleanEntry(
                "show_shulker_box_item_hint",
                true,
                "If this is enabled, the mod will show additional information about the items in shulker boxes when in item form"
        );
        showShulkerBoxBlockHint = builder.booleanEntry(
                "show_shulker_box_block_hint",
                true,
                "If this is enabled, the mod will show additional information about the shulker box block on the lid of the shulker box"
        );
        showShulkerBoxItems = builder.booleanEntry(
                "show_shulker_box_items",
                true,
                "If this is enabled, the mod will show the item on the shulker box lid if it only contains one type of item"
        );
        showShulkerBoxLabels = builder.booleanEntry(
                "show_shulker_box_labels",
                true,
                "If this is enabled, the mod will show the custom name of the shulker box on the lid of the shulker box"
        );
        useShulkerBoxDataStrings = builder.booleanEntry(
                "use_shulker_box_data_strings",
                false,
                "If this is enabled, the mod will use the data strings to determine what to show on the shulker box lid",
                "Note that this setting is experimental and subject to change"
        );
        useShulkerBoxItemNames = builder.booleanEntry(
                "use_shulker_box_item_names",
                false,
                "If this is enabled, the mod will use item names to determine what to show on the shulker box lid",
                "Note that this setting is experimental and subject to change"
        );
        hideShulkerBoxDataStrings = builder.booleanEntry(
                "hide_shulker_box_data_strings",
                true,
                "If this is enabled, the mod will hide the data strings on tooltips and GUIs"
        );
        showDecoratedPotHint = builder.booleanEntry(
                "show_decorated_pot_hint",
                false,
                "If this is enabled, decorated pots will show the contained item and amount",
                "If you are playing on a player, you need to have the mod installed on the server with the config option 'send_decorated_pot_data_to_client' enabled",
                "If you are on singleplayer, the config option 'send_decorated_pot_data_to_client' must be enabled"
        );
        
        showHud = builder.booleanEntry(
                "show_hud",
                false,
                "If the HUD should be shown",
                "NOTE: This option is experimental"
        );
        hudBackgroundColor = builder.stringEntry(
                "hud_background_color",
                "AA000000",
                "The background color of the HUD in ARGB hex"
        );
        hudTextColor = builder.stringEntry(
                "hud_text_color",
                "FFFFFFFF",
                "The color of the HUD text in ARGB hex"
        );

        hudBackgroundColorValue = new BigInteger(hudBackgroundColor.get(), 16).intValue();
        hudTextColorValue = new BigInteger(hudTextColor.get(), 16).intValue();
    }

}
