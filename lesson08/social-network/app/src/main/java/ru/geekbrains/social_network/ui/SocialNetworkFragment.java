package ru.geekbrains.social_network.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.geekbrains.social_network.R;
import ru.geekbrains.social_network.data.CardsSource;
import ru.geekbrains.social_network.data.CardsSourceImpl;

public class SocialNetworkFragment extends Fragment {

    public static SocialNetworkFragment newInstance() {
        return new SocialNetworkFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_socialnetwork, container, false);
        CardsSource data = new CardsSourceImpl(getResources()).init();
        initRecyclerView(view, data);
        return view;
    }

    private void initRecyclerView(View view, CardsSource data) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        SocialNetworkAdapter adapter = new SocialNetworkAdapter(data);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);

        adapter.setOnItemClickListener((view1, position) -> {
            Toast.makeText(getContext(), String.format("Позиция - %d", position), Toast.LENGTH_SHORT).show();
        });
    }
}