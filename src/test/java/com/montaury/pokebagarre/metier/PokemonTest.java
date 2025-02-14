package com.montaury.pokebagarre.metier;

import com.montaury.pokebagarre.webapi.PokeBuildApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.concurrent.CompletableFuture;

/**
 * Description du contenu : Cette classe va nous servir à définir tous les tests
 * nécessaires au bon fonctionnement de notre jeu
 *
 * La liste des tests préalablement définis :
 * - Si l'attaque est la même (2 sens)
 * - Si la défense est le même (2 sens)
 * - Si l'attaque et défense sont les mêmes (2 sens)
 */

class PokemonTest {

    @Test
    void l_attaque_est_la_meme_defense_premier_inferieure() {
        //GIVEN
        //PokeBuildApi api = new PokeBuildApi();
        //CompletableFuture<Pokemon> poke1 = api.recupererParNom("Pikachu");
        //CompletableFuture<Pokemon> poke2 = api.recupererParNom("Salamèche");

        Pokemon pokemon1 = new Pokemon("p1", "url", new Stats(10, 12));
        Pokemon pokemon2 = new Pokemon("p2", "url", new Stats(10, 15));

        //WHEN
        boolean vainqueur = pokemon1.estVainqueurContre(pokemon2);

        //WHEN
        Assertions.assertEquals(false, vainqueur);
    }

    @Test
    void l_attaque_est_la_meme_defense_premier_superieure() {
        //GIVEN
        //PokeBuildApi api = new PokeBuildApi();
        //CompletableFuture<Pokemon> poke1 = api.recupererParNom("Pikachu");
        //CompletableFuture<Pokemon> poke2 = api.recupererParNom("Salamèche");

        Pokemon pokemon1 = new Pokemon("p1", "url", new Stats(10, 17));
        Pokemon pokemon2 = new Pokemon("p2", "url", new Stats(10, 15));

        //WHEN
        boolean vainqueur = pokemon1.estVainqueurContre(pokemon2);

        //WHEN
        Assertions.assertEquals(true, vainqueur);
    }

    @Test
    void l_attaque_est_differente_premier_attaque_superieure() {
        //GIVEN
        Pokemon pokemon1 = new Pokemon("p1", "url", new Stats(19, 12));
        Pokemon pokemon2 = new Pokemon("p2", "url", new Stats(10, 15));

        //WHEN
        boolean vainqueur = pokemon1.estVainqueurContre(pokemon2);

        //WHEN
        Assertions.assertEquals(true, vainqueur);
    }

    @Test
    void l_attaque_est_differente_premier_attaque_inferieure() {
        //GIVEN
        Pokemon pokemon1 = new Pokemon("p1", "url", new Stats(7, 12));
        Pokemon pokemon2 = new Pokemon("p2", "url", new Stats(10, 15));

        //WHEN
        boolean vainqueur = pokemon1.estVainqueurContre(pokemon2);

        //WHEN
        Assertions.assertEquals(false, vainqueur);
    }

    @Test
    void l_attaque_et_la_defense_sont_les_memes_premier_vainqueur() {
        //GIVEN
        Pokemon pokemon1 = new Pokemon("p1", "url", new Stats(10, 12));
        Pokemon pokemon2 = new Pokemon("p2", "url", new Stats(10, 12));

        //WHEN
        boolean vainqueur = pokemon1.estVainqueurContre(pokemon2);

        //WHEN
        Assertions.assertEquals(true, vainqueur);
    }

    @Test
    void l_attaque_et_la_defense_sont_les_memes_deuxieme_perdant() {
        //GIVEN
        Pokemon pokemon1 = new Pokemon("p1", "url", new Stats(10, 12));
        Pokemon pokemon2 = new Pokemon("p2", "url", new Stats(10, 12));

        //WHEN
        boolean vainqueur = pokemon2.estVainqueurContre(pokemon1);

        //WHEN
        Assertions.assertEquals(false, vainqueur);
    }
}