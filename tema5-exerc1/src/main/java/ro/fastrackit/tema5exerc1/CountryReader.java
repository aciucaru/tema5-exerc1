package ro.fastrackit.tema5exerc1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class CountryReader
{
	private static long countryCount = 0;

	// metoda ajutatoare ce converteste o singura linie din fisierul cu tari intr-un obiect de tip 'Country'
	// prin apelarea repetata a acestei metode, se poate converti tot string-ul aferent fisierului .txt intr-un
	// sir de obiecte 'Country'
	private static Country parseCountryString(String countryLine)
	{
		Country country = null;
		
		if(countryLine != null)
		{
			// se sparge string-ul 'countryLine' dupa caracterul '|', rezultand sirul de argumente aferente unei
			// singure tari, toate argumentele fiind in format String
			// split() foloseste regex iar '|' este caracter special in regex, deci se foloseste "\\|" in loc de "|"
			String[] countryParameters = countryLine.split("\\|", Integer.MAX_VALUE);
			
			// daca String-ul reprezenta toti cei 6 parametrii (in afara de ID) ai unei tari, atunci se va converti tara
			// altfel, pentru simplitate, tara nu va fi luata in considerare si nu va fi convertita intr-un 'Country'
			// este doar pentru siguranta, in realitate, toate tarile dint "countries.txt" vor genera 6 parametrii
			if(countryParameters.length == 6)
			{
				// se seteaza parametrii unui 'Country' la valorile din fisierul .txt
				// unele metoda folosite ar putea arunca exceptii, deci se foloseste bloc 'try'
				try
				{
					long id = countryCount;  // id-ul se genereaza, nu se citeste din fisierul .txt
					countryCount++;
					
					String name = countryParameters[0];
					String capital = countryParameters[1];
					long population = Integer.parseInt(countryParameters[2]);
					int area  = Integer.parseInt(countryParameters[3]);
					String continent = countryParameters[4];
	
					// sirul de tari vecine este ultimul parametru, adica elem. de index 5 al sirului de parametrii
					String countryNeighbours = countryParameters[5];
					// se sparge String-ul cu tarile vecine dupa delimitatorul '~', rezultand un sir de tari vecine
					// sirul rezultat va avea garantat un element, chiar si daca nu exista tari vecine, iar in acest
					// caz String-ul nu va fi nul, ci va fi gol (cu zero caractere)
					String[] neighbours = countryNeighbours.split("\\~", Integer.MAX_VALUE);
						
					country = new Country(id, name, capital, population, area, continent, neighbours);
				}
				catch(NumberFormatException excep) { System.out.print("number parameters for Country where not valid."); }
			}
		}
		
		return country;
	}
	
	public List<Country> readCountries()
	{
		 // sirul de tari in format Country
		List<Country> countries = new ArrayList<Country>();
		
	    Resource resource = new ClassPathResource("countries.txt", this.getClass().getClassLoader());
	    
	    InputStream resourceInputStream;
	    try
	    {
	    	resourceInputStream = resource.getInputStream();
	    	
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(resourceInputStream));
	    	
	    	countries = reader.lines()
	    					// se converteste din String in Country
							.map((String countryString) -> parseCountryString(countryString))
							// functia 'parseCountryString()' din mapping-ul de mai sus s-ar putea sa returneze
							// obiecte 'Country' nule, asa ca acele obiecte nule se filtreaza cu 'filter()'
							.filter((Country country) -> country != null)
							.collect(Collectors.toList());
	    }
	    catch (IOException excep)
	    {
	    	System.out.println("error: could not read 'countries.txt'.");
	    	excep.printStackTrace();
	    }
		
		return countries;
	}
}
