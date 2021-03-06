package no.runsafe.azuren;

import no.runsafe.azuren.dungeons.DungeonHandler;
import no.runsafe.azuren.mobs.MobHandler;
import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.framework.api.IServer;
import no.runsafe.framework.features.Events;
import org.bukkit.generator.ChunkGenerator;

public class Plugin extends RunsafeConfigurablePlugin
{
	@Override
	protected void pluginSetup()
	{
		server = getComponent(IServer.class);

		addComponent(Events.class);

		addComponent(Generator.class);
		addComponent(WorldHandler.class);
		addComponent(DungeonHandler.class);
		addComponent(MobHandler.class);
	}

	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id)
	{
		return getComponent(Generator.class);
	}

	public static IServer server;
}
