package com.example.cloudcards;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {
    private String[] card_names;
    private String[] imageIds;
    private ArrayList<Card> cardList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public CollectionAdapter(String[] card_names, String[] imageIds, ArrayList<Card> cardList) {
        this.card_names = card_names;
        this.imageIds = imageIds;
        this.cardList = cardList;
    }

    @Override
    public int getItemCount() {
        return card_names.length;
    }

    @Override
    public CollectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_image, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final CardView cardView = holder.cardView;

        ImageView imageView = cardView.findViewById(R.id.card_image);
        Picasso.get()
                .load(this.imageIds[position])
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
        imageView.setContentDescription(card_names[position]);

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
    public interface Listener {
        void onClick(Card cardName);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

}
