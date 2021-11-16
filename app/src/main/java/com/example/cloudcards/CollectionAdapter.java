package com.example.cloudcards;

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

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {
    private String[] card_names;
    private String[] imageIds;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public CollectionAdapter(String[] card_names, String[] imageIds) {
        this.card_names = card_names;
        this.imageIds = imageIds;
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
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

//        TextView textView = cardView.findViewById(R.id.card_name);
//        textView.setText(card_names[position]);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(card_names[position]);
                }
            }
        });


    }

    private Listener listener;
    interface Listener {
        void onClick(String foodName);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
