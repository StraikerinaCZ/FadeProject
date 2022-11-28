package sk.m3ii0.fadeproject.code.bukkit.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import sk.m3ii0.fadeproject.code.bukkit.permissions.Permissions;
import sk.m3ii0.fadeproject.code.shared.permissions.Group;
import sk.m3ii0.fadeproject.code.shared.user.User;

import java.util.UUID;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        // Prepare user
        User user = User.parseLogin(e.getPlayer().getUniqueId());
        if (user != null) {
            Group group = Group.getByPlayer(user.getFuid());
            if (group != null) {
                user.setGroup(group);
            }
            Permissions.syncPermissions(user, e.getPlayer());
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

        // Remove user
        UUID uuid = e.getPlayer().getUniqueId();
        User.parseQuit(uuid, User.getUser(uuid));

    }

}
