package github.com.franciscovenned.kirmikcore.utils;

import org.bukkit.entity.Player;

public class Coins {

    private int coins;

    private Player player;

    public Coins(Player player, int coins){

        this.coins = coins;
        this.player = player;
    }

    public int getCoins(Player player) {
        return coins;
    }

    public void setCoins(int coins, Player player) {
        this.coins = coins;
        this.player = player;
    }
}
