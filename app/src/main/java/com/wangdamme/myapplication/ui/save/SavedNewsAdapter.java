package com.wangdamme.myapplication.ui.save;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wangdamme.myapplication.R;
import com.wangdamme.myapplication.databinding.SavedNewsItemBinding;
import com.wangdamme.myapplication.model.Article;

import java.util.ArrayList;
import java.util.List;

public class SavedNewsAdapter extends RecyclerView.Adapter<SavedNewsAdapter.SavedNewsViewHolder>{

    // 1. Supporting data:
    private List<Article> articles = new ArrayList<>();

    public void setArticles(List<Article> newList)
    {
        articles.clear();
        articles.addAll(newList);
        notifyDataSetChanged(); // refresh data from observer
    }

    // 2. Adapter overrides:


    //onCreateViewHolder: for providing the generated item views
    @NonNull
    @Override
    public SavedNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_news_item,parent,false);
        return new SavedNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedNewsViewHolder holder, int position)
    {
        // super.onBindViewHolder(holder, position, payloads);
        Article article = articles.get(position);
        holder.authorTextView.setText(article.author);
        holder.descriptionTextView.setText(article.description);

        // set up Onclick Listener for image Icon
        holder.favoriteIcon.setOnClickListener(
                v->{
                    itemCallback.onRemoveFavorite(article);
                }
        );

        holder.itemView.setOnClickListener(
                v -> {
                    itemCallback.onOpenDetails(article);
                }
        );
    }

    //onBindViewHolder: for binding the data with a view.



    //getItemCount: for providing the current data collection size;
    @Override
    public int getItemCount() {
        return articles.size();
    }

    // 3. SavedNewsViewHolder:
    public static class SavedNewsViewHolder extends RecyclerView.ViewHolder
    {
        TextView authorTextView;
        TextView descriptionTextView;
        ImageView favoriteIcon;

        public SavedNewsViewHolder(@NonNull View itemView)
        {
            super(itemView);
            SavedNewsItemBinding binding = SavedNewsItemBinding.bind(itemView);

            authorTextView = binding.savedItemAuthorContent;
            descriptionTextView=binding.savedItemDescriptionContent;
            favoriteIcon=binding.savedItemFavoriteImageView;
        }
    }

    // Unlike to Delete
    interface ItemCallback
    {
        void onOpenDetails(Article article);    // to-be-implemented : for opening a new fragment for expanding article details
        void onRemoveFavorite(Article article); // to-be-implemented : to remove articles in the saved database
    }

    private ItemCallback itemCallback;

    public void setItemCallback(ItemCallback itemCallback)
    {
        this.itemCallback = itemCallback;
    }
}
