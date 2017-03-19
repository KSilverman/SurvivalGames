package Objects;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Tribute implements Serializable{
	private static final long serialVersionUID = 1L;
    
    String name;
    int kills;
    int deaths;
    int wins;
    int losses;
    int points;
    int logins;
    int chests;
    int killstreak;
    transient boolean sponsor;
    transient boolean isReady;
    transient boolean hasBet;
    transient boolean spectating;
    transient String bet;
    transient int betAmount;
    transient String viewing;
    
    
    
    public Tribute()
    {
    }
    
    
    
    // ====================================================== \\
    
    
    public Player getPlayer()
    {
        return Bukkit.getServer().getPlayerExact(name);
    }
    
    
    
    public static Tribute getTribute(String name)
    {
        return SurvivalGames.getInstance().getPlayers().get(name);
    }
    
    
    
    public static Tribute getTribute(Player player)
    {
        return SurvivalGames.getInstance().getPlayers().get(player.getName());
    }
    
    
    
    public String getName()
    {
        return name;
    }
    
    
    
    public int getKills()
    {
        return kills;
    }
    
    
    
    public int getDeaths()
    {
        return deaths;
    }
    
    
    
    public int getWins()
    {
        return wins;
    }
    
    
    
    public int getLosses()
    {
        return losses;
    }
    
    
    
    public int getPoints()
    {
        return points;
    }
    
    
    
    public int getLogins()
    {
    	return logins;
    }
    
    
    
    public int getChestsOpened()
    {
    	return chests;
    }
    
    
    
    public boolean isSponsor()
    {
        return sponsor;
    }
    
    
    
    public boolean hasBet()
    {
        return hasBet;
    }
    
    
    
    public boolean isSpectator()
    {
        return spectating;
    }
    
    
    
    public String getBet()
    {
        return bet;
    }
    
    
    
    public int getBetAmount()
    {
        return betAmount;
    }
    
    
    
    public String getSpectating()
    {
        return viewing;
    }
    
    public int getKillSterak()
    {
    	return killstreak;
    }
    
    
    
    // ====================================================== \\
    
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    
    
    public void setKills(int kills)
    {
        this.kills = kills;
    }
    
    
    
    public void setDeaths(int deaths)
    {
        this.deaths = deaths;
    }
    
    
    
    public void setWins(int wins)
    {
        this.wins = wins;
    }
    
    
    
    public void setLosses(int losses)
    {
        this.losses = losses;
    }
    
    
    
    public void setPoints(int points)
    {
        this.points = points;
    }
    
    
    
    public void setLogins(int log)
    {
    	this.logins = log;
    }
    
    
    
    public void setChestsOpened(int c)
    {
    	this.chests = c;
    }
    
    
    
    public void setHasBet(boolean b)
    {
        this.hasBet = b;
    }
    
    
    
    public void setSpectator(boolean b)
    {
        this.spectating = b;
    }
    
    
    
    public void setBet(String name, int bet2)
    {
        this.bet = name;
        this.betAmount = bet2;
    }
    
    public void setKillStreak(int ks)
    {
    	this.killstreak = ks;
    }
    
    
    
    public void save()
    {
        try
        {
            File f = new File(SurvivalGames.getInstance().getDataFolder() + "/players/" + name + ".dat");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(this);
            oos.close();
            System.out.println("Saved player data: " + this.getName());
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            System.out.println("Error saving DAT player: " + this.getName() + "!");
        }
    }
    
    
    
    public void setSpectating(String name2)
    {
        this.viewing = name2;
    }
}
