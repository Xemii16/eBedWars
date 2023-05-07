package com.yecraft.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.yecraft.bedwars.BedWars;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BedWarsMessage implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) return;

        ByteArrayDataInput input = ByteStreams.newDataInput(message);
        String subchannel = input.readUTF();
    }

    public void connect(Player player, String server){
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("connect");
        output.writeUTF(server);

        player.sendPluginMessage(BedWars.getInstance(), "BungeeCord", output.toByteArray());
    }

}
