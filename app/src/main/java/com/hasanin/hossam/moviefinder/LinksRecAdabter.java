package com.hasanin.hossam.moviefinder;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

public class LinksRecAdabter extends RecyclerView.Adapter<LinksRecAdabter.LinksViewHolder> {

    private ArrayList<String> links;
    private Activity activity;
    private Integer[] colors = new Integer[10];

    public LinksRecAdabter(ArrayList<String> links , Activity activity){
        this.links = links;
        this.activity = activity;
        colors[0] = R.drawable.rect_foshia_rounded;
        colors[1] = R.drawable.rect_pink_rounded;
        colors[2] = R.drawable.rect_rounded_yellow;
        colors[3] = R.drawable.rect_baby_blue_rounded;
    }

    @NonNull
    @Override
    public LinksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View layoutInflater = LayoutInflater.from(activity).inflate(R.layout.links_card , parent , false);
        return new LinksViewHolder(layoutInflater);
    }

    @Override
    public void onBindViewHolder(@NonNull LinksViewHolder holder, final int i) {
        if (!links.get(i).isEmpty()) {
            holder.linkButton.setText(links.get(i));
            int n = new Random().nextInt(4);

            holder.linkButton.setBackground(ContextCompat.getDrawable(activity, colors[n]));
            holder.linkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = links.get(i);
                    if (!links.get(i).startsWith("http://") && !links.get(i).startsWith("https://")) {
                        url = "http://" + url;
                    }
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    activity.startActivity(browserIntent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    public static class LinksViewHolder extends RecyclerView.ViewHolder{

        public Button linkButton;
        public LinksViewHolder(@NonNull View itemView) {
            super(itemView);
            linkButton = itemView.findViewById(R.id.linkButton);
        }
    }

}
