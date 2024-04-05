import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DatabazeUcebnic {
	DatabazeUcebnic()
	{
		prvkyDatabazeUcebnic=new HashMap<String,Ucebnice>();
	}
		
	public boolean setUcebnice(String jmeno,int rok)
	{
		if (prvkyDatabazeUcebnic.put(jmeno,new Ucebnice(jmeno,rok))==null)
			return true;
		else
			return false;
	}
	
	public Ucebnice getUcebnice(String jmeno) 
	{
		return prvkyDatabazeUcebnic.get(jmeno);
	}
	
	public boolean setPrumer(String jmeno, float prumer)
	{
		if (prvkyDatabazeUcebnic.get(jmeno)==null)
			return false;
		return prvkyDatabazeUcebnic.get(jmeno).setStudijniPrumer(prumer);
	}
	public boolean vymazUcebnice(String jmeno)
	{
		if (prvkyDatabazeUcebnic.remove(jmeno)!=null)
			return true;
		return false;
	}
	public void vypisDatabazeUcebnic()
	{
		Set<String> seznamJmen= prvkyDatabazeUcebnic.keySet();

		for(String jmeno:seznamJmen)
			System.out.println(jmeno);
	}
	private Map<String,Ucebnice>  prvkyDatabazeUcebnic;
}