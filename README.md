# MAIF PikAPI
Afin de développer son activité, la MAIF propose un nouveau service pour assurer les Pokemons et leurs dresseurs.

Pour mieux cerner cette nouvelle cible, il nous ait demandé de mettre en place une API nous permettant d’avoir facilement des informations sur les caractéristiques spéciales de nos sociétaires atypiques

## Préambule

**Programmation fonctionnelle -- Lambda ou fonctions anonyme**

https://gayerie.dev/epsi-b3-java/langage_java/les_lambdas.html

*Une approche fonctionnelle va favoriser la représentation d’une application comme un appel chaîné de fonctions pouvant prendre elles-mêmes des fonctions en paramètres.*

```java

//méthode classique 
public static int method(int value){
    return value+1;
}

//fonction anonyme
(value)-> value+1;

//appel par référence de la méthode
...
.map(UselessClass::method)
...
```


## Optional:

**Objectif:** 
- Maitriser la valeur null
- Résilience 
- Force nos appelants à gérer l’absence de valeur

**Création de l’objet:**

- Optional.empty() absence de valeur
- Optional.of() présence assurée de valeur
- Optional.ofNullable() présence facultative de valeur

**Exercice 1:**  

Ecrire le service getPokemon() afin qu’il retourne un Optional avec le pokemon trouvé ou un optional empty si le pokemon n'existe pas

Utiliser la méthode getListAllPokemon() pour avoir la liste des pokemons

```java
	@Test
	public void exercice1() {
		/** Tester que l'on n'a pas de pokemon */
		final Optional<Pokemon> unknownPokemon = service.getPokemon(2500);
		Assertions.assertTrue(unknownPokemon.isEmpty());
		/** Tester que l'on a un pokemon */
		final Optional<Pokemon> pikachu = service.getPokemon(25);
		Assertions.assertTrue(pikachu.isPresent());
	}

```

**La consultation de l’objet:**

- optional.isEmpty() test l’absence de valeur
- optional.isPresent() test la présence de valeur
- optional.get() récupération de la valeur

**Programmation fonctionnelle**

- optional.map() , flatmap(), filter()
- optional.orElse..()



**Exercice 2:** 
Ecrire le service getPokemonNicknameOrNone () afin qu’il retourne: le surnom du pokemon 
ou "NONE" si il n’en a pas ou si le pokemon n’existe pas.
```java
	@Test
	public void exercice2() {
		/** Retourne le surnom des pokemons ou "NONE"*/
		String nickname = service.getPokemonNicknameOrNone(25);
		Assertions.assertEquals("Pique à chou",nickname);
		 nickname = service.getPokemonNicknameOrNone(2500);
		Assertions.assertEquals("NONE",nickname);
		nickname = service.getPokemonNicknameOrNone(12);
		Assertions.assertEquals("NONE", nickname);
	}
```


## Stream:


**Avantage:**
- Favoriser la programmation fonctionnelle
- Donner une place importante au lambda
- Rendre les programmes java plus efficient
- Favoriser les opérations multi thread

https://theboreddev.com/understanding-java-streams/

**Pipeline:**

| Source =>| => Opération intermédiaire  =>| =>Opération terminale |
| :--------------------- | :--------------- | :--------------- |
| Fichiers | map |Collect |
| Collections | filter |Reduce |
| Stream | sort |forEach |

https://www.youtube.com/watch?v=t1-YZ6bF-g0


**Source**
- Collection
    - myCollection.stream()
- Tableau
    - Arrays.stream(myArray)
- Fichier
    - Files.lines(path, Charset.forName("UTF-8"));
- Stream
    - Stream.empty()
    - Stream.of()
    - Stream.iterate(…)
- Primitif
    - IntStream.range(n,m)

**Opérateur terminal**

- Collection
    - collect(Collectors.toList());
- Math
    - count(), min(), max()
- Fusion
    - reduce()


**Exercice 1:** 

Ecrire le service getAllPokemon ()

Vous pouvez vous appuyer sur l’adaptateur:
```adapter.getStreamAllPokemon()```

```java
    @Test
	public void exercice1() {
		/** Réunir l'ensemble des données dans une liste*/
		final List<Pokemon> allPokemon = service.getAllPokemon();
		Assertions.assertEquals(795,allPokemon.size());
	}
```
**Opérateur intermédiaire**

- filter((v)->method(v))
    - Argument: Prend une méthode qui pour chaque élément retourne un boolean
    - Retour: un stream qui contient les éléments positif au filtre
- anymatch((v)->method(v))
    - Argument: Prend une méthode qui pour chaque élément retourne un boolean
    - Retour: un boolean qui indique si le test a été positif au moins une fois
- findFirst()
    - Retour: le premier élément avec un Optional


**Exercice 2:** 

Ecrire le service getAllLegendaryPokemon ()

Vous pouvez vous appuyer sur l’attribut ```isLegendary``` de l’objet Pokemon

```java
	@Test
	public void exercice2() {
		/** Retourne les pokemons legendaire*/
		final List<Pokemon> allPokemon = service.getAllLegendaryPokemon();
		Assertions.assertEquals(65,allPokemon.size());
	}
```
**Exercice 2 bis :** 

Ré-écrire le service getPokemon () en s’appuyant sur ```adapter.getStreamAllPokemon()```

- sorted(methodComparable)
    - Ordonne les éléments
- distinct()
    - Conserve un seul élément distinct 
- map((v)->method(v))
    - Applique l’opération élément par élément
    - Exemple:  map( vehicule -> vehicule.nbRoue()) 
        - Retourne un stream avec le nombre de roue de chaque élément



**Exercice 3:** 

Ecrire le service getFastestPokemon ( int n) qui retourne les n Pokemons les plus rapide

```java
	@Test
	public void exercice3() {
		/** Retourne le nom des pokemons les plus rapide*/
		final List<String> names = service.getFastestPokemon(3);
		Assertions.assertEquals(List.of("Shuckle", "Munchlax", "Trapinch"),names);
	}
```
- flatmap()
    - Permet de faire un traitement comme le map sur une liste/stream imbriqué
    - retourne un stream à plat

**_Exemple:_** 

*On a une liste de véhicules qui ont chacun une liste de passagers. 
On souhaite la liste de tous les passagers*
```java
…
//Stream des véhicules
.flatmap(veh -> veh.getStreamPassager())
//Stream des passagers de tous les véhicules
…
```

**Exercice 4:** 

Ecrire le service getAllTypeOfLegendaryPokemon()

```java
	@Test
	public void exercice4() {
		/** Retourne les types des pokemons legendaire*/
		final List<String> types = service.getAllTypeOfLegendaryPokemon();
		Assertions.assertEquals(16,types.size());
	}
```

**Exercice 5:** 

Ecrire le service getFusionPokemon (Integer… numbers ) qui fusionne plusieurs pokemons en un MegaPokemon

On va s’appuyer sur la méthode 
```private  Pokemon fusionPokemon(Pokemon p1, Pokemon p2)```
qui sait fusionner deux pokemons entre eux.


```java

	@Test
	public void exercice5() {
		/** Retourne un pokemon fusionné*/
		final Pokemon pokemon = service.getFusionPokemon(2,25);
		Assertions.assertEquals("Ivyachu",pokemon.getName());
		final Pokemon pokemon1 = service.getFusionPokemon(6,19,30);
		Assertions.assertEquals("Charrina",pokemon1.getName());
	}   
```
