package sk.m3ii0.fadeproject.code.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import sk.m3ii0.fadeproject.code.bukkit.listeners.JoinListener;
import sk.m3ii0.fadeproject.code.shared.colors.Hex;
import sk.m3ii0.fadeproject.code.shared.mysql.MySQL;
import sk.m3ii0.fadeproject.code.shared.permissions.Group;

public class CodeBukkit extends JavaPlugin {

    private static Plugin instance;

    @Override
    public void onEnable() {

        // Instance
        instance = this;

        // Load MySQL
        MySQL.prepareConnection();

        Bukkit.getScheduler().runTaskLater(this, () -> {

            // Prepare tables
            MySQL.prepareTables();
            Bukkit.getConsoleSender().sendMessage(
                    Hex.colorize(
                            Hex.PREFIX + "Tables has been prepared!"
                    )
            );

            // Fetch groups
            Group.refreshGroups();
            Bukkit.getConsoleSender().sendMessage(
                    Hex.colorize(
                            Hex.PREFIX + "Groups has been synchronized!"
                    )
            );

            // Load listeners
            Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
            Bukkit.getConsoleSender().sendMessage(
                    Hex.colorize(
                            Hex.PREFIX + "Listeners has been registered!"
                    )
            );

        }, 40);

    }

    @Override
    public void onDisable() {

        // Close MySQL
        MySQL.closeConnection();

    }

    public static Plugin getInstance() {
        return instance;
    }

}