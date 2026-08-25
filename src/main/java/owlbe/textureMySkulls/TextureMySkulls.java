package owlbe.textureMySkulls;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class TextureMySkulls extends JavaPlugin {

	public static JavaPlugin instance;
	int pluginId = 33634;

	@Override
	public void onLoad() {
		instance = this;
		PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
		PacketEvents.getAPI().load();
	}


	@Override
	public void onEnable() {
		PacketEvents.getAPI().init();
		PacketEvents.getAPI().getEventManager().registerListener(new Listener(), PacketListenerPriority.HIGHEST);
		MainCommand.register(getLifecycleManager());
		saveDefaultConfig();
		new Metrics(this, pluginId);
		getLogger().fine("Plugin enabled successfully!");
	}

	@Override
	public void onDisable() {
		PacketEvents.getAPI().terminate();
	}

}
