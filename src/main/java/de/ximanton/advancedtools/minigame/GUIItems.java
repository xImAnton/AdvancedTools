package de.ximanton.advancedtools.minigame;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class GUIItems {

    public static ItemStack getPlaceholderItem() {
        ItemStack empty = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = empty.getItemMeta();
        meta.setDisplayName(" ");
        empty.setItemMeta(meta);
        return empty;
    }

    public static ItemStack getCloseGuiItem() {
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta meta = close.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Close");
        close.setItemMeta(meta);
        return close;
    }

    public static ItemStack getClickTypeItem(ClickType clickType) {
        ItemStack item;
        ItemMeta meta;
        switch (clickType) {
            case LEFT:
                item = new ItemStack(Material.LIGHT_BLUE_WOOL);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.YELLOW + ChatColor.UNDERLINE.toString() + "Linksklick");
                item.setItemMeta(meta);
                return item;
            case RIGHT:
                item = new ItemStack(Material.PINK_WOOL);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.YELLOW + ChatColor.UNDERLINE.toString() + "Rechtsklick");
                item.setItemMeta(meta);
                return item;
            case MIDDLE:
                item = new ItemStack(Material.YELLOW_WOOL);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.YELLOW + ChatColor.UNDERLINE.toString() + "Mausrad");
                item.setItemMeta(meta);
                return item;
        }
        return null;
    }

    public static ItemStack getStatusItem(int state) {
        ItemStack item;
        ItemMeta meta;
        switch (state) {
            case 3:
                item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.RED + "3");
                item.setItemMeta(meta);
                break;
            case 2:
                item = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.YELLOW + "2");
                item.setItemMeta(meta);
                break;
            case 1:
                item = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.GREEN + "1");
                item.setItemMeta(meta);
                break;
            default:
                item = null;
        }
        return item;
    }

    public static ItemStack emptySlot() {
        ItemStack item = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getCursorItem() {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Click");
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack rightWrongItem(boolean right) {
        ItemStack item;
        ItemMeta meta;
        if (right) {
            item = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
            meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "Richtig");
        } else {
            item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.RED + "Falsch");
        }
        item.setItemMeta(meta);
        return item;
    }

}
