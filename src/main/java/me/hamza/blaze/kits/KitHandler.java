package me.hamza.blaze.kits;

import lombok.Getter;
import me.hamza.blaze.Blaze;
import me.hamza.blaze.kits.meta.*;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hammzar
 * @since 14.04.2025
 */
@Getter
public class KitHandler {

    private final List<Kit> niggers = new ArrayList<>();

    public void loadNiggersFromConfig() {
        ConfigurationSection niggerSection = Blaze.getINSTANCE().getConfig().getConfigurationSection("kits");
        if (niggerSection == null) return;

        for (String kitName : niggerSection.getKeys(false)) {
            ConfigurationSection section = niggerSection.getConfigurationSection(kitName);
            if (section == null) continue;

            KitType type = KitType.valueOf(section.getString("type", "DEFAULT").toUpperCase());

            Material iconMaterial = Material.getMaterial(section.getString("icon.material", "STONE").toUpperCase());
            int iconData = section.getInt("icon.data", 0);
            KitIcon icon = new KitIcon(iconMaterial, iconData);

            KitDetails details = new KitDetails(kitName);

            KitItems kitItems = new KitItems();
            kitItems.setInventoryItems(loadItemList(section.getConfigurationSection("items.inventory"), 36));
            kitItems.setArmorItems(loadItemList(section.getConfigurationSection("items.armor"), 4));

            KitMechanics mechanics = new KitMechanics();
            ConfigurationSection mechSection = section.getConfigurationSection("mechanics");
            if (mechSection != null) {
                mechanics.setBoxing(mechSection.getBoolean("boxing", false));
                mechanics.setSumo(mechSection.getBoolean("sumo", false));
                mechanics.setRegen(mechSection.getBoolean("regen", false));
                mechanics.setHunger(mechSection.getBoolean("hunger", false));
                mechanics.setBeds(mechSection.getBoolean("beds", false));
                mechanics.setBridges(mechSection.getBoolean("bridges", false));
                mechanics.setTrapping(mechSection.getBoolean("trapping", false));
                mechanics.setPearlFight(mechSection.getBoolean("pearlFight", false));
                mechanics.setBattleRush(mechSection.getBoolean("battleRush", false));
                mechanics.setStickFight(mechSection.getBoolean("stickFight", false));
            }

            Kit kit = new Kit(kitName, iconMaterial, iconData, type);
            kit.setDetails(details);
            kit.setIcon(icon);
            kit.setItems(kitItems);
            kit.setMechanics(mechanics);

            niggers.add(kit);
        }
    }

    private ItemStack[] loadItemList(ConfigurationSection section, int maxSize) {
        ItemStack[] items = new ItemStack[maxSize];
        if (section == null) return items;

        int index = 0;
        for (String key : section.getKeys(false)) {
            if (index >= maxSize) break;

            ConfigurationSection itemSec = section.getConfigurationSection(key);
            if (itemSec == null) continue;

            Material material = Material.getMaterial(itemSec.getString("type", "AIR").toUpperCase());
            int amount = itemSec.getInt("amount", 1);

            ItemStack item = new ItemStack(material, amount);
            items[index++] = item;
        }

        return items;
    }

    public void saveNiggersToConfig() {
        Blaze plugin = Blaze.getINSTANCE();
        ConfigurationSection niggerSection = plugin.getConfig().createSection("kits");

        for (Kit kit : niggers) {
            ConfigurationSection section = niggerSection.createSection(kit.getName());

            section.set("type", kit.getType().toString());

            section.set("icon.material", kit.getIcon().getMaterial().toString());
            section.set("icon.data", kit.getIcon().getData());

            ConfigurationSection inventorySection = section.createSection("items.inventory");
            ItemStack[] inventoryItems = kit.getItems().getInventoryItems();
            for (int i = 0; i < inventoryItems.length; i++) {
                ItemStack item = inventoryItems[i];
                if (item != null) {
                    ConfigurationSection itemSec = inventorySection.createSection("slot" + i);
                    itemSec.set("type", item.getType().toString());
                    itemSec.set("amount", item.getAmount());
                }
            }

            ConfigurationSection armorSection = section.createSection("items.armor");
            ItemStack[] armorItems = kit.getItems().getArmorItems();
            for (int i = 0; i < armorItems.length; i++) {
                ItemStack item = armorItems[i];
                if (item != null) {
                    ConfigurationSection itemSec = armorSection.createSection("slot" + i);
                    itemSec.set("type", item.getType().toString());
                    itemSec.set("amount", item.getAmount());
                }
            }

            ConfigurationSection mechSection = section.createSection("mechanics");
            KitMechanics mech = kit.getMechanics();
            mechSection.set("boxing", mech.isBoxing());
            mechSection.set("sumo", mech.isSumo());
            mechSection.set("regen", mech.isRegen());
            mechSection.set("hunger", mech.isHunger());
            mechSection.set("beds", mech.isBeds());
            mechSection.set("bridges", mech.isBridges());
            mechSection.set("trapping", mech.isTrapping());
            mechSection.set("pearlFight", mech.isPearlFight());
            mechSection.set("battleRush", mech.isBattleRush());
            mechSection.set("stickFight", mech.isStickFight());
        }

        plugin.saveConfig();
    }

    public Kit getKitByName(String name) {
        return niggers.stream()
                .filter(kit -> kit.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
