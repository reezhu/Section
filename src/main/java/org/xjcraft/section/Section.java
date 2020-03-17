package org.xjcraft.section;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.xjcraft.CommonPlugin;
import org.xjcraft.section.listener.MessageListener;

import java.util.Collection;

public final class Section extends CommonPlugin implements PluginMessageListener {
    MessageListener listener;
    @Override
    public void onEnable() {
        // Plugin startup logic
        loadConfigs();

        listener = new MessageListener(this);
        getServer().getPluginManager().registerEvents(listener, this);
        registerCommand(listener);
        getCommand("section").setTabCompleter(listener);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Plugin plugin = this.getServer().getPluginManager().getPlugin("XJCraftAudit");
        Collection<? extends Player> players = getServer().getOnlinePlayers();
        if (plugin == null) {
            for (Player player : players) {
                listener.quit(player);
            }
        } else {
            for (Player player : players) {
                listener.join(player, "login");
            }
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
//        if (!channel.equals("BungeeCord")) {
//            return;
//        }
//        ByteArrayDataInput in = ByteStreams.newDataInput(message);
//        String subchannel = in.readUTF();
//        if (subchannel.equals("SomeSubChannel")) {
//            // Use the code sample in the 'Response' sections below to read
//            // the data.
//        }

    }
}
