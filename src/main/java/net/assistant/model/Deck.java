package net.assistant.model;

public interface Deck {
    int getSize();
    int drawCard();
    CardType getCardType(int card);
    int getGemValue(int card);
}
