package id.ijang.java_pokemon.Retrofit;


import id.ijang.java_pokemon.Model.Pokedex;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface IPokemonDex {
    @GET("pokedex.json")
    Observable<Pokedex> getListPokemon();
}
