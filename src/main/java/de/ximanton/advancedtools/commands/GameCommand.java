package de.ximanton.advancedtools.commands;

import de.ximanton.advancedtools.Main;
import de.ximanton.advancedtools.enums.Profession;
import de.ximanton.advancedtools.enums.ToolDifficulty;
import de.ximanton.advancedtools.minigame.Minigame;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        Main.getPlugin().getPlayerGames().remove(player);
        Minigame game = new Minigame(Profession.TOOL_SMITHING, ToolDifficulty.NETHERITE, new ItemStack(Material.DIAMOND_PICKAXE), player);
        player.openInventory(game.getGui());
        return false;
    }
}
