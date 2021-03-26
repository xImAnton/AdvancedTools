package de.ximanton.advancedtools.minigame;

import org.bukkit.scheduler.BukkitRunnable;

public class GameRunnable extends BukkitRunnable {

    private final Minigame game;

    @Override
    public void run() {
        game.updateScreen();
        game.setUncounted();
        game.sequenceTick();
    }

    public GameRunnable(Minigame game) {
        this.game = game;
    }
}
