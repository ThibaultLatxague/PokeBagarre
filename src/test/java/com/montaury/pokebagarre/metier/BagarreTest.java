package com.montaury.pokebagarre.metier;

import com.montaury.pokebagarre.erreurs.ErreurMemePokemon;
import com.montaury.pokebagarre.erreurs.ErreurPokemonNonRenseigne;
import com.montaury.pokebagarre.webapi.PokeBuildApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

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
    public void devrait_determiner_le_vainqueur_correctement() {
        Pokemon pikachu = mock(Pokemon.class);
        Pokemon bulbizarre = mock(Pokemon.class);

        when(mockApi.recupererParNom("Pikachu")).thenReturn(CompletableFuture.completedFuture(pikachu));
        when(mockApi.recupererParNom("Bulbizarre")).thenReturn(CompletableFuture.completedFuture(bulbizarre));
        when(pikachu.estVainqueurContre(bulbizarre)).thenReturn(true);

        Pokemon vainqueur = bagarre.demarrer("Pikachu", "Bulbizarre").join();

        assertThat(vainqueur).isEqualTo(pikachu);
    }
}