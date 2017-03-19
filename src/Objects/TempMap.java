package Objects;

public class TempMap {
	String name;
	String description;
	String authors;
	String download;



	public TempMap(String name, String description, String authors, String download)
	{
		this.name = name;
		this.authors = authors;
		this.description = description;
		this.download = download;
	}



	public String getName()
	{
		return name;
	}



	public String getDescription()
	{
		return description;
	}



	public String getAuthors()
	{
		return authors;
	}



	public String getDownload()
	{
		return download;
	}
}
