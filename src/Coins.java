public class Coins {
    private int coinBalance;

    public Coins() {
        this.coinBalance = 0;
    }

    public void addCoins(int amount) {
        this.coinBalance += amount;
    }

    // New method to return the formatted coin balance
    public int getFormattedBalance() {
        return coinBalance;
    }

    public int getCoinBalance() {
        return coinBalance;
    }
}
