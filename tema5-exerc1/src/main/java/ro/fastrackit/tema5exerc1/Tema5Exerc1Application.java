package ro.fastrackit.tema5exerc1;

/*
Spring Web Application

Country Endpoints: Read the countries from file . You get the file location from configuration

It has the format: name|capital|population|area|continent|neighbour1~neighbour2

also generate an ID for each country

Build the country endpoints offering the following behavior

- list all countries: /countries -> returns a list of country objects

- list all country names : /countries/names -> returns a list of strings

- get capital of a country : /countries/<countryId>/capital -> returns a string

- get population of a country : /countries/<countryId>/population -> returns a long

- get countries in continent : /continents/<continentName>/countries -> returns list of Country objects

- get country neighbours : /countries/<countryId>/neighbours -> returns list of Strings

- get countries in <continent> with population larger than <population> : /continents/<continentName>/countries?minPopulation=<minimum population> -> returns list of Country objects

- get countries that neighbor X but not neighbor Y : /countries?includeNeighbour=<includedNeighbourCode>&excludeNeighbour=<excludedNeighbourCode> -> returns list of Country objects

- get map from country to population : /countries/population -> returns map from String to Long

- get map from continent to list of countries : /continents/countries  -> returns Map from String to List<Country>

for the next endpoint you will use a request-scoped bean to read the headers. For this method you will require a
header with name "X-Country" that will represent the caller's country. Return that country if exists.
- get my country: /countries/mine

 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Tema5Exerc1Application
{

	public static void main(String[] args)
	{
		SpringApplication.run(Tema5Exerc1Application.class, args);
	}
}
