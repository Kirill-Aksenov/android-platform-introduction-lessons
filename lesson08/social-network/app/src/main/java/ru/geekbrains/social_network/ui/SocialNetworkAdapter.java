package ru.geekbrains.social_network.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import ru.geekbrains.social_network.R;
import ru.geekbrains.social_network.data.CardData;
import ru.geekbrains.social_network.data.CardsSource;

public class SocialNetworkAdapter extends RecyclerView.Adapter<SocialNetworkAdapter.ViewHolder> {

    private final static String TAG = "SocialNetwork";
    private CardsSource dataSource;
    private OnItemClickListener itemClickListener;

    public SocialNetworkAdapter(CardsSource dataSource){
        this.dataSource = dataSource;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        Log.d(TAG, "onCreateViewHolder");
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SocialNetworkAdapter.ViewHolder holder, int position) {
        holder.setData(dataSource.getCardData(position));
        Log.d(TAG, String.format("onBindViewHolder - %d", position));
    }

    @Override
    public int getItemCount() {
        return dataSource.getSize();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView description;
        private AppCompatImageView image;
        private CheckBox like;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.imageView);
            like = itemView.findViewById(R.id.like);

            image.setOnClickListener(v -> {
                if (itemClickListener != null){
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }

        public void setData(CardData data){
            title.setText(data.getTitle());
            description.setText(data.getDescription());
            like.setChecked(data.isLike());
            image.setImageResource(data.getPicture());
        }
    }
}
