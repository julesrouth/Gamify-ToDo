package com.example.todofromscratch.game;
import com.example.todofromscratch.model.domain.Statuses;

public class Move {

    private int power;
    private GameEnum.MoveType moveType;
    private int speed;
    private String effect;
    private String name;
    private int manacost;

    public Move(GameEnum.MoveType moveType, int power, int speed, String effect, String name) {
        this.moveType = moveType;
        this.power = power;
        this.speed = speed;
        this.effect = effect;
        this.name = name;
        if (moveType == GameEnum.MoveType.SPELL) {
            this.manacost = Integer.parseInt(effect.split(" ")[0]);
        }
    }
    public Move(GameEnum.MoveType moveType, int power, int speed, String name) {
        this(moveType, power, speed, "", name);
    }

    public String getName() {
        return name;
    }
    public int getPower() {
        return power;
    }
    public int getSpeed() {
        return speed;
    }

    public String getStatus(){
        //Do something
        switch(this.name) {
            case "Enrage":
                return "rage";
            default:
                return "error";
        }
    }
    public int getStatusDuration(){
        //Do something
        switch(this.name) {
            case "Enrage":
                return 170;
            default:
                return 0;
        }
    }
    public boolean effectsSelf(){
        //Do something
        switch(this.name) {
            case "Enrage":
                return true;
            default:
              return false;

        }
    }
    public String getEffect() {
        return effect;
    }

    @Override
    public String toString() {
        return name;
    }
    public int getManaCost() {
        return manacost;
    }
    public int getManacost(){
        return manacost;
    }
    
    public GameEnum.MoveType getMoveType() {
        return moveType;
    }

    

    public static void main(String[] args) {
        Move move = new Move(GameEnum.MoveType.ATTACK, 10, 100, "0", "Attack");
    }
}
