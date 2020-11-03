package com.wangdamme.myapplication.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wangdamme.myapplication.R;
import com.wangdamme.myapplication.databinding.FragmentHomeBinding;
import com.wangdamme.myapplication.repository.NewsRepository;
import com.wangdamme.myapplication.repository.NewsViewModelFactory;


public class HomeFragment extends Fragment {

    //Use ViewModel in the Fragments
    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;  // generate by //res/layout/fragment_home

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_home, container, false);

        binding = FragmentHomeBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NewsRepository repository= new NewsRepository(getContext()); // input Context
        viewModel= new ViewModelProvider(this, new NewsViewModelFactory(repository)).get(HomeViewModel.class);

        viewModel.setCountryInput("us");
        // LiveDate<>. observe //Adds the given observer to the observers list within the lifespan of the given owner.
        // LiveData<NewsResponse>
        viewModel.getTopHeadlines().observe(
                getViewLifecycleOwner(), newsResponse -> {
                    if(newsResponse!=null)
                    {
                        Log.d("HomeFragment",newsResponse.toString()); // print in logCat
                    }
                }
        );

    }
}