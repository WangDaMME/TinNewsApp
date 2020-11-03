package com.wangdamme.myapplication.ui.search;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wangdamme.myapplication.R;
import com.wangdamme.myapplication.databinding.SearchNewsItemBinding;
import com.wangdamme.myapplication.model.Article;

import java.util.ArrayList;
import java.util.List;

// *** [1]  Recycler View 1. Adapter  *** //
public class SearchNewsAdapter extends RecyclerView.Adapter<SearchNewsAdapter.SearchNewsViewHolder> {

    // 1. Supporting data: List<Articles>
    private List<Article> articles = new ArrayList<>();

    public void setArticles( List<Article> newList)
    {
        articles.clear();
        articles.addAll(newList);
        notifyDataSetChanged();  // same function as Refresh,  notify the data is refreshed
    }

    // 2. Adapter overrides:

    //onCreateViewHolder is for providing the generated item views;  // from res/layout/search_news_item.xml generateds
    @NonNull
    @Override
    public SearchNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_news_item,parent,false);
        return new SearchNewsViewHolder(view);
    }

    //onBindViewHolder is for binding the data with a view;
    @NonNull
    @Override
    public void onBindViewHolder(@NonNull SearchNewsViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_24dp);
        holder.itemTitleTextView.setText(article.title);
        Picasso.get().load(article.urlToImage).into(holder.itemImageView);

    }


    //providing the current data collection size;
    @NonNull
    @Override
    public int getItemCount() {
        return articles.size();
    }

    //  create ViewHolder ***//
    // 3. SearchNewsViewHolder:
    public static class SearchNewsViewHolder extends RecyclerView.ViewHolder
    {
        // everyview what contains in item view.xml

        ImageView favoriteImageView;
        ImageView itemImageView;
        TextView itemTitleTextView;

        public SearchNewsViewHolder(@NonNull View itemView)
        {
            super(itemView);
            SearchNewsItemBinding binding = SearchNewsItemBinding.bind(itemView);  // from res/layout/search_news_item.xml generateds
            favoriteImageView = binding.searchItemFavorite; //"@+id/search_item_favorite"
            itemImageView = binding.searchItemImage;
            itemTitleTextView=binding.searchItemTitle;
        }

    }

}
