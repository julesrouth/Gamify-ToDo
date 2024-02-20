import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy {

    private Stances.StanceEnum stance = Stances.StanceEnum.DEFEND;
    private Stat stat;
    private int shielding;
    private int lastMoveInit;
    private Statuses status;
    private List<Move> moves;
    private int initiative;


    public Enemy() {
        this.stat = new Stat();
        this.initiative = 10;
        this.status = new Statuses();
        this.moves = new ArrayList<>();
        this.moves.add(new Move(GameEnum.MoveType.ATTACK, 2, 100, "Slash"));
        this.moves.add(new Move(GameEnum.MoveType.DEFEND, 2, 75, "Shield"));
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

    public Move move() {
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
        return null;
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
        enemy.move();
        System.out.println(enemy.getDefense());
        System.out.println(enemy.getSpeed());
        System.out.println(enemy.stance);
    }
}
