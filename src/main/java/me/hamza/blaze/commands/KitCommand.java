package me.hamza.blaze.commands;

import me.hamza.blaze.kits.Kit;
import me.hamza.blaze.kits.KitHandler;
import me.hamza.blaze.kits.KitType;
import me.hamza.blaze.kits.meta.KitMechanics;
import me.hamza.blaze.Blaze;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Hammzar
 * @since 14.04.2025
 */
public class KitCommand implements CommandExecutor {

    private final KitHandler kitHandler = Blaze.getINSTANCE().getKitHandler();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Usage: /kit <create|setmechanic|seticon|settype|setdescription>");
            return true;
        }

        String sub = args[0].toLowerCase();

        if (sub.equals("create")) {
            if (args.length < 4) {
                player.sendMessage(ChatColor.RED + "Usage: /kit create <name> <material> <type>");
                return true;
            }

            String name = args[1];
            Material material = Material.getMaterial(args[2].toUpperCase());
            if (material == null) {
                player.sendMessage(ChatColor.RED + "Invalid material.");
                return true;
            }

            KitType type;
            try {
                type = KitType.valueOf(args[3].toUpperCase());
            } catch (IllegalArgumentException e) {
                player.sendMessage(ChatColor.RED + "Invalid kit type.");
                return true;
            }

            Kit kit = new Kit(name, material, 0, type);
            kitHandler.getNiggers().add(kit);
            player.sendMessage(ChatColor.GREEN + "Kit '" + name + "' created.");
            return true;
        }

        if (sub.equals("setmechanic")) {
            if (args.length < 4) {
                player.sendMessage(ChatColor.RED + "Usage: /kit setmechanic <kitName> <mechanic> <true/false>");
                return true;
            }

            String kitName = args[1];
            Kit kit = kitHandler.getKitByName(kitName);
            if (kit == null) {
                player.sendMessage(ChatColor.RED + "Kit not found.");
                return true;
            }

            KitMechanics mech = kit.getMechanics();
            String key = args[2].toLowerCase();
            boolean value = Boolean.parseBoolean(args[3]);

            switch (key) {
                case "boxing":
                    mech.setBoxing(value);
                    break;
                case "sumo":
                    mech.setSumo(value);
                    break;
                case "regen":
                    mech.setRegen(value);
                    break;
                case "hunger":
                    mech.setHunger(value);
                    break;
                case "beds":
                    mech.setBeds(value);
                    break;
                case "bridges":
                    mech.setBridges(value);
                    break;
                case "trapping":
                    mech.setTrapping(value);
                    break;
                case "pearlfight":
                    mech.setPearlFight(value);
                    break;
                case "battlerush":
                    mech.setBattleRush(value);
                    break;
                case "stickfight":
                    mech.setStickFight(value);
                    break;
                default:
                    player.sendMessage(ChatColor.RED + "Invalid mechanic.");
                    return true;
            }

            player.sendMessage(ChatColor.GREEN + "Set " + key + " to " + value + " for " + kitName + ".");
            return true;
        }

        if (sub.equals("seticon")) {
            if (args.length < 3) {
                player.sendMessage(ChatColor.RED + "Usage: /kit seticon <kitName> <material>");
                return true;
            }

            Kit kit = kitHandler.getKitByName(args[1]);
            if (kit == null) {
                player.sendMessage(ChatColor.RED + "Kit not found.");
                return true;
            }

            Material material = Material.getMaterial(args[2].toUpperCase());
            if (material == null) {
                player.sendMessage(ChatColor.RED + "Invalid material.");
                return true;
            }

            kit.getIcon().setMaterial(material);
            player.sendMessage(ChatColor.GREEN + "Updated icon for kit " + kit.getName() + " to " + material + ".");
            return true;
        }

        if (sub.equals("settype")) {
            if (args.length < 3) {
                player.sendMessage(ChatColor.RED + "Usage: /kit settype <kitName> <type>");
                return true;
            }

            Kit kit = kitHandler.getKitByName(args[1]);
            if (kit == null) {
                player.sendMessage(ChatColor.RED + "Kit not found.");
                return true;
            }

            KitType type;
            try {
                type = KitType.valueOf(args[2].toUpperCase());
            } catch (IllegalArgumentException e) {
                player.sendMessage(ChatColor.RED + "Invalid kit type.");
                return true;
            }

            kit.setType(type);
            player.sendMessage(ChatColor.GREEN + "Updated type for kit " + kit.getName() + " to " + type + ".");
            return true;
        }

        if (sub.equals("setdescription")) {
            if (args.length < 3) {
                player.sendMessage(ChatColor.RED + "Usage: /kit setdescription <kitName> <description...>");
                return true;
            }

            Kit kit = kitHandler.getKitByName(args[1]);
            if (kit == null) {
                player.sendMessage(ChatColor.RED + "Kit not found.");
                return true;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 2; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }

            kit.getDetails().setDescription(sb.toString().trim());
            player.sendMessage(ChatColor.GREEN + "Updated description for kit " + kit.getName() + ".");
            return true;
        }

        player.sendMessage(ChatColor.RED + "Unknown subcommand.");
        return true;
    }
}
