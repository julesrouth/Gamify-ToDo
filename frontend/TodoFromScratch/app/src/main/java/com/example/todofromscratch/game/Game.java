package com.example.todofromscratch.game;

import com.example.todofromscratch.game.Enemy;
import com.example.todofromscratch.game.Move;
import com.example.todofromscratch.game.Player;
import com.example.todofromscratch.model.domain.Stat;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import java.util.Scanner;
import java.util.List;

public class Game {

    private Stances.StanceGrid stances;
    private Deque<String> messageLog;
    private int combatTime;
    private int printCnt;

    private Player player;
    private Map map;
    private Enemy currentEnemy;
    private Shop shop;
    private boolean needPlayerInput = true;
    private Move nextPlayerMove = null;


    public static final int LOG_LENGTH = 5;

    public Game() {
        this.stances = new Stances.StanceGrid();
        this.messageLog = new ArrayDeque<>(LOG_LENGTH);
        for(int i = 0; i < LOG_LENGTH; i++) {
            this.messageLog.addLast("");
        }
        this.combatTime = 0;

        this.player = new Player(0, 0);
        this.map = new Map();
        // this.currentEnemy = new Enemy();
        this.currentEnemy = new Enemy();
        this.currentEnemy.initEnemyList();
        this.currentEnemy = this.currentEnemy.getRandomEnemy();

        this.shop = new Shop();

        for (int i = 0; i < LOG_LENGTH; i++) {
            this.messageLog.addLast("");
        }
    }
    public int getPlayerInitiative(){
        return player.getInitiative();
    }
    public int getEnemyInitiative(){
        return currentEnemy.getInitiative();
    }
    public void userInputShop() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Input Item to buy, you have " + this.player.getGold() + " gold");
            String inputPlayer = scanner.nextLine().toLowerCase();

            for (Item i : this.shop.getItems()) {
                if (i.getName().toLowerCase().equals(inputPlayer)) {
                    if (this.player.getGold() < i.getPrice()) {
                        System.out.println("Not enough gold");
                        return;
                    }
                    this.player.setGold(this.player.getGold() - i.getPrice());
                    this.player.addItem(i);
                    this.shop.removeItem(i);
                    System.out.println("Item " + i.getName() + " bought for " + i.getPrice() + " gold");
                    return;
                }
            }
            System.out.println("Invalid item");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void moveOnMap() {
        System.out.print("\033c");
        System.out.println(map.getSurrounding(this.player.getLocation()[0], this.player.getLocation()[1]));
        System.out.println("\n\nInput Direction\n");

        Scanner scanner = new Scanner(System.in);
        String inputDir = scanner.nextLine();
        GameEnum.Direction direction;

        switch (inputDir) {
            case "w":
                direction = GameEnum.Direction.UP;
                break;
            case "s":
                direction = GameEnum.Direction.DOWN;
                break;
            case "a":
                direction = GameEnum.Direction.LEFT;
                break;
            case "d":
                direction = GameEnum.Direction.RIGHT;
                break;
            default:
                System.out.println("Invalid input");
                return;
        }

        System.out.println(direction);

        int[] tempLoc = this.player.moveLoc(direction, map);
        if (tempLoc == null) {
            System.out.println("Invalid move");
            return;
        }

        GameEnum.Terrain terrain = map.move(tempLoc[0], tempLoc[1]);
        this.player.move(tempLoc, terrain);
    }

    public void speedCheck() {
        if (this.player.getStat().getLevel() > this.currentEnemy.getStat().getLevel()) {
            // Unchanged
        } else if (this.player.getStat().getLevel() < this.currentEnemy.getStat().getLevel()) {
            // Unchanged
        } else {
            Random random = new Random();
            if (random.nextInt(2) == 0) {
                // Unchanged
            } else {
                // Unchanged
            }
        }
    }

    public int typeAdvantage(int attackType, int defenseType) {
        // Unchanged
        return 1;
    }

