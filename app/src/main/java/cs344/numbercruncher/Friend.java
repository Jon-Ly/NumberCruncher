package cs344.numbercruncher;

/**
 * Created by wintow on 5/2/2018.
 */

public class Friend {

    private String username;
    private int losses;
    private int wins;

    public Friend(){
        this.username = "";
        this.losses = 0;
        this.wins = 0;
    }

    public String getUsername(){
        return this.username;
    }

    public int getLosses(){
        return this.losses;
    }

    public int getWins(){
        return this.wins;
    }

}
