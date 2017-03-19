package Misc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class Shapes {

	public List<Location> getCylinder(Location location, double radiusX, double radiusZ, int height, boolean filled)
	{
		Vector pos = location.toVector();
		World world = location.getWorld();
		List<Location> blocks = new ArrayList<Location>();
		radiusX += 0.5;
		radiusZ += 0.5;

		if (height == 0)
			return blocks;
		else if (height < 0)
		{
			height = -height;
			pos = pos.subtract(new Vector(0, height, 0));
		}

		if (pos.getBlockY() < 0)
			pos = pos.setY(0);
		else if (pos.getBlockY() + height - 1 > world.getMaxHeight()) 
			height = world.getMaxHeight() - pos.getBlockY() + 1;


		final double invRadiusX = 1 / radiusX;
		final double invRadiusZ = 1 / radiusZ;

		final int ceilRadiusX = (int) Math.ceil(radiusX);
		final int ceilRadiusZ = (int) Math.ceil(radiusZ);

		double nextXn = 0;
		forX: for (int x = 0; x <= ceilRadiusX; ++x)
		{
			final double xn = nextXn;
			nextXn = (x + 1) * invRadiusX;
			double nextZn = 0;
			forZ: for (int z = 0; z <= ceilRadiusZ; ++z) 
			{
				final double zn = nextZn;
				nextZn = (z + 1) * invRadiusZ;

				double distanceSq = lengthSq(xn, zn);
				if (distanceSq > 1)
				{
					if (z == 0) 
					{
						break forX;
					}
					break forZ;
				}

				if (!filled) 
				{
					if (lengthSq(nextXn, zn) <= 1 && lengthSq(xn, nextZn) <= 1)
					{
						continue;
					}
				}

				for (int y = 0; y < height; ++y) 
				{
					blocks.add(pos.add(new Vector(x,y,z)).toLocation(world));
					blocks.add(pos.add(new Vector(-x, y, z)).toLocation(world));
					blocks.add(pos.add(new Vector(x,y,-z)).toLocation(world));
					blocks.add(pos.add(new Vector(-x,y,-z)).toLocation(world));
				}
			}
		}

		return blocks;
	}

	public static List<Location> getSphere(Location location, double radiusX, double radiusY, double radiusZ, boolean filled)
	{
		Vector pos = location.toVector();
		World world = location.getWorld();
		List<Location> blocks = new ArrayList<Location>();

		radiusX += 0.5;
		radiusY += 0.5;
		radiusZ += 0.5;

		final double invRadiusX = 1 / radiusX;
		final double invRadiusY = 1 / radiusY;
		final double invRadiusZ = 1 / radiusZ;

		final int ceilRadiusX = (int) Math.ceil(radiusX);
		final int ceilRadiusY = (int) Math.ceil(radiusY);
		final int ceilRadiusZ = (int) Math.ceil(radiusZ);

		double nextXn = 0;
		forX: for (int x = 0; x <= ceilRadiusX; ++x) 
		{
			final double xn = nextXn;
			nextXn = (x + 1) * invRadiusX;
			double nextYn = 0;
			forY: for (int y = 0; y <= ceilRadiusY; ++y) 
			{
				final double yn = nextYn;
				nextYn = (y + 1) * invRadiusY;
				double nextZn = 0;
				forZ: for (int z = 0; z <= ceilRadiusZ; ++z) 
				{
					final double zn = nextZn;
					nextZn = (z + 1) * invRadiusZ;

					double distanceSq = lengthSq(xn, yn, zn);
					if (distanceSq > 1) 
					{
						if (z == 0) 
						{
							if (y == 0) 
							{
								break forX;
							}
							break forY;
						}
						break forZ;
					}

					if (!filled) 
					{
						if (lengthSq(nextXn, yn, zn) <= 1 && lengthSq(xn, nextYn, zn) <= 1 && lengthSq(xn, yn, nextZn) <= 1) 
						{
							continue;
						}
					}

					blocks.add(pos.add(new Vector(x,y,z)).toLocation(world));
					blocks.add(pos.add(new Vector(-x,y,z)).toLocation(world));
					blocks.add(pos.add(new Vector(x,-y,z)).toLocation(world));
					blocks.add(pos.add(new Vector(x,y,-z)).toLocation(world));
					blocks.add(pos.add(new Vector(-x,-y,z)).toLocation(world));
					blocks.add(pos.add(new Vector(x,-y,-z)).toLocation(world));
					blocks.add(pos.add(new Vector(-x,y,-z)).toLocation(world));
					blocks.add(pos.add(new Vector(-x,-y,-z)).toLocation(world));
				}
			}
		}

		return blocks;
	}

	private static final double lengthSq(double x, double y, double z) 
	{
		return (x * x) + (y * y) + (z * z);
	}

	private static final double lengthSq(double x, double z) 
	{
		return (x * x) + (z * z);
	}

}
