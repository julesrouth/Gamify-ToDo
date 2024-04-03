package com.example.todofromscratch.game;


import com.example.todofromscratch.model.domain.Stat;
import com.example.todofromscratch.model.domain.Statuses;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy {

    private Stances.StanceEnum stance = Stances.StanceEnum.DEFEND;
    private Stat stat;
    private int shielding;
    private int lastMoveInit = -1;
    private Statuses status;
    private List<Move> moves;
    private int initiative;
    private List<Enemy> enemyList;
    private String name;

    public String getName(){
        return name;
    }


    public ArrayList<Move> getMoves(){
        return (ArrayList<Move>) moves;
    }

    public void initEnemyList(){
        enemyList = new ArrayList<Enemy>();
        enemyList.add(new Enemy());
        ArrayList<Move> attackMoveList = new ArrayList<Move>();
        ArrayList<Move> defendMoveList = new ArrayList<Move>();

        //Add 10 unique moves to the list
        attackMoveList.add(new Move(GameEnum.MoveType.ATTACK, 1, 60, "Poke"));
        attackMoveList.add(new Move(GameEnum.MoveType.ATTACK, 2, 108, "Slash"));
        attackMoveList.add(new Move(GameEnum.MoveType.ATTACK, 3, 150, "Stab"));
        attackMoveList.add(new Move(GameEnum.MoveType.ATTACK, 4, 190, "Punch"));
        attackMoveList.add(new Move(GameEnum.MoveType.ATTACK, 5, 220, "Kick"));
        attackMoveList.add(new Move(GameEnum.MoveType.ATTACK, 6, 250, "Bite"));
        
        defendMoveList.add(new Move(GameEnum.MoveType.DEFEND, 1, 30, "Block"));
        defendMoveList.add(new Move(GameEnum.MoveType.DEFEND, 2, 54, "Shield"));
        defendMoveList.add(new Move(GameEnum.MoveType.DEFEND, 3, 75, "Block"));
        defendMoveList.add(new Move(GameEnum.MoveType.DEFEND, 4, 95, "Parry"));
        defendMoveList.add(new Move(GameEnum.MoveType.DEFEND, 5, 110, "Dodge"));
        defendMoveList.add(new Move(GameEnum.MoveType.DEFEND, 6, 125, "Evade"));


        ArrayList<String> nameList = new ArrayList<String>();
        nameList.add("Goblin");
        nameList.add("Orc");
        nameList.add("Troll");
        nameList.add("Giant");
        nameList.add("Dragon");
        nameList.add("Golem");

        //Get two random moves and add them to a list
        for(int i = 0; i < 5; i ++){
            ArrayList<Move> tempMoveList2 = new ArrayList<Move>();
            Random random = new Random();
            tempMoveList2.add(attackMoveList.get(random.nextInt(attackMoveList.size())));
            tempMoveList2.add(defendMoveList.get(random.nextInt(defendMoveList.size())));
            // for (int j = 0; j < 2; j++) {
            //     int temp = random.nextInt(tempMoveList.size());
            //     tempMoveList2.add(tempMoveList.get(temp));
            // }

            //Get a random name from the list
            int temp = random.nextInt(nameList.size());
            String tempName = nameList.get(temp);

            //Add the enemy to the list
            Enemy tempEnemy = new Enemy(new Stat(50, 10, 10, 10, 1), 10, tempMoveList2, tempName);
            tempEnemy.stat = generateRandomEnemyStat(5);
            enemyList.add(tempEnemy);
        }

    }

public Stat generateRandomEnemyStat(int level){
    Random random = new Random();
    
    float randomValueHealth = 1 + random.nextFloat() * 3;
    int health = 20 + Math.round(2 * level * randomValueHealth);
    
    float randomValueAttack = 1 + random.nextFloat() * 3;
    int attack = 10 + Math.round(level * randomValueAttack);
    
    float randomValueDefense = 1 + random.nextFloat() * 3;
    int defense = 10 + Math.round(level * randomValueDefense);
    
    float randomValueSpeed = 1 + random.nextFloat() * 3;
    int speed = 10 + Math.round(level * randomValueSpeed);
    
    return new Stat(health, attack, defense, speed, level);
}

    public Enemy() {
        this.stat = new Stat();
        this.initiative = 10;
        this.status = new Statuses();
        this.moves = new ArrayList<>();
        this.moves.add(new Move(GameEnum.MoveType.ATTACK, 2, 100, "Slash"));
        this.moves.add(new Move(GameEnum.MoveType.DEFEND, 2, 75, "Shield"));
        this.name = "Fortnite Default Dance";
    }

    public Enemy(Stat stat, int initiative, List<Move> moves, String name) {
        this.stat = stat;
        this.initiative = initiative;
        this.status = new Statuses();
        this.moves = moves;
        this.name = name;
    }

    public Enemy getRandomEnemy(){
        Random random = new Random();
        int temp = random.nextInt(enemyList.size());
        return enemyList.get(temp);
    }

    public double getSpeed() {
        return 1 - (stat.getSpeed() / 100);
    }

    public int getAttack() {
        double tempAttack = stat.getAttack();
        if (status.isRage()) {
            tempAttack *= 1.3;
        }
        if (status.isFrightened()) {
            tempAttack *= 0.75;
        }
        return (int) Math.ceil(tempAttack);
    }

    public int getDefense() {
        if (shielding != 0) {
            int temp = (int) (stat.getDefense() * shielding);
            shielding = 0;
            return temp;
        }
        return stat.getDefense();
    }

    public Move getMove() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int temp = random.nextInt(moves.size());
            Move tempMove = moves.get(temp);
            if (shielding != 0 && tempMove.getMoveType() == GameEnum.MoveType.DEFEND) {
                continue;
            } else {
                return tempMove;
            }
        }
        return new Move(GameEnum.MoveType.ATTACK, 0, 100, "Does Nothing");
    }
    public Stat getStat() {
        return stat;
    }

    public Statuses getStatus() {
        return status;
    }

    public void setStatus(Statuses status) {
        this.status = status;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }

    public void setShielding(int shielding) {
        this.shielding = shielding;
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

    public int getShielding() {
        return shielding;
    }

    public int getInitiative() {
        return initiative;
    }

    public Stances.StanceEnum getStance() {
        return stance;
    }

    public static void main(String[] args) {
        Enemy temp = new Enemy();
        System.out.println("Testing Enemy Sstat Generation");
        System.out.println(temp.generateRandomEnemyStat(1));
        System.out.println(temp.generateRandomEnemyStat(1));
        System.out.println(temp.generateRandomEnemyStat(1));

        System.out.println(temp.generateRandomEnemyStat(10));
        System.out.println(temp.generateRandomEnemyStat(10));
        System.out.println(temp.generateRandomEnemyStat(10));



        System.out.println(temp.generateRandomEnemyStat(100));
        System.out.println(temp.generateRandomEnemyStat(100));
        System.out.println(temp.generateRandomEnemyStat(100));

        Enemy enemy = new Enemy();
        System.out.println(enemy.getAttack());
        enemy.getMove();
        System.out.println(enemy.getDefense());
        System.out.println(enemy.getSpeed());
        System.out.println(enemy.stance);
    }
}