    public boolean useItem(Item item) {
        this.messageLog.addLast("The item: " + item.getName());
        if ("potion".equals(item.getName().toLowerCase())) {
            this.messageLog.addLast("Player used " + item.getName() + " for " + item.getStats().getHealth() + " health");
            this.player.getStat().setHealth(this.player.getStat().getHealth() + item.getStats().getHealth());
            if (this.player.getStat().getHealth() > this.player.getStat().getMaxHealth()) {
                this.player.getStat().setHealth(this.player.getStat().getMaxHealth());
            }
            this.player.getInventory().remove(item);
            return true;
        } else {
            this.messageLog.addLast("Invalid item" + " " + item.getName());
        }
        return false;
    }

    public int damageCalc(int attack, int defense, double adv, int attackerLevel, int defenderLevel) {
        double attackDefenseRatio = attack / (defense / 2);
        double levelRatio = attackerLevel / (double) defenderLevel;
        double randomValue = Math.random() * 0.2 + 0.9;
        return (int) Math.ceil(attackDefenseRatio * levelRatio * adv * randomValue);
    }
    public int damageCalc(int attack, int defense, double adv) {
        return damageCalc(attack, defense, adv, 1, 1);
    }
    public int damageCalc(int attack, int defense) {
        return damageCalc(attack, defense, 1);
    }


    public boolean enoughMana(Move spell){
        if(this.player.getStat().getMana() < spell.getManaCost()){
                this.messageLog.addLast(
                        "Not enough mana for " + spell.getName() + ", you need " + spell.getManaCost() +
                                " mana, you have " + this.player.getStat().getMana() + " mana"
                );            
                return false;
        }
        return true;
    }
    public boolean spellDoSomething(Move spell) {
        
        if ("fireball".equals(spell.getName())) {
            if (!enoughMana(spell)) {
                return false;
            }
            this.player.getStat().setMana(this.player.getStat().getMana() - spell.getManaCost());
            int damage = this.damageCalc(
                    this.player.getAttack() * spell.getPower(),
                    this.currentEnemy.getDefense()

            );
            this.damageEnemy(damage);
            String actionStr = "Player used " + spell.getName() + " for " + damage + " damage";
            this.messageLog.addLast(actionStr);
        } 
        else if("heal".equals(spell.getName())){
            if (!enoughMana(spell)) {
                return false;
            }
            this.player.getStat().setMana(this.player.getStat().getMana() - spell.getManaCost());
            int heal = spell.getPower();
            this.player.getStat().setHealth(this.player.getStat().getHealth() + heal);
            if(this.player.getStat().getHealth() > this.player.getStat().getMaxHealth()){
                this.player.getStat().setHealth(this.player.getStat().getMaxHealth());
            }
            String actionStr = "Player used " + spell.getName() + " for " + heal + " heal";
            this.messageLog.addLast(actionStr);
        }
        else if("shield".equals(spell.getName())){
            if (this.player.getStat().getMana() < spell.getManaCost()) {
                this.messageLog.addLast(
                        "Not enough mana for shield, you need " + spell.getManaCost() +
                                " mana, you have " + this.player.getStat().getMana() + " mana"
                );
                return false;
            }
            this.player.getStat().setMana(this.player.getStat().getMana() - spell.getManaCost());
            int shield = spell.getPower();
            this.player.setShielding(shield);
            String actionStr = "Player used " + spell.getName() + " for " + shield + " shield";
            this.messageLog.addLast(actionStr);
        }
        else if("enrage".equals(spell.getName())){
            if (this.player.getStat().getMana() < spell.getManaCost()) {
                this.messageLog.addLast(
                        "Not enough mana for enrage, you need " + spell.getManaCost() +
                                " mana, you have " + this.player.getStat().getMana() + " mana"
                );
                return false;
            }
            this.player.getStat().setMana(this.player.getStat().getMana() - spell.getManaCost());
            this.player.getStatus().addStatus(spell.getStatus(), spell.getStatusDuration());
            String actionStr = "Player used " + spell.getName();
            this.messageLog.addLast(actionStr);
        }
        else if("necrotic touch".equals(spell.getName())){
            if (!enoughMana(spell)) {
                return false;
            }
            this.player.getStat().setMana(this.player.getStat().getMana() - spell.getManaCost());
            int damage = this.damageCalc(
                    this.player.getAttack() * spell.getPower(),
                    this.currentEnemy.getDefense()
            );
            this.damageEnemy(damage);
        
            this.player.getStat().setHealth(this.player.getStat().getHealth() + damage);
            if(this.player.getStat().getHealth() > this.player.getStat().getMaxHealth()){
                this.player.getStat().setHealth(this.player.getStat().getMaxHealth());
            }
            String actionStr = "Player used " + spell.getName() + " for " + damage + " damage and healed for " + damage;
            this.messageLog.addLast(actionStr);
        }
        
        else {
            this.messageLog.addLast("Invalid spell");
            return false;
        }
        return true;
    }


