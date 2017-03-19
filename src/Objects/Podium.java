package Objects;

import java.io.Serializable;

import org.bukkit.Location;

public class Podium implements Serializable{
	private static final long serialVersionUID = 1L;
	SerializableLocation location;
	transient boolean occupied;



	public Podium(Location loc)
	{
		this.location = new SerializableLocation(loc);
	}



	public Location getLocation()
	{
		return location.getLocation();
	}



	public boolean isOccupied()
	{
		return occupied;
	}



	public void setOccupied(boolean b)
	{
		this.occupied = b;
	}
}
