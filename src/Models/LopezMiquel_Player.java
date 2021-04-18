package Models;

import java.util.ArrayList;

public class LopezMiquel_Player {
    String nickname;
    ArrayList<LopezMiquel_Card> stickerAlbum = new ArrayList<>();
    ArrayList<LopezMiquel_Card> deck = new ArrayList<>();

    public void addCardToDeck( LopezMiquel_Card x){
        this.deck.add(x);
    }

    public LopezMiquel_Player(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ArrayList<LopezMiquel_Card> getDeck() {
        return deck;
    }

    public ArrayList<LopezMiquel_Card> getStickerAlbum() {
        return stickerAlbum;
    }

    @Override
    public String toString() {
        return "Player{" +
                "nickname='" + nickname + '\'' +
                ", deck=" + deck +
                '}';
    }
}