    public void damagePlayer(int damage) {
        this.player.getStat().setHealth(this.player.getStat().getHealth() - damage);
    }

    public void damageEnemy(int damage) {
        this.currentEnemy.getStat().setHealth(this.currentEnemy.getStat().getHealth() - damage);
    }

    public double getStanceValue(boolean player) {
        if (player) {
            return this.stances.getStance(this.player.getStance(), this.currentEnemy.getStance());
        } else {
            return this.stances.getStance(this.currentEnemy.getStance(), this.player.getStance());
        }
    }

    public void resetCombat(){
        this.combatTime = 0;
        this.player.setInitiative(0);
        this.currentEnemy.setInitiative(0);
        this.printCnt = 0;
    }

    public void combatTick(){
        Global.clearScreen();
        // this.combatTime = 0;
        // int printCnt = 0;

        this.printCombatStatus();
        List<String> tempStatus = this.player.getStatus().checkStatus(this.combatTime);

        if (tempStatus != null) {
            for (String i : tempStatus) {
                this.messageLog.addLast(i);
            }
        }

        this.printCnt++;


        if(this.needPlayerInput == true){
            System.out.println("Need player input");
            return;
        }
        if (this.combatTime >= this.player.getInitiative()) {
            System.out.println("Resolving Move");
            // Move playerMove = this.player.getMove();
            System.out.println(this.nextPlayerMove.getName());
            this.resolvePlayerMove(this.nextPlayerMove);
            this.nextPlayerMove = null;
            this.needPlayerInput = true;
        }

        if (this.combatTime >= this.currentEnemy.getInitiative()) {
            Move enemyMove = this.currentEnemy.getMove();
            this.resolveEnemyMove(enemyMove);
        }

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (this.player.getStat().getHealth() <= 0) {
            System.out.println("You lose");
            return;
        }

        if (this.currentEnemy.getStat().getHealth() <= 0) {
            System.out.println("You win");
            return;
        }

        this.combatTime++;

    }

