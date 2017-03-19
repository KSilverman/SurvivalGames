package Events;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class EVT_Ping implements Listener
{


	@EventHandler
	public void onPing(ServerListPingEvent e)
	{
		switch (SurvivalGames.getInstance().getGame().getTime())
		{
		case DEATHMATCH:
			e.setMotd("§4§lDEATHMATCH");
			break;
		case GAME:
			e.setMotd("§a§lIN PROGRESS");
			break;
		case LOBBY:
			e.setMotd("§b§lJOIN NOW");
			break;
		case PREGAME:
			e.setMotd("§e§lPREGAME");
			break;
		case RESTARTING:
			e.setMotd("§5§lRESTARTING");
			break;
		default:
			break;
		}
	}
}
