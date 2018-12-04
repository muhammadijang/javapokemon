package id.ijang.java_pokemon;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.ijang.java_pokemon.Adapter.PokemonListAdapter;
import id.ijang.java_pokemon.Common.Common;
import id.ijang.java_pokemon.Common.ItemOffsetDecoration;
import id.ijang.java_pokemon.Model.Pokedex;
import id.ijang.java_pokemon.Retrofit.IPokemonDex;
import id.ijang.java_pokemon.Retrofit.RetrofitClient;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class PokemonList extends Fragment {

    IPokemonDex iPokemonDex;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    RecyclerView pokemon_list_recycleview;

    static PokemonList instance;

    public static PokemonList getInstance(){
        if (instance == null)
            instance = new PokemonList();
        return instance;
    }


    public PokemonList() {
        Retrofit retrofit = RetrofitClient.getInstance();
        iPokemonDex = retrofit.create(IPokemonDex.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pokemon_list, container, false);

        pokemon_list_recycleview = (RecyclerView)view.findViewById(R.id.pokemon_list_recycleview);
        pokemon_list_recycleview.setHasFixedSize(true);
        pokemon_list_recycleview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(getActivity(),R.dimen.spacing);
        pokemon_list_recycleview.addItemDecoration(itemOffsetDecoration);

        fetchData();

        return view;
    }

    private void fetchData() {
        compositeDisposable.add(iPokemonDex.getListPokemon()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Pokedex>() {
                    @Override
                    public void accept(Pokedex pokedex) throws Exception {
                        Common.commonPokemonList = pokedex.getPokemon();
                        PokemonListAdapter adapter = new PokemonListAdapter(getActivity(),Common.commonPokemonList);

                        pokemon_list_recycleview.setAdapter(adapter);
                    }
                })
        );
    }

}
