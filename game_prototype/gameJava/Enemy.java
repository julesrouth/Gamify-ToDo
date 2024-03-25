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
        ArrayList<Move> tempMoveList = new ArrayList<Move>();
        //Add 10 unique moves to the list
        tempMoveList.add(new Move(GameEnum.MoveType.ATTACK, 1, 50, "Poke"));
        tempMoveList.add(new Move(GameEnum.MoveType.DEFEND, 1, 25, "Block"));
        tempMoveList.add(new Move(GameEnum.MoveType.ATTACK, 2, 100, "Slash"));
        tempMoveList.add(new Move(GameEnum.MoveType.DEFEND, 2, 75, "Shield"));
        tempMoveList.add(new Move(GameEnum.MoveType.ATTACK, 3, 150, "Stab"));
        tempMoveList.add(new Move(GameEnum.MoveType.DEFEND, 3, 100, "Block"));
        tempMoveList.add(new Move(GameEnum.MoveType.ATTACK, 4, 200, "Punch"));
        tempMoveList.add(new Move(GameEnum.MoveType.DEFEND, 4, 150, "Parry"));
        tempMoveList.add(new Move(GameEnum.MoveType.ATTACK, 5, 250, "Kick"));
        tempMoveList.add(new Move(GameEnum.MoveType.DEFEND, 5, 200, "Dodge"));
        tempMoveList.add(new Move(GameEnum.MoveType.ATTACK, 6, 300, "Bite"));
        tempMoveList.add(new Move(GameEnum.MoveType.DEFEND, 6, 250, "Evade"));


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
            for (int j = 0; j < 2; j++) {
                int temp = random.nextInt(tempMoveList.size());
                tempMoveList2.add(tempMoveList.get(temp));
            }

            //Get a random name from the list
            int temp = random.nextInt(nameList.size());
            String tempName = nameList.get(temp);

            //Add the enemy to the list
            enemyList.add(new Enemy(new Stat(100, 10, 10, 10, 1), 10, tempMoveList2, tempName));
        }

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
        Enemy enemy = new Enemy();
        System.out.println(enemy.getAttack());
        enemy.getMove();
        System.out.println(enemy.getDefense());
        System.out.println(enemy.getSpeed());
        System.out.println(enemy.stance);
    }
}
