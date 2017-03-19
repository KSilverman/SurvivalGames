package Commands;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.entity.Player;

import Commands.SimpleCommand.CommandHandler;

public class CMD_Hub {
	@CommandHandler(name = "hub")
	public void onCommand(Player sender, String[] args)
	{
		SurvivalGames.redirectRequest("hub", sender);
	}
}
