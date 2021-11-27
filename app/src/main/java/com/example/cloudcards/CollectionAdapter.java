package com.example.cloudcards;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> implements Filterable {
    private ArrayList<Card> cardList;
    private ArrayList<Card> cardListFull;

    @Override
    public Filter getFilter() {
        return cardFilter;
    }

    private Filter cardFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Card> filteredCards = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredCards.addAll(cardListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Card cardItem : cardListFull) {
                    if(cardItem.getCard_name().toLowerCase().contains(filterPattern)) {
                        filteredCards.add(cardItem);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredCards;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            cardList.clear();
            cardList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public CollectionAdapter(ArrayList<Card> cardList) {
        this.cardList = cardList;
        this.cardListFull = new ArrayList<>(cardList);
    }

    @Override
    public int getItemCount() {
        return this.cardList.size();
    }

    @Override
    public CollectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_image, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final CardView cardView = holder.cardView;

        ImageView imageView = cardView.findViewById(R.id.card_image);
        Picasso.get()
                .load(this.cardList.get(position).getCard_img())
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }
                    @Override
                    public void onError(Exception e) {
                        Log.e("TAG", "onError:" + e.getMessage());
                    }
                });
        imageView.setContentDescription(this.cardList.get(position).getCard_name());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(cardList.get(position));
                }
            }
        });


    }

    private Listener listener;
    interface Listener {
        void onClick(Card cardName);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

}
