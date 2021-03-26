package de.ximanton.advancedtools.minigame;

import de.ximanton.advancedtools.Main;
import de.ximanton.advancedtools.enums.Profession;
import de.ximanton.advancedtools.enums.ToolDifficulty;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Random;

public class Minigame {

    public static final int[] emptySlots = {0, 1, 2, 3, 5, 6, 7, 8, 18, 19, 20, 21, 23, 24, 25, 26};
    public static final int[] statusSlots = {9, 10, 11, 12, 14, 15, 16, 17};

    public static String getTitle() {
        return ChatColor.AQUA + ChatColor.BOLD.toString() + "ADVANCED TOOL CRAFTING";
    }

    private final Profession type;
    private final ToolDifficulty difficulty;
    private final ItemStack itemToCraft;
    private final Inventory gui = Bukkit.createInventory(null, 27, getTitle());
    private final ArrayList<ClickType> clickSequence;
    private int currentSequenceIndex;
    private int taskId = 0;
    private final Player player;
    private boolean currentClickCounted = false;
    private int score = 0;
    private boolean closed = false;
    private boolean currentClickIsCorrect;

    public Minigame(Profession type, ToolDifficulty difficulty, ItemStack itemToCraft, Player player) {
        this.difficulty = difficulty;
        this.itemToCraft = itemToCraft;
        this.type = type;
        this.player = player;
        this.clickSequence = new ArrayList<>();

        Random r = new Random();

        for (int i = 0; i < difficulty.getClickSequenceLength(); i++) {
            switch (r.nextInt(3)) {
                case 0:
                    clickSequence.add(ClickType.LEFT);
                    break;
                case 1:
                    clickSequence.add(ClickType.RIGHT);
                    break;
                case 2:
                    clickSequence.add(ClickType.MIDDLE);
                    break;
            }
        }

        currentSequenceIndex = -3;

        for (int slot : emptySlots) {
            gui.setItem(slot, GUIItems.getPlaceholderItem());
        }

        gui.setItem(4, itemToCraft);
        gui.setItem(22, GUIItems.getCloseGuiItem());
        gui.setItem(13, GUIItems.getCursorItem());

        Main.getPlugin().addPlayerGame(this.player, this);

        updateScreen();
    }

    public Inventory getGui() {
        return gui;
    }

    public void updateScreen() {
        if (currentSequenceIndex < 0) {
            switch (currentSequenceIndex) {
                case -3:
                    for (int i: statusSlots) {
                        gui.setItem(i, GUIItems.getStatusItem(3));
                    }
                    break;
                case -2:
                    for (int i: statusSlots) {
                        gui.setItem(i, GUIItems.getStatusItem(2));
                    }
                    break;
                case -1:
                    for (int i: statusSlots) {
                        gui.setItem(i, GUIItems.getStatusItem(1));
                    }
                    break;
            }
        } else {
            int index;
            for (int i = 0; i < 4; i++) {
                index = currentSequenceIndex + i;
                if (index >= clickSequence.size()) {
                    gui.clear(12 - i);
                    continue;
                }
                gui.setItem(12 - i, GUIItems.getClickTypeItem(clickSequence.get(index)));
            }

            if (currentSequenceIndex == 0) {
                for (int rwSlot : new int[]{14, 15, 16, 17}) { // clear RW-Items on game start
                    gui.clear(rwSlot);
                }
            } else {
                for (int sSlot = 17; sSlot > 14; sSlot--) {
                    gui.setItem(sSlot, gui.getItem(sSlot - 1)); // move RW-Items
                }
            }
            if (currentSequenceIndex > 1) { // set new RW-Item
                gui.setItem(14, GUIItems.rightWrongItem(currentClickIsCorrect));
            }
            currentClickIsCorrect = false; // If not clicked, count click as incorrect
        }
    }

    public void clicked(ClickType clickType) {
        if (currentSequenceIndex == -3 & taskId == 0) {
            start();
        } else if (currentSequenceIndex < 0 & taskId != 0) {
            return;
        } else {
            if (currentClickCounted) return;
            currentClickCounted = true;
            if (clickType == getCurrentClick()) {
                score++;
                currentClickIsCorrect = true;
            } else {
                currentClickIsCorrect = false;
            }
        }
    }

    private void start() {
        GameRunnable runnable = new GameRunnable(this);
        BukkitTask task = runnable.runTaskTimerAsynchronously(Main.getPlugin(), difficulty.getMsPerClick() / 50, difficulty.getMsPerClick() / 50);
        taskId = task.getTaskId();
    }

    public void sequenceTick() {
        if (currentSequenceIndex < clickSequence.size() + 1) {
            currentSequenceIndex++;
        } else {
            stopTask();
        }
    }

    public ClickType getCurrentClick() {
        return clickSequence.get(currentSequenceIndex - 2);
    }

    public Player getPlayer() {
        return player;
    }

    public void stopTask() {
        Bukkit.getServer().getScheduler().cancelTask(taskId);
        player.sendMessage("Spiel mit " + score + " / " + clickSequence.size() + " Punkten beendet");
    }

    public void setUncounted() {
        currentClickCounted = false;
    }

    public int getScore() {
        return score;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
