package org.xjcraft.section.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.Plugin;
import org.xjcraft.section.Section;
import org.xjcraft.section.config.Config;
import org.xjcraft.utils.StringUtil;

import java.util.HashMap;

public class CommandListener implements Listener {
    private Section plugin;
    private MessageListener listener;
    boolean shutdown = false;

    public CommandListener(Section plugin, MessageListener listener) {
        this.plugin = plugin;
        this.listener = listener;
    }

    @EventHandler
    public void command(ServerCommandEvent event) {
        if (shutdown) return;
        if (event.getCommand().equals("stop")) {
            event.setCancelled(true);
            shutdown = true;
            for (int i = 0; i < 10; i++) {
                int finalI = 10 - i;
                plugin.getServer().getScheduler().runTaskLater(plugin, () -> boardcast(finalI), i * 20);
            }
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                Plugin plugin = this.plugin.getServer().getPluginManager().getPlugin("XJCraftAudit");
                if (plugin == null) {
                    for (Player player : this.plugin.getServer().getOnlinePlayers()) {
                        listener.quit(player);
                    }
                } else {
                    for (Player player : this.plugin.getServer().getOnlinePlayers()) {
                        listener.join(player, "login");
                    }
                }
            }, 10 * 20);
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "stop");
            }, 12 * 20);
        }
    }

    private void boardcast(int finalI) {
        plugin.getServer().broadcastMessage(StringUtil.applyPlaceHolder(Config.config.getShutdown(), new HashMap<String, String>() {{
            put("count", finalI + "");
        }}));
    }
}
