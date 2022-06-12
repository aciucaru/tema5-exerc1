package ro.fastrackit.tema5exerc1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//controller pentru subdomeniul "/continents/"
@RestController
@RequestMapping("continents") // subdomeniu la nivel de clasa (controller), toate metodele vor fi relative la acest subdomeniu
public class ContinentController
{
	private final CountryService countryService;
	
	public ContinentController(CountryService countryService)
	{
		this.countryService = countryService;
	}
	
	@GetMapping("/{continent}/countries")
	public List<Country> getAllCountriesByContinent(@PathVariable String continent)
	{
		// se initializeaza doar la nevoie (lazy)
		List<Country> countriesByContinent = null;
		
		if(continent != null)
		{
			// cu stream, se gasesc toate tarile ce apartin continentul trimis ca argument
			countriesByContinent = countryService.findAll()
												.stream()
												.filter((Country country) -> country.continent().equals(continent))
												.collect(Collectors.toList());
		}
		else
			// ca sa nu se returneze null se initializeaza un List<Country> gol (si doar la nevoie (lazy))
			countriesByContinent = new ArrayList<Country>();
		
		return countriesByContinent;
	}
	
	@GetMapping(value = "/{continent}/countries", params = "minPopulation")
	public List<Country> getAllCountriesByContinentAndMinPopulation(@PathVariable String continent,
																	@RequestParam long minPopulation)
	{
		List<Country> countries= null;
		
		if(continent != null)
		{
			// cu stream, se gasesc toate tarile ce apartin continentul trimis ca argument
			// si au populatia >= cu minPopulation
			countries = countryService.findAll()
										.stream()
										.filter((Country country) -> country.continent().equals(continent))
										.filter((Country country) -> country.population() >= minPopulation)
										.collect(Collectors.toList());
		}
		else
			// ca sa nu se returneze null se initializeaza un List<Country> gol (si doar la nevoie (lazy))
			countries = new ArrayList<Country>();
		
		return countries;
	}
	
	@GetMapping("/countries")
	public Map<String, List<Country>> getContinentToCountriesMap()
	{
		Map<String, List<Country>> map;
		
		List<Country> countriesByContinent;
		
		// se grupeaza tarile dupa continent; pentru grupare se foloseste 'groupingBy()', iar gruparea se face
		// dupa rezultatul metodei 'continent()', ce returneaza field-ul cu acelasi nume
		map = countryService.findAll()
					.stream()
					.collect(Collectors.groupingBy(Country::continent));
		
		return map;
	}
}
