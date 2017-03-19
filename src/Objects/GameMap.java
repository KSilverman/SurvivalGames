package Objects;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.BajanAmerican.SurvivalGames.SurvivalGames;

import org.bukkit.Location;

public class GameMap implements Serializable{
	private static final long serialVersionUID = 1L;
	String name;
	SerializableLocation location;
	List<Podium> podiums;



	public GameMap()
	{
		this.podiums = new ArrayList<Podium>();
	}



	public static GameMap getMap(String name)
	{
		return SurvivalGames.getInstance().getMaps().get(name);
	}



	public void save()
	{
		try
		{
			File f = new File(SurvivalGames.getInstance().getDataFolder() + "/maps/" + name + ".map");
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(this);
			oos.close();
			System.out.println("Saved map: " + this.getName());
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
			System.out.println("Error saving map: " + this.getName() + "!");
		}
	}



	//=======================================================

	public String getName()
	{
		return name;
	}



	public SerializableLocation getLocation()
	{
		return location;
	}



	public List<Podium> getPodiums()
	{
		return podiums;
	}



	//=======================================================


	public void setName(String name)
	{
		this.name = name;
	}



	public void setLocation(Location loc)
	{
		this.location = new SerializableLocation(loc);
	}
}
