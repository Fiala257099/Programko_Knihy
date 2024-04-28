import java.util.Scanner;

public class main {
    public static void main(String[] args) throws Exception {
    Scanner sc=new Scanner(System.in);
		DatabazeRomanu mojeDatabazeRomanu=new DatabazeRomanu();
        DatabazeUcebnic mojeDatabazeUcebnic=new DatabazeUcebnic();
		int idx;
		float prumer;
		int volba;
		String jmeno;
		String autor;
		boolean stav_dostupnosti;
		int rok;
		boolean run=true;
        while(run)
        {
            System.out.println("Vyberte pozadovanou knihovnu: ");
            System.out.println("1 .. Pro Upravu/Pridani Ucebnice ");
            System.out.println("2 .. Pro Upravu/Pridani Romanu ");

            volba=pouzeCelaCisla(sc);
            if(volba = 1)
        {
		while(run)
		{
			System.out.println("Vyberte pozadovanou cinnost:");
			System.out.println("1 .. Vlozeni noveho Romanu");
			System.out.println("2 .. Uprava Romanu");
			System.out.println("3 .. Odstraneni Romanu");
			System.out.println("4 .. Výpis Románů ");
			System.out.println("5 .. Vyhledání Románu ");
			System.out.println("6 .. Pro vrácení do nabídky");
			
			volba=pouzeCelaCisla(sc);
			switch(volba)
			{
				case 1:
					System.out.println("Zadejte název nového Románu");
					jmeno=sc.next();
					System.out.println("Zadejte Autora");
					autor=sc.next();
					System.out.println("Zadejte stav dostupnosti ve formátu 1 pro dostupný, 0 pro nedostupný");
					stav_dostupnosti=sc.next();
					if 
					{
						stav_dostupnosti==1
					}
					rok=Test.pouzeCelaCisla(sc);				
					if (!mojeDatabaze.setStudent(jmeno,rok))
						System.out.println("Student v databazi jiz existuje");
					break;
				case 2:
					System.out.println("Zadejte název knihy");
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
        if(volba = 2)
        {
            while(run)
		{
			System.out.println("Vyberte pozadovanou cinnost:");
			System.out.println("1 .. Vlozeni nove knihy");
			System.out.println("2 .. Uprava knihy");
			System.out.println("3 .. Odstraneni knihy");
			System.out.println("4 .. Výpis knih ");
			System.out.println("5 .. Vyhledání knihy ");
			System.out.println("6 .. Ukonceni aplikace");
			
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
        else if()
        {
            System.out.println("Nespravna volba, Prosim zadejte znovu");
        }
	}

}

}