    public void combat() {
        Global.clearScreen();
        this.combatTime = 0;
        this.printCnt = 0;

        while (true) {
            this.printCombatStatus();
            List<String> tempStatus = this.player.getStatus().checkStatus(this.combatTime);
            if (tempStatus != null) {
                for (String i : tempStatus) {
                    this.messageLog.addLast(i);
                }
            }

            this.printCnt++;

            if (this.combatTime >= this.player.getInitiative()) {
                Move playerMove = this.player.getMove();
                this.resolvePlayerMove(playerMove);
            }

            if (this.combatTime >= this.currentEnemy.getInitiative()) {
                Move enemyMove = this.currentEnemy.getMove();
                this.resolveEnemyMove(enemyMove);
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (this.player.getStat().getHealth() <= 0) {
                System.out.println("You lose");
                break;
            }

            if (this.currentEnemy.getStat().getHealth() <= 0) {
                System.out.println("You win");
                break;
            }

            this.combatTime++;
        }
    }
    public void resolvePlayerMove(Move playerMove) {
        if (playerMove.getMoveType() == GameEnum.MoveType.ATTACK) {
            int damage = this.damageCalc(
                    this.player.getAttack() * playerMove.getPower(),
                    this.currentEnemy.getDefense(),
                    this.getStanceValue(true)
            );
            String actionStr = "Player used " + playerMove.getName() + " for " + damage + " damage";
            this.messageLog.addLast(actionStr);
            this.damageEnemy(damage);
        } else if (playerMove.getMoveType() == GameEnum.MoveType.DEFEND) {
            this.player.setShielding(playerMove.getPower());
            String actionStr = "Player used " + playerMove.getName() + " for " + playerMove.getPower() + " shield";
            this.messageLog.addLast(actionStr);
        } else if (playerMove.getMoveType() == GameEnum.MoveType.SPELL) {
            if (!this.spellDoSomething(playerMove)) {
                System.out.println("Invalid Spell\n");
                this.player.setInitiative(this.player.getInitiative() + 2);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            }
        } else if (playerMove.getMoveType() == GameEnum.MoveType.USEITEM) {
            this.messageLog.addLast("Player used " + playerMove.getName());
            System.out.println(this.player.printInventory());
            System.out.println("Input item to use");
            Scanner scanner = new Scanner(System.in);
            String inputPlayer = scanner.nextLine();
            for (Item i : this.player.getInventory().getItems()) {
                if (i.getName().toLowerCase().equals(inputPlayer)) {
                    this.messageLog.addLast("Using item: " + i.getName());
                    if (this.useItem(i)) {
                        return;
                    }
                }
            }
            this.messageLog.addLast("Invalid item");
            return;
        }
        else if(playerMove.getMoveType() == GameEnum.MoveType.STATUS){
            this.messageLog.addLast("Player used " + playerMove.getName());
            if(playerMove.effectsSelf()){
                this.player.getStatus().addStatus(playerMove.getStatus(), playerMove.getStatusDuration());
            }
            else{
                this.currentEnemy.getStatus().addStatus(playerMove.getStatus(), playerMove.getStatusDuration());
            }
        }
        else {
            this.messageLog.addLast("Invalid move");
            return;
        }
        this.player.setLastMoveInit(this.player.getInitiative());
        this.player.setInitiative(this.player.getInitiative() + (int) Math.ceil(playerMove.getSpeed() * this.player.getSpeed()));
        System.out.println('\n');
    }


    public void setPlayerMove(Move move){
        this.needPlayerInput = false;
        this.nextPlayerMove = move;
    }

    public void resolveEnemyMove(Move enemyMove) {
        if (enemyMove.getMoveType() == GameEnum.MoveType.ATTACK) {
            int damage = this.damageCalc(
                    this.currentEnemy.getAttack() * enemyMove.getPower(),
                    this.player.getDefense(),
                    this.getStanceValue(false)
            );
            String actionStr = "Enemy used " + enemyMove.getName() + " for " + damage + " damage";
            this.messageLog.addLast(actionStr);
            this.damagePlayer(damage);
        } else {
            this.currentEnemy.setShielding(enemyMove.getPower());
            String actionStr = "Enemy used " + enemyMove.getName() + " for " + enemyMove.getPower() + " shield";
            this.messageLog.addLast(actionStr);
        }
        this.currentEnemy.setLastMoveInit(this.currentEnemy.getInitiative());
        this.currentEnemy.setInitiative(
                this.currentEnemy.getInitiative() + (int) Math.ceil(enemyMove.getSpeed() * this.currentEnemy.getSpeed())
        );
        System.out.println('\n');
    }
    public List<Move> getPlayerMoves(){
        return player.getMoves();
    }

    public Stat getPlayerStat(){
        return player.getStat();
    }
    public Stat getEnemyStat(){
        return currentEnemy.getStat();
    }
    public void printCombatStatus() {
        if (messageLog.size() > LOG_LENGTH) {
            messageLog.removeFirst();
        }

        Global.clearScreen();

        String tempStr = currentEnemy.getName();
        //Print out the moves
        tempStr += "  Moves: ";
        for (Move i : this.currentEnemy.getMoves()) {
            tempStr += i.getName() + " ";
        }
        tempStr += "\nPlayer:";
        double tempHealthRatio = (double)(this.player.getStat().getHealth()) / (double)(this.player.getStat().getMaxHealth());
        tempHealthRatio = Math.ceil(tempHealthRatio * 10.0) / 10.0;
        try{
            tempStr += "Health Bar[" + "X".repeat((int) (tempHealthRatio * 10)) + " ".repeat(10 - (int) (tempHealthRatio * 10)) + "]\n"; 
        }   
        catch(Exception e){
            e.printStackTrace();
            tempStr += "Health Bar[" + "X".repeat(10) + " ".repeat(0) + "]\n";
        }
        //print mana bar
        double tempManaRatio = (double)(this.player.getStat().getMana()) / (double)(this.player.getStat().getMaxMana());
        tempManaRatio = Math.ceil(tempManaRatio * 10.0) / 10.0;
        try{
            tempStr += "Mana Bar[" + "X".repeat((int) (tempManaRatio * 10)) + " ".repeat(10 - (int) (tempManaRatio * 10)) + "]\n";
        }
        catch(Exception e){
            e.printStackTrace();
            tempStr += "Mana Bar[" + "X".repeat(10) + " ".repeat(0) + "]\n";
        }
        //Print statuses
        tempStr += "Statuses: ";
        tempStr += this.player.getStatus().getStatusString();
        try {
            int percentInitPlayer = (int) Math.ceil(
                    10 * (this.combatTime - this.player.getLastMoveInit()) /
                            (this.player.getInitiative() - this.player.getLastMoveInit())
            );
            tempStr += "Action [" + "X".repeat(percentInitPlayer) + " ".repeat(10 - percentInitPlayer) + "]\n\n";
        } catch (Exception e) {
            e.printStackTrace();
            tempStr += "Action [" + "X".repeat(10) + " ".repeat(0) + "]\n\n";

        }

        tempStr += "Enemy:\n";
        tempStr +="Enemy Stats: " + this.currentEnemy.getStat().toString() + "\n";
        tempHealthRatio = (double)this.currentEnemy.getStat().getHealth() / (double)this.currentEnemy.getStat().getMaxHealth();
        tempHealthRatio = Math.round(tempHealthRatio * 10) / 10.0;
        try{
        tempStr += "Health Bar[" + "X".repeat((int) (tempHealthRatio * 10)) + " ".repeat(10 - (int) (tempHealthRatio * 10)) + "]\n";
        }
        catch(Exception e){
            e.printStackTrace();
            tempStr += "Health Bar[" + "X".repeat(10) + " ".repeat(0) + "]\n";
        }
        try {
            int percentInitEnemy = (int) Math.ceil(
                    10 * (this.combatTime - this.currentEnemy.getLastMoveInit()) /
                            (this.currentEnemy.getInitiative() - this.currentEnemy.getLastMoveInit())
            );
            tempStr += "Action [" + "X".repeat(percentInitEnemy) + " ".repeat(10 - percentInitEnemy) + "]\n\n";
        } catch (Exception e) {
            e.printStackTrace();
            tempStr += "Action [" + "X".repeat(10) + " ".repeat(0) + "]\n\n";

        }

        for (String i : this.messageLog) {
            tempStr += i + "\n";
        }

        System.out.println(tempStr);
    }

    public boolean getNeedPlayerInput() {
        return this.needPlayerInput;
    }
    public static void main(String[] args) {
        Game game = new Game();
        game.resetCombat();
        int counter = 0;
        while(true){
            counter++;
            if(counter > 10000){
                break;
            }
            game.combatTick();
            if(game.getNeedPlayerInput() == true){
                Move tempMove = game.player.getMove();
                game.setPlayerMove(tempMove);
            }
        }
        game.combat();
    }
}