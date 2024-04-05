import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DatabazeRomanu {
	DatabazeRomanu()
	{
		prvkyDatabazeRomanu=new HashMap<String,Roman>();
	}
		
	public boolean setRoman(String jmeno,int rok)
	{
		if (prvkyDatabazeRomanu.put(jmeno,new Roman(jmeno,rok))==null)
			return true;
		else
			return false;
	}
	
	public Roman getRoman(String jmeno) 
	{
		return prvkyDatabazeRomanu.get(jmeno);
	}
	
	public boolean setPrumer(String jmeno, float prumer)
	{
		if (prvkyDatabazeRomanu.get(jmeno)==null)
			return false;
		return prvkyDatabazeRomanu.get(jmeno).setStudijniPrumer(prumer);
	}
	public boolean vymazRoman(String jmeno)
	{
		if (prvkyDatabazeRomanu.remove(jmeno)!=null)
			return true;
		return false;
	}
	public void vypisDatabazeRomanu()
	{
		Set<String> seznamJmen= prvkyDatabazeRomanu.keySet();

		for(String jmeno:seznamJmen)
			System.out.println(jmeno);
	}
	private Map<String,Roman>  prvkyDatabazeRomanu;
}