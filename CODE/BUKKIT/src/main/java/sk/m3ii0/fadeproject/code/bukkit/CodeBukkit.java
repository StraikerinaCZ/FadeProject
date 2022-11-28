package sk.m3ii0.fadeproject.code.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import sk.m3ii0.fadeproject.code.shared.mysql.MySQL;
import sk.m3ii0.fadeproject.code.shared.user.User;

import java.util.UUID;

public class CodeBukkit extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

        Bukkit.getPluginManager().registerEvents(this, this);

        // Load MySQL
        MySQL.prepareConnection();
        Bukkit.getScheduler().runTaskLater(this, MySQL::prepareTables, 40);

    }

    @Override
    public void onDisable() {

        // Close MySQL
        MySQL.closeConnection();

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        User.parseLogin(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        User.parseQuit(uuid, User.getUser(uuid));
    }

}