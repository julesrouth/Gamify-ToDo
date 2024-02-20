import java.util.Arrays;

public class Stances {
    public enum StanceEnum {
        ATTACK(1),
        DEFEND(2),
        AGGRO(3),
        WATER(4),
        FIRE(5);

        private final int value;

        StanceEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            String temp = "Hello";
            temp = temp.repeat(3);
            return value;
        }
    }

    public static class StanceArray {
        private double[] stance;

        public StanceArray(double attack, double defend, double aggro, double water, double fire) {
            this.stance = new double[]{attack, defend, aggro, water, fire};
        }

        public String strWeakness() {
            StringBuilder temp = new StringBuilder();
            for (int i = 0; i < stance.length; i++) {
                if (stance[i] < 1) {
                    temp.append(StanceEnum.values()[i]).append(" ");
                }
            }
            return temp.toString();
        }

        public String strStrength() {
            StringBuilder temp = new StringBuilder();
            for (int i = 0; i < stance.length; i++) {
                if (stance[i] > 1) {
                    temp.append(StanceEnum.values()[i]).append(" ");
                }
            }
            return temp.toString();
        }

        public double get(int key) {
            return stance[key];
        }

        

        @Override
        public String toString() {
            StringBuilder tempStr = new StringBuilder();
            for (double i : stance) {
                tempStr.append(i).append(" ".repeat(Math.max(0, 10 - String.valueOf(i).length()))).append("|");
            }
            return tempStr.toString();
        }
    }

    public static class StanceGrid {
        private String[] nameList = {"Attack", "Defend", "Aggro", "Water", "Fire"};
        private StanceArray[] grid;

        public StanceGrid() {
            this.grid = new StanceArray[]{
                    new StanceArray(0, 1, 1, 0, 0.5),
                    new StanceArray(1.5, 0, 0.5, 0, 0),
                    new StanceArray(0.5, 1.5, 0, 0, 0),
                    new StanceArray(0, 0, 0, 0, 1.5),
                    new StanceArray(0, 0, 0, 1.5, 0.5)
            };
        }

        public double getStance(int stance1, int stance2) {
            return grid[stance1].get(stance2);
        }

        public double getStance(StanceEnum stance1, StanceEnum stance2) {
            return grid[stance1.getValue() - 1].get(stance2.getValue() - 1);
        }

        public String strGrid() {
            StringBuilder tempStr = new StringBuilder();
            int j = 0;
            tempStr.append(" ".repeat(12)).append("-".repeat((11 * nameList.length) + 1)).append("\n");
            tempStr.append(" ".repeat(11)).append("|");
            for (String i : nameList) {
                tempStr.append(i).append(" ".repeat(Math.max(0, 10 - i.length()))).append("|");
            }
            tempStr.append("\n");
            tempStr.append("-".repeat((11 * (nameList.length + 1)) + 2)).append("\n");
            for (StanceArray i : grid) {
                String tempNameStr = "| " + nameList[j] + " ".repeat(Math.max(0, 10 - nameList[j].length())) + "|";
                String tempTotalStr = tempNameStr + i.toString() + "\n";
                String tempLineStr = "-".repeat(tempTotalStr.length() - 1) + "\n";
                j++;
                tempStr.append(tempTotalStr).append(tempLineStr);
            }
            return tempStr.toString();
        }
    }

    public static void main(String[] args) {
        System.out.println("Testing Stance");
        StanceArray stance = new StanceArray(1, 1, 1, 1, 1);
        System.out.println(Arrays.toString(stance.stance));
        System.out.println(stance.strWeakness());
        System.out.println(stance.strStrength());
        StanceGrid stanceGrid = new StanceGrid();
        System.out.println("Testing StanceGrid");
        System.out.println("GRID: " + Arrays.toString(stanceGrid.grid));
        System.out.println("VALUE: " + stanceGrid.getStance(0, 1));
        System.out.println(stanceGrid.strGrid());
    }
}
