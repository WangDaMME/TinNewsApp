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
import com.wangdamme.myapplication.model.Article;
import com.wangdamme.myapplication.repository.NewsRepository;
import com.wangdamme.myapplication.repository.NewsViewModelFactory;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import java.util.List;

// To know a card has been swiped either by gesture or button click?
// We need some sort of callback for it.
// This callback is CardStackListener.
// Now we let the HomeFragment implement the CartStackListener interface:
public class HomeFragment extends Fragment implements CardStackListener {

    //Use ViewModel in the Fragments
    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;  // generate by //res/layout/fragment_home

    private CardStackLayoutManager layoutManager;

    private List<Article> articles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_home, container, false);

        // First draw a Home Fragment View
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Setup CardStack View
        CardSwipeAdapter swipeAdapter = new CardSwipeAdapter();
        layoutManager = new CardStackLayoutManager(requireContext(),this);
        // setup stack display
        layoutManager.setStackFrom(StackFrom.Top);
        //CardStack View actually is RecyclerView
        binding.homeCardStackView.setLayoutManager(layoutManager);
        binding.homeCardStackView.setAdapter(swipeAdapter);

        // Handle like unlike button clicks
        binding.homeLikeButton.setOnClickListener(v-> swipeCard(Direction.Right));
        binding.homeUnlikeButton.setOnClickListener(v-> swipeCard(Direction.Left));


        NewsRepository repository= new NewsRepository(getContext()); // input Context
        viewModel= new ViewModelProvider(this, new NewsViewModelFactory(repository)).get(HomeViewModel.class);

        viewModel.setCountryInput("us");
        // LiveDate<>. observe //Adds the given observer to the observers list within the lifespan of the given owner.
        // LiveData<NewsResponse>
        viewModel.getTopHeadlines().observe(
                getViewLifecycleOwner(), newsResponse -> {
                    if(newsResponse!=null)
                    {
                        // Log.d("HomeFragment",newsResponse.toString()); // print in logCat
                        articles= newsResponse.articles;
                        swipeAdapter.setArticles(articles);
                    }
                }
        );

    }

    //=========== Override Listener ========//

    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {
        if(direction==Direction.Left)
        {
            Log.d("CardStackView", "Unliked "+layoutManager.getTopPosition());
        }
        else if (direction==Direction.Right)
        {
            Log.d("CardStackView", "Liked "+layoutManager.getTopPosition());

        }
    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {

    }

    @Override
    public void onCardDisappeared(View view, int position) {

    }
    //=========== Override Listener ========//



    // Implement Swipe Events
    private void swipeCard(Direction direction)
    {
        SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                                        .setDirection(direction)
                                        .setDuration(Duration.Normal.duration)
                                        .build();

        layoutManager.setSwipeAnimationSetting(setting);
        binding.homeCardStackView.swipe();
    }
}