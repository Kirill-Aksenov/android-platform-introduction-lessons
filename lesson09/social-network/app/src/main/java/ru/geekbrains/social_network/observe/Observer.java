package ru.geekbrains.social_network.observe;

import ru.geekbrains.social_network.data.CardData;

public interface Observer {
    void updateCardData(CardData cardData);
}
