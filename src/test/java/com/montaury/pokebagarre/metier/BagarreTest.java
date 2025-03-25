package com.montaury.pokebagarre.metier;

import com.montaury.pokebagarre.erreurs.ErreurBagarre;
import com.montaury.pokebagarre.erreurs.ErreurMemePokemon;
import com.montaury.pokebagarre.erreurs.ErreurPokemonNonRenseigne;
import com.montaury.pokebagarre.erreurs.ErreurRecuperationPokemon;
import com.montaury.pokebagarre.webapi.PokeBuildApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class BagarreTest {
    private PokeBuildApi mockApi;
    private Bagarre bagarre;

    @BeforeEach
    public void setUp() {
        mockApi = mock(PokeBuildApi.class);
        bagarre = new Bagarre(mockApi);
    }

    @Test
    public void devrait_lancer_erreur_si_premier_pokemon_vide() {
        Throwable thrown = catchThrowable(() -> bagarre.demarrer("", "Pikachu"));
        assertThat(thrown).isInstanceOf(ErreurPokemonNonRenseigne.class).hasMessageContaining("premier");
    }

    @Test
    public void devrait_lancer_erreur_si_second_pokemon_vide() {
        Throwable thrown = catchThrowable(() -> bagarre.demarrer("Pikachu", ""));
        assertThat(thrown).isInstanceOf(ErreurPokemonNonRenseigne.class).hasMessageContaining("second");
    }

    @Test
    public void devrait_lancer_erreur_si_premier_pokemon_null() {
        Throwable thrown = catchThrowable(() -> bagarre.demarrer(null, "Pikachu"));
        assertThat(thrown).isInstanceOf(ErreurPokemonNonRenseigne.class).hasMessageContaining("premier");
    }

    @Test
    public void devrait_lancer_erreur_si_second_pokemon_null() {
        Throwable thrown = catchThrowable(() -> bagarre.demarrer("Pikachu", null));
        assertThat(thrown).isInstanceOf(ErreurPokemonNonRenseigne.class).hasMessageContaining("second");
    }

    @Test
    public void devrait_lancer_erreur_si_les_deux_pokemons_sont_identiques() {
        Throwable thrown = catchThrowable(() -> bagarre.demarrer("Pikachu", "Pikachu"));
        assertThat(thrown).isInstanceOf(ErreurMemePokemon.class);
    }

    @Test
    public void devrait_lancer_erreur_car_mauvaise_recuperation_pokemon_2() {
        Pokemon pikachu = new Pokemon("Pikachu", "url", new Stats(2,2));
        String nomPokemon1 = "Pikachu";
        String nomPokemon2 = "pikachute";

        when(mockApi.recupererParNom(nomPokemon1)).thenReturn(CompletableFuture.completedFuture(pikachu));
        when(mockApi.recupererParNom(nomPokemon2)).thenReturn(CompletableFuture.failedFuture(new ErreurRecuperationPokemon(nomPokemon2)));

        Throwable thrown = catchThrowable(() -> bagarre.demarrer(nomPokemon1, nomPokemon2).get());

        assertThat(thrown)
                .hasCauseInstanceOf(ErreurRecuperationPokemon.class)
                .hasMessageContaining("Impossible de recuperer les details sur '" + nomPokemon2 + "'");
    }

    @Test
    public void devrait_lancer_erreur_car_mauvaise_recuperation_pokemon_1() {
        Pokemon pikachu = mock(Pokemon.class);
        String nomPokemon1 = "Pikachute";
        String nomPokemon2 = "pikachu";

        when(mockApi.recupererParNom(nomPokemon1)).thenReturn(CompletableFuture.failedFuture(new ErreurRecuperationPokemon(nomPokemon1)));
        when(mockApi.recupererParNom(nomPokemon2)).thenReturn(CompletableFuture.completedFuture(pikachu));

        Throwable thrown = catchThrowable(() -> {
            mockApi.recupererParNom(nomPokemon1).join();
        });

        assertThat(thrown)
                .isInstanceOf(ErreurRecuperationPokemon.class)
                .hasMessage("Impossible de recuperer les details sur '" + nomPokemon1 + "'") ;
    }
}