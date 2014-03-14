package no.runsafe.azuren.dungeons;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.event.player.IPlayerRightClickBlock;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.WorldBlockEffect;
import no.runsafe.framework.minecraft.WorldBlockEffectType;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

public class DungeonHandler implements IPlayerRightClickBlock
{
	public DungeonHandler(IScheduler scheduler)
	{
		this.scheduler = scheduler;
		sparkEffect = new WorldBlockEffect(WorldBlockEffectType.BLOCK_DUST, Item.BuildingBlock.StainedClay.Green);
	}

	@Override
	public boolean OnPlayerRightClick(IPlayer player, RunsafeMeta usingItem, final IBlock targetBlock)
	{
		if (targetBlock.is(Item.Decoration.EnderPortalFrame) && usingItem.is(Item.Miscellaneous.EyeOfEnder))
		{
			final ILocation effectLocation = targetBlock.getLocation();
			effectLocation.offset(0.5, 0.5, 0.5); // Offset to middle of the block.

			for (int i = 0; i < 10; i++)
				playSpark(effectLocation, i);

			scheduler.startSyncTask(new Runnable()
			{
				@Override
				public void run()
				{
					targetBlock.set(Item.Unavailable.Air); // Remove the end frame block
					effectLocation.getWorld().createExplosion(effectLocation, 3, false, false);
				}
			}, 10);
		}
		return true;
	}

	private void playSpark(final ILocation location, int seconds)
	{
		scheduler.startSyncTask(new Runnable()
		{
			@Override
			public void run()
			{
				location.playEffect(sparkEffect, 1F, 100, 30);
			}
		}, seconds);
	}

	private final WorldBlockEffect sparkEffect;
	private final IScheduler scheduler;
}
