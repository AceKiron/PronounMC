package dev.mxace.pronounmc.utils.builders;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class MessageBuilder {

    private final ChatColor defaultColor;
    private final List<String> partitions;

    public MessageBuilder(boolean ok) {
        defaultColor = ok ? ChatColor.GREEN : ChatColor.RED;
        partitions = new ArrayList<String>();
    }

    public MessageBuilder addPartition(String partition) {
        partitions.add(partition);
        return this;
    }

    public MessageBuilder addPartition(int partition) {
        partitions.add(Integer.toString(partition));
        return this;
    }

    public MessageBuilder addPartition(double partition) {
        partitions.add(Double.toString(partition));
        return this;
    }

    public MessageBuilder setColor(ChatColor color) {
        partitions.add(color == null ? defaultColor.toString() : color.toString());
        return this;
    }

    public String finish() {
        return defaultColor + "PronounMC: " + String.join("", partitions) + (((String) (partitions.toArray()[partitions.size() - 1])).endsWith(".") ? "" : ".");
    }

}
