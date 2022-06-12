package ro.fastrackit.tema5exerc1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// controller pentru subdomeniul "/countries/"
@RestController
@RequestMapping("countries") // subdomeniu la nivel de clasa (controller), toate metodele vor fi relative la acest subdomeniu
public class CountryController
{
	private final CountryService countryService;
	
	public CountryController(CountryService countryService)
	{
		this.countryService = countryService;
	}
	
	@GetMapping("")
	public List<Country> getAllCountries() { return countryService.findAll(); }
	
	@GetMapping("/names")
	public List<String> getAllCountryNames()
	{
		List<String> countryNames;
		countryNames = countryService.findAll()
										.stream()
										.map((Country country) -> country.name())
										.collect(Collectors.toList());
		return countryNames;
	}
	
	@GetMapping("/{id}/capital")
	public String getOneCountryCapital(@PathVariable long id)
	{
		// se cauta tara dupa ID-ul repectiv
		Country country = countryService.getReferenceById(id);
		
		// daca tara a fost gasita
		if(country != null)
			return country.capital();
		else
			return ""; // nu se returneaza null
	}
	
	@GetMapping("/{id}/population")
	public long getOneCountryPopulation(@PathVariable long id)
	{
		// se cauta tara dupa ID-ul repectiv
		Country country = countryService.getReferenceById(id);
		
		// daca tara a fost gasita
		if(country != null)
			return country.population();
		else
			return 0;
	}

	@GetMapping("/{id}/neighbours")
	public List<String> getOneCountryNeighbours(@PathVariable long id)
	{
		List<String> countryNeighbours = null; // se initializeaza doar la nevoie (lazy)
		
		// se cauta tara dupa ID-ul repectiv
		Country country = countryService.getReferenceById(id);
		
		// daca tara a fost gasita
		if(country != null)
			countryNeighbours = Arrays.asList(country.neighbours());
		else
			countryNeighbours = new ArrayList<String>();
		
		return countryNeighbours;
	}
	
	@GetMapping(value = "", params = "includeNeighbour, excludeNeighbour")
	public List<Country> getAllCountriesByIncludeExcludeNeighbours
							(@RequestParam("includeNeighbour") String includedNeighbourCode,
							 @RequestParam("excludeNeighbour") String excludedNeighbourCode)
	{
		List<Country> countries= null;
		
		if(includedNeighbourCode != null && excludedNeighbourCode != null)
		{
			countries = countryService.findAll()
				.stream()
				.filter((Country country) -> Arrays.asList(country.neighbours()).contains(includedNeighbourCode))
				.filter((Country country) -> Arrays.asList(country.neighbours()).contains(excludedNeighbourCode) == false)
				.collect(Collectors.toList());
		}
		else
			// ca sa nu se returneze null se initializeaza un List<Country> gol (si doar la nevoie (lazy))
			countries = new ArrayList<Country>();
		
		return countries;
	}
	
	@GetMapping("/population")
	public Map<String, Long> getCountryToPopulationMap()
	{
		Map<String, Long> map = countryService.findAll()
				.stream()
				.collect(Collectors.toMap(Country::name, Country::population));
		
		return map;
	}
}
