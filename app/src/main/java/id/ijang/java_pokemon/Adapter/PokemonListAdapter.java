package id.ijang.java_pokemon.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import id.ijang.java_pokemon.Interface.IItemClickListener;
import id.ijang.java_pokemon.Model.Pokemon;
import id.ijang.java_pokemon.R;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.MyViewHolder> {

    Context context;
    List<Pokemon> pokemonList;

    public PokemonListAdapter(Context context, List<Pokemon> pokemonList) {
        this.context = context;
        this.pokemonList = pokemonList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.pokemon_list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Load image
        Glide.with(context).load(pokemonList.get(position).getImg()).into(holder.pokemon_image);
        //Get Name
        holder.pokemon_name.setText(pokemonList.get(position).getName());

        //Event
        holder.setiItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(context, "Click at Pokemon: "+pokemonList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView pokemon_image;
        TextView pokemon_name;

        IItemClickListener iItemClickListener;

        public void setiItemClickListener(IItemClickListener iItemClickListener) {
            this.iItemClickListener = iItemClickListener;
        }

        public MyViewHolder(View itemView) {
            super(itemView);

            pokemon_image = (ImageView)itemView.findViewById(R.id.pokemon_image);
            pokemon_name = (TextView)itemView.findViewById(R.id.txt_pokemon_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iItemClickListener.onClick(view,getAdapterPosition());
        }
    }
}
