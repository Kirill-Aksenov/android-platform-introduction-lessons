package ru.geekbrains.social_network.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.geekbrains.social_network.MainActivity;
import ru.geekbrains.social_network.Navigation;
import ru.geekbrains.social_network.R;
import ru.geekbrains.social_network.data.CardData;
import ru.geekbrains.social_network.data.CardsSource;
import ru.geekbrains.social_network.data.CardsSourceFirebaseImpl;
import ru.geekbrains.social_network.data.CardsSourceImpl;
import ru.geekbrains.social_network.data.CardsSourceResponse;
import ru.geekbrains.social_network.observe.Observer;
import ru.geekbrains.social_network.observe.Publisher;

public class SocialNetworkFragment extends Fragment {

    private static final int MY_DEFAULT_DURATION = 1000;
    private CardsSource data;
    private SocialNetworkAdapter adapter;
    private RecyclerView recyclerView;
    private Navigation navigation;
    private Publisher publisher;
    // признак, что при повторном открытии фрагмента
    // (возврате из фрагмента, добавляющего запись)
    // надо прыгнуть на последнюю запись
    private boolean moveToLastPosition;

    public static SocialNetworkFragment newInstance() {
        return new SocialNetworkFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_socialnetwork, container, false);
        setHasOptionsMenu(true);
        initRecyclerView(view);
        data = new CardsSourceFirebaseImpl().init(new CardsSourceResponse() {
            @Override
            public void initialized(CardsSource cardsData) {
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setDataSource(data);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.cards_menu, menu);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v,
                                    @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.card_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onContextItemSelected(item);
    }

    private boolean onItemSelected(int menuItemId) {
        switch (menuItemId) {
            case R.id.action_add:
                navigation.addFragment(CardFragment.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateCardData(CardData cardData) {
                        data.addCardData(cardData);
                        adapter.notifyItemInserted(0);
                        // это сигнал, чтобы вызванный метод onCreateView
                        // перепрыгнул на конец списка
                        recyclerView.scrollToPosition(0);
                    }
                });
                return true;
            case R.id.action_update:
                int updatePosition = adapter.getMenuPosition();
                navigation.addFragment(CardFragment.newInstance(data.getCardData(updatePosition)), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateCardData(CardData cardData) {
                        data.updateCardData(updatePosition, cardData);
                        adapter.notifyItemChanged(updatePosition);
                    }
                });
                return true;
            case R.id.action_delete:
                int deletePosition = adapter.getMenuPosition();
                data.deleteCardData(deletePosition);
                adapter.notifyItemRemoved(deletePosition);
                return true;
            case R.id.action_clear:
                data.clearCardData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return false;
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_lines);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SocialNetworkAdapter(this);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);

        // Установим анимацию. А чтобы было хорошо заметно, сделаем анимацию долгой
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(MY_DEFAULT_DURATION);
        animator.setRemoveDuration(MY_DEFAULT_DURATION);
        recyclerView.setItemAnimator(animator);

        adapter.setOnItemClickListener((view1, position) -> {
            Toast.makeText(getContext(), String.format("Позиция - %d", position), Toast.LENGTH_SHORT).show();
        });
    }
}