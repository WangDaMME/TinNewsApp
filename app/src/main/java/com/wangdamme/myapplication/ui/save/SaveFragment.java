package com.wangdamme.myapplication.ui.save;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wangdamme.myapplication.R;
import com.wangdamme.myapplication.databinding.FragmentSaveBinding;
import com.wangdamme.myapplication.model.Article;
import com.wangdamme.myapplication.repository.NewsRepository;
import com.wangdamme.myapplication.repository.NewsViewModelFactory;


public class SaveFragment extends Fragment {

    private FragmentSaveBinding binding;//fragment_save.xml
    private SaveViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSaveBinding.inflate(inflater,container,false);
        return binding.getRoot();

        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_save, container, false);
    }


    // Create Save Model Instance
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // Intgerate savedNewsAdapter Recycler View
        SavedNewsAdapter savedNewsAdapter = new SavedNewsAdapter();
        binding.newsSavedRecyclerView.setAdapter(savedNewsAdapter);
        binding.newsSavedRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        //Provide an Anonymous implemetation of ItemCallback
        savedNewsAdapter.setItemCallback(new SavedNewsAdapter.ItemCallback() {
            @Override
            public void onOpenDetails(Article article) {
                Log.d("onOpenDetails",article.toString());
                SaveFragmentDirections.ActionNavigationSaveToNavigationDetails direction = SaveFragmentDirections.actionNavigationSaveToNavigationDetails(article);

                NavHostFragment.findNavController(SaveFragment.this).navigate(direction);

            }

            @Override
            public void onRemoveFavorite(Article article) {
                viewModel.deleteSavedArticle(article);
            }
        });

        NewsRepository repository = new NewsRepository(getContext());
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository)).get(SaveViewModel.class);
        viewModel
                .getAllSavedArticles()
                .observe(
                        getViewLifecycleOwner(),
                        //LiveData<List< Article >> getAllSavedArticles()
                        savedArticles -> {
                            if (savedArticles != null) {
                                Log.d("SaveFragment", savedArticles.toString());
                                savedNewsAdapter.setArticles(savedArticles);
                            }
                        });
    }
}