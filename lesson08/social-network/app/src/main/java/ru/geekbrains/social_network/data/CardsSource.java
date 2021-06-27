package ru.geekbrains.social_network.data;

public interface CardsSource {
    CardData getCardData(int position);
    int getSize();
}
