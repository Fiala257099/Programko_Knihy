import java.util.Scanner;


public class Test {

	public static int pouzeCelaCisla(Scanner sc) 
	{
		int cislo = 0;
		try
		{
			cislo = sc.nextInt();
		}
		catch(Exception e)
		{
			System.out.println("Nastala vyjimka typu "+e.toString());
			System.out.println("zadejte prosim cele cislo ");
			sc.nextLine();
			cislo = pouzeCelaCisla(sc);
		}
		return cislo;
	}
	
	public static float pouzeCisla(Scanner sc) 
	{
		float cislo = 0;
		try
		{
			cislo = sc.nextFloat();
		}
		catch(Exception e)
		{
			System.out.println("Nastala vyjimka typu "+e.toString());
			System.out.println("zadejte prosim cislo ");
			sc.nextLine();
			cislo = pouzeCisla(sc);
		}
		return cislo;
	}

	public static void main(String[] args) {	
		Scanner sc=new Scanner(System.in);
		Databaze mojeDatabaze=new Databaze();
		int idx;
		float prumer;
		int volba;
		String jmeno;
		int rok;
		boolean run=true;
		while(run)
		{
			System.out.println("Vyberte pozadovanou cinnost:");
			System.out.println("1 .. Vlozeni nove knihy");
			System.out.println("2 .. Uprava knihy");
			System.out.println("3 .. Odstraneni knihy");
			System.out.println("4 .. Výpis knih ");
			System.out.println("5 .. Vyhledání knihy ");
			System.out.println("6 .. ukonceni aplikace");
			
			volba=pouzeCelaCisla(sc);
			switch(volba)
			{
				case 1:
					System.out.println("Zadejte jmeno studenta, rok narozeni");
					jmeno=sc.next();
					rok=Test.pouzeCelaCisla(sc);				
					if (!mojeDatabaze.setStudent(jmeno,rok))
						System.out.println("Student v databazi jiz existuje");
					break;
				case 2:
					System.out.println("Zadejte jmeno a prumer studenta");
					jmeno=sc.next();
					prumer = pouzeCisla(sc);
					if (!mojeDatabaze.setPrumer(jmeno,prumer))
						System.out.println("Prumer nezadan");
					
					break;
				case 3:
					System.out.println("Zadejte jmeno studenta");
					jmeno=sc.next();
					Student info = null;
					info=mojeDatabaze.getStudent(jmeno);
					if (info!=null)
						System.out.println("Jmeno: " + info.getJmeno() + " rok narozeni: " + info.getRocnik() + " prumer: " + info.getStudijniPrumer());
					else
						System.out.println("Vybrana polozka neexistuje");
					break;
				case 4:
					System.out.println("Zadejte jmeno studenta k odstraneni");
					jmeno=sc.next();
					if (mojeDatabaze.vymazStudenta(jmeno))
						System.out.println(jmeno + " odstranen ");
					else
						System.out.println(jmeno + " neni v databazi ");
					break;
				case 5:
					mojeDatabaze.vypisDatabaze();
					break;
				case 6:
					run=false;
					break;
			}
			
		}
	}

}
