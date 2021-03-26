package de.ximanton.advancedtools.listener;

import de.ximanton.advancedtools.Main;
import de.ximanton.advancedtools.minigame.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GuiProtector implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Minigame game = Main.getPlugin().getPlayerGame(player);
        if (e.getView().getTitle().equals(Minigame.getTitle()) && game != null) {
            e.setCancelled(true);

            int slot = e.getRawSlot();

            if (slot == 22) {
                game.setClosed(true);
                player.closeInventory();
            }
            if (slot == 13) {
                game.clicked(e.getClick());
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        if (Main.getPlugin().getPlayerGames().containsKey(player)) {
            Minigame game = Main.getPlugin().getPlayerGame(player);
            if (!game.isClosed()) {
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> player.openInventory(e.getInventory()), 1);
            } else {
                game.stopTask();
                Main.getPlugin().getPlayerGames().remove(player);
                player.closeInventory();
            }
        }
    }
}
