package ru.geekbrains.social_network.data;

public interface CardsSource {
    CardsSource init(CardsSourceResponse cardsSourceResponse);
    CardData getCardData(int position);
    int getSize();
    void deleteCardData(int position);
    void updateCardData(int position, CardData cardData);
    void addCardData(CardData cardData);
    void clearCardData();
}
