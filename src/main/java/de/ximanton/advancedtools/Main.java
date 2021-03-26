package de.ximanton.advancedtools;

import de.ximanton.advancedtools.commands.GameCommand;
import de.ximanton.advancedtools.minigame.Minigame;
import de.ximanton.advancedtools.listener.GuiProtector;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class Main extends JavaPlugin {

    private static Main INSTANCE;

    public static Main getPlugin() {
        return INSTANCE;
    }

    private final Map<Player, Minigame> playerGames = new HashMap<>();

    public Main() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        registerCommands();
        registerListener();
    }

    private void registerListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new GuiProtector(), this);
    }

    private void registerCommands() {
        getCommand("game").setExecutor(new GameCommand());
    }

    public Map<Player, Minigame> getPlayerGames() {
        return playerGames;
    }

    public Minigame getPlayerGame(Player player) {
        return playerGames.getOrDefault(player, null);
    }

    public boolean addPlayerGame(Player player, Minigame game) {
        if (playerGames.containsKey(player)) {
            return false;
        } else {
            playerGames.put(player, game);
            return true;
        }
    }

}
