package owlbe.textureMySkulls;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import static owlbe.textureMySkulls.TextureMySkulls.instance;

public final class MainCommand {

	public static void register(LifecycleEventManager<?> manager) {
		manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
			LiteralArgumentBuilder<CommandSourceStack> command = Commands.literal("texturemyskulls")
					.requires(context -> context.getSender().hasPermission("texturemyskulls.command"))
					.then(Commands.literal("reload")
							.executes(context -> {
								instance.reloadConfig();
								Component message = MiniMessage.miniMessage().deserialize("Successfully reloaded the configuration.");
								context.getSource().getSender().sendMessage(message);
								return Command.SINGLE_SUCCESS;
							}));

			event.registrar().register(command.build());
		});
	}

}
