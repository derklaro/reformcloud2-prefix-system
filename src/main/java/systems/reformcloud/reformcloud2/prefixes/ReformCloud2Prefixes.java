package systems.reformcloud.reformcloud2.prefixes;

import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import systems.reformcloud.reformcloud2.executor.api.common.ExecutorAPI;
import systems.reformcloud.reformcloud2.executor.api.common.configuration.JsonConfiguration;
import systems.reformcloud.reformcloud2.prefixes.config.ConfigParser;
import systems.reformcloud.reformcloud2.prefixes.listener.BukkitPrefixListener;
import systems.reformcloud.reformcloud2.prefixes.listener.ReformCloudPrefixListener;

import java.util.HashMap;
import java.util.Map;

public class ReformCloud2Prefixes extends JavaPlugin {

    private static ReformCloud2Prefixes instance;

    private String chatFormat;

    private Map<String, String> prefixGetter;

    @Override
    public void onLoad() {
        instance = this;

        JsonConfiguration config = ConfigParser.getConfig(this.getDataFolder());
        this.chatFormat = config.getString("chatFormat");
        this.prefixGetter = config.getOrDefault("prefixes", new TypeToken<Map<String, String>>() {
        }.getType(), new HashMap<>());
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new BukkitPrefixListener(), this);
        ExecutorAPI.getInstance().getEventManager().registerListener(new ReformCloudPrefixListener());
    }

    public static ReformCloud2Prefixes getInstance() {
        return instance;
    }

    public Map<String, String> getPrefixGetter() {
        return prefixGetter;
    }

    public String getChatFormat() {
        return chatFormat;
    }
}
