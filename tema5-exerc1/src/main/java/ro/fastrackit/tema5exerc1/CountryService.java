package ro.fastrackit.tema5exerc1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class CountryService
{
	List<Country> countries;
	
	public CountryService(CountryReader countryReader)
	{
		countries = countryReader.readCountries();
	}
	
	// metoda ce returneaza toate obiectele 'Country" din repozitoriu
	public List<Country> findAll() { return countries; }
	
	// metoda ce returneaza un singur obiect 'Country', dupa ID
	public Country getReferenceById(long id)
	{
		// pentru cautarea dupa ID se poate folosi o bucla while sau stream-uri
		// pt. ca se folosesc stream-uri, rezultatul va fi de tip Optional<Country>, nu de tip Country
		Optional<Country> optCountry = null;
		
		// se cauta obiectul de tip Country dupa ID, folosind 'filter()' si 'findFirst()'
		optCountry = countries.stream()
								.filter((Country country) -> country.id() == id)
								.findFirst();
		
		// in acest caz, 'findFirst()' returneaza un Optional<Country> si se verifica daca exista un obiect Country
		// in interiorul obiectului Optional
		if(optCountry.isPresent()) // daca exista, atunci inseamna ca s-a gasit obiect cu ID-ul repectiv
			return optCountry.get(); // se returneaza obiectul
		else // daca nu exista obiect in interiorul lui Optional, atunci nu s-a gasit nimic si se returneaza null
			return null;
	}
}
