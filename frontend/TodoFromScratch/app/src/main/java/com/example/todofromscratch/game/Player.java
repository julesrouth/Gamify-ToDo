package com.example.todofromscratch.game;


import com.example.todofromscratch.model.domain.Stat;
import com.example.todofromscratch.model.domain.Statuses;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



// enum Stance {
//     ATTACK, // Add other stances as needed
// }

class Player {
    private Stances.StanceEnum stance = Stances.StanceEnum.ATTACK;
    private int gold;
    private int[] location;
    private Stat stat;
    private int shielding;
    private int lastMoveInit;
    private Inventory inventory;
    private List<Move> moves;
    private int initiative;
    private Statuses status;
    private Move nextMove;

    public Player(int x, int y) {
        this.location = new int[]{x, y};
        this.stat = new Stat();
        this.inventory = new Inventory();
        this.moves = new ArrayList<Move>();
        this.initiative = 5;
        this.status = new Statuses();

        // Initialize moves
        moves.add(new Move(GameEnum.MoveType.USEITEM, 0, 0, "Use an Item"));
        moves.add(new Move(GameEnum.MoveType.ATTACK, 2, 100, "attack"));
        moves.add(new Move(GameEnum.MoveType.DEFEND, 2, 75, "defend"));
        moves.add(new Move(GameEnum.MoveType.ATTACK, 4, 150, "strong attack"));
        moves.add(new Move(GameEnum.MoveType.SPELL, 4, 50,"5", "fireball"));
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public Move getNextMove(){
        return nextMove;
    }

    public void setNextMove(Move nextMove){
        this.nextMove = nextMove;
    }


    public List<Move> getMoves(){
        return moves;
    }
    public void removeItem(Item item) {
        inventory.remove(item);
    }
    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void addGold(int gold) {
        this.gold += gold;
    }
    public Stat getStat() {
        return stat;
    }
    public void setStat(Stat stat) {
        this.stat = stat;
    }
    public void setHealth(int health) {
        stat.setHealth(health);
    }


    public String printInventory() {
        StringBuilder tempStr = new StringBuilder();
        for (Item item : inventory.getItems()) {
            tempStr.append(item).append("\n");
        }
        return tempStr.toString();
    }
    public Stances.StanceEnum getStance() {
        return stance;
    }
    public double getSpeed() {
        return 1 - (stat.getSpeed() / 100);
    }

    public int getAttack() {
        int tempAttack = stat.getAttack();
        for (Item item : inventory.getItems()) {
            tempAttack += item.getStats().getAttack();
        }
        tempAttack = status.calculateAttack(tempAttack); // Assuming 'status' is an instance variable of the Statuses class
        return (int) Math.ceil(tempAttack);
    }
    public Statuses getStatus() {
        return status;
    }
    public int getInitiative() {
        return initiative;
    }

    public void setMana(int mana) {
        stat.setMana(mana);
    }

    public int getMana() {
        return stat.getMana();
    }
    public Inventory getInventory() {
        return inventory;
    }

    public void setLastMoveInit(int lastMoveInit) {
        this.lastMoveInit = lastMoveInit;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public int getLastMoveInit() {
        return lastMoveInit;
    }
    public int setShielding(int shield) {
        this.shielding = shield;
        return shield;
    }

    public int getDefense() {
        int tempDef = stat.getDefense();
        for (Item item : inventory.getItems()) {
            tempDef += item.getStats().getDefense();
        }

        if (shielding != 0) {
            int temp = tempDef * shielding;
            shielding = 0;
            return temp;
        }

        return stat.getDefense();
    }

    public int[] getLocation() {
        return location;
    }

    public int[] moveLoc(GameEnum.Direction dir, Map map) {
        int[] tempLoc = location.clone();
        switch (dir) {
            case UP:
                tempLoc[1] -= 1;
                break;
            case DOWN:
                tempLoc[1] += 1;
                break;
            case LEFT:
                tempLoc[0] -= 1;
                break;
            case RIGHT:
                tempLoc[0] += 1;
                break;
            default:
                System.out.println("Invalid direction");
                return null;
        }
        return tempLoc;
    }

    public void move(int[] tempLoc, GameEnum.Terrain terrain) {
        if (terrain == GameEnum.Terrain.INVALID) {
            System.out.println("Invalid move");
        } else {
            System.out.println("Valid move");
            location = tempLoc;
        }
    }

    public String movesStr() {
        StringBuilder tempStr = new StringBuilder();
        for (Move move : moves) {
            tempStr.append(move.getName()).append(", ");
        }
        return tempStr.substring(0, tempStr.length() - 2);
    }

    public Move getMove() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Input Move Valid Moves:" + movesStr());
            String inputPlayer = scanner.nextLine().toLowerCase();

            for (Move move : moves) {
                if (move.getName().toLowerCase().equals(inputPlayer)) {
                    return move;
                }
            }

            System.out.println("Invalid move");
        }
    }

    public static void main(String[] args) {
        Player player = new Player(0, 0);
        System.out.println(player.getAttack());
        player.moveLoc(GameEnum.Direction.UP, new Map());
        System.out.println(player.getDefense());
        System.out.println(player.getLocation()[0]);
    
        System.out.println(player.movesStr());

    }
}
