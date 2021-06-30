package ru.geekbrains.social_network.data;

public interface CardsSource {
    CardData getCardData(int position);
    int getSize();
    void deleteCardData(int position);
    void updateCardData(int position, CardData cardData);
    void addCardData(CardData cardData);
    void clearCardData();
}
