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
		int typ;
		String nazev_knihy;
		String jmeno_autora;
		int rok_vydani;
		boolean stav_dostupnosti;
		int vhodny_rocnik;
		String zanr;	
		boolean run=true;
		while(run)
		{
			System.out.println("Vyberte pozadovanou cinnost:");
			System.out.println("1 ... Vlozeni nove knihy");
			System.out.println("2 ... Uprava knihy");
			System.out.println("3 ... Odstraneni knihy");
			System.out.println("4 ... Výpis knih ");
			System.out.println("5 ... Vyhledání knihy ");
			System.out.println("6 ... Vypis knih autora: ");
			System.out.println("7 ... Výpis knih podle žánru ");
			System.out.println("8 ... Výpis půjčených knih: ");
			System.out.println("9 ... Uložení knih do souboru: ");
			System.out.println("10 .. Vypsání knih ze souboru: ");
			System.out.println("11 .. Změna dostupnosti: ");
			System.out.println("12 .. Ukončení aplikace");
			
			int volba=pouzeCelaCisla(sc);
			switch(volba)
			{
				case 1:
					System.out.println("Zadej typ knihy, Název, Autora, Rok vydání");
					typ=Test.pouzeCelaCisla(sc);
					nazev_knihy = sc.next();
					jmeno_autora = sc.next();
					rok_vydani=Test.pouzeCelaCisla(sc);
					stav_dostupnosti = true;	
					
					switch (typ) {
						case 1:
						System.out.println("Zadej žánr:");
						zanr = sc.next();
						mojeDatabaze.pridejRoman(nazev_knihy, jmeno_autora, rok_vydani, stav_dostupnosti, zanr);
						System.out.println("Úspěšně přidán.");
						break;
				
					case 2:
						System.out.println("Zadej vhodný ročník:");
						vhodny_rocnik = Test.pouzeCelaCisla(sc);
						mojeDatabaze.pridejUcebnici(nazev_knihy, jmeno_autora, rok_vydani, stav_dostupnosti, vhodny_rocnik);
						System.out.println("Úspěšně přidána.");
						break;
					}
					break;
			
				case 2:
				System.out.println("Zadejte název knihy:");
				nazev_knihy = sc.next();
			
				System.out.println("Vyberte, kterou vlastnost knihy chcete upravit:");
				System.out.println("1 ... Název knihy");
				System.out.println("2 ... Autor knihy");
				System.out.println("3 ... Rok vydání knihy");
				System.out.println("4 ... Stav dostupnosti knihy");
				
				int volbaUpravy = pouzeCelaCisla(sc);
				switch (volbaUpravy) {
					case 1:
						System.out.println("Zadejte nový název knihy:");
						String novyNazev = sc.next();
						mojeDatabaze.zmenaNazvu(nazev_knihy, novyNazev);
						break;
					case 2:
						System.out.println("Zadejte nového autora knihy:");
						String novyAutor = sc.next();
						mojeDatabaze.zmenaAutora(nazev_knihy, novyAutor);
						break;
					case 3:
						System.out.println("Zadejte nový rok vydání knihy:");
						int novyRokVydani = pouzeCelaCisla(sc);
						mojeDatabaze.zmenaRokuVydani(nazev_knihy, novyRokVydani);
						break;
					case 4:
						System.out.println("Zadejte nový stav dostupnosti knihy (false pro vypůjčenou, true pro dostupnou):");
						boolean novyStav = sc.nextBoolean();
						mojeDatabaze.zmenaStavuDostupnosti(nazev_knihy, novyStav);
						System.out.println("Stav dostupnosti knihy úspěšně aktualizován.");
						break;
					default:
						System.out.println("Neplatná volba.");
						break;
				}
				break;

				case 3:
    				System.out.println("Zadejte název knihy, kterou chcete smazat:");
    				nazev_knihy = sc.next();
    				mojeDatabaze.odstranitKnihu(nazev_knihy);
			    break;

			
				case 4:
					mojeDatabaze.vypisVsechnyKnihy();
					break;

				case 11:
					System.out.println("Zadejte název knihy:");
					nazev_knihy = sc.next();
					System.out.println("Zadejte stav dostupnosti knihy (false pro vypůjčenou, true pro dostupnou):");
					boolean stav = sc.nextBoolean();
					mojeDatabaze.setStavDostupnosti(nazev_knihy, stav);
					break;
			}
			
		}
	}

}
