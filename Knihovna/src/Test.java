import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;



public class Test {
	public static final String[] ZANRY = {"HISTORICKY", "DOBRODRUZNY", "DETEKTIVNI", "FANTASY", "SCI-FI"};

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
    
    	Databaze mojeDatabaze = new Databaze();
		int typ;
		String nazev_knihy;
		String jmeno_autora;
		int rok_vydani;
		boolean stav_dostupnosti;
		int vhodny_rocnik;
		Roman.Zanr zanr;	
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
			System.out.println("12: databaze");
			System.out.println("13 .. Ukončení aplikace");
			
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
						System.out.println("Zadej žánr (historicky, dobrodruzny, detektivni, fantasy, sci-fi): ");
						
						String typzanru = sc.next().toUpperCase();

						if (Arrays.asList(ZANRY).contains(typzanru)) {
							zanr = Roman.Zanr.valueOf(typzanru);
							mojeDatabaze.pridejRoman(nazev_knihy, jmeno_autora, rok_vydani, stav_dostupnosti, zanr);
							System.out.println("Úspěšně přidán.");
						} else {
							System.out.println("Neplatný žánr, zadávejte prosím jednu z možností: historický, dobrodružný, detektivní, fantasy, sci-fi");
						}
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

				case 5: 
					System.out.println("Zadej nazev knihy co chceš vyshledat: ");
					nazev_knihy = sc.next();
					mojeDatabaze.vyhledatKnihu(nazev_knihy);
					break;

				case 6:
					System.out.println("ZAdej jmeno autora pro kterého chceš vyhledat knihy: ");
					jmeno_autora = sc.next();
					mojeDatabaze.vypisKnihAutora(jmeno_autora);
					break;

				case 7: 
					System.out.println("Zadej název žánru knih které potřebuješ (historicky, dobrodruzny, detektivni, fantasy, sci-fi): ");
						
					String typzanru = sc.next().toUpperCase();

					if (Arrays.asList(ZANRY).contains(typzanru)) 
					{
						zanr = Roman.Zanr.valueOf(typzanru);
						mojeDatabaze.vypisKnihyDleZanru(zanr);
					} 
					else
					{
						System.out.println("Neplatný žánr, zadávejte prosím jednu z možností: historický, dobrodružný, detektivní, fantasy, sci-fi");
					}
					break;

				case 8:
					mojeDatabaze.vypisVypujnychKnih();
					break;

				case 9:
					System.out.println("Zadej nazev knihy kterou si prejes uložit do souboru: ");
					nazev_knihy = sc.next();
					mojeDatabaze.ulozeniKnihyDoSouboru(nazev_knihy);
					break;
				case 10:
					System.out.println("Zadej nazev knihy kterou chces vypsati ze souboru: ");
					nazev_knihy = sc.next();
					mojeDatabaze.nacteniKnihyZeSouboru(nazev_knihy);
					break;
				case 11:
				

					System.out.println("Zadejte název knihy:");
					nazev_knihy = sc.next();
					System.out.println("Zadejte stav dostupnosti knihy (false pro vypůjčenou, true pro dostupnou):");
					boolean stav = sc.nextBoolean();
					mojeDatabaze.setStavDostupnosti(nazev_knihy, stav);
					break;


				case 13:
					mojeDatabaze.ulozeniKnihDoDatabaze();
					run = false;
					break;
			
			}
			
		}
		mojeDatabaze.closeConnection();

	}
}
