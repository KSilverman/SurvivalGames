package Objects;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SerializableLocation implements Serializable{

	private static final long serialVersionUID = 1L;
	double x;
	double y;
	double z;
	String world;
	float pitch;
	float yaw;



	public SerializableLocation(Location loc)
	{
		this.x = loc.getX();
		this.y = loc.getY();
		this.z = loc.getZ();
		this.world = loc.getWorld().getName();
		this.pitch = loc.getPitch();
		this.yaw = loc.getYaw();
	}



	public Location getLocation()
	{
		Location loc = new Location(Bukkit.getServer().getWorld(world), x, y, z);
		loc.setPitch(pitch);
		loc.setYaw(yaw);
		return loc;
	}
}
