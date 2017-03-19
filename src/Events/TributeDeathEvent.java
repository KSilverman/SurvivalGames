package Events;


import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import Enums.GameTime;
import Objects.Tribute;

public class TributeDeathEvent extends Event
{


	private static final HandlerList handlers = new HandlerList();

	Tribute tribute;
	Tribute killer;
	GameTime time;



	public TributeDeathEvent(Tribute tribute, Tribute killer, GameTime time)
	{
		this.tribute = tribute;
		this.killer = killer;
		this.time = time;
	}



	/*
	 * (non-Javadoc)
	 * @see org.bukkit.event.Event#getHandlers()
	 */
	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}



	public static HandlerList getHandlerList()
	{
		return handlers;
	}



	/*
	 * Returns the tribute killed
	 */
	public Tribute getTribute()
	{
		return tribute;
	}



	/*
	 * Returns the tribute who killed
	 */
	public Tribute getKiller()
	{
		return killer;
	}



	/*
	 * Returns the game time
	 */
	public GameTime getTime()
	{
		return time;
	}

}
