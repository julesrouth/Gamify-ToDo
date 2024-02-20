public class GameEnum {
    public enum Terrain {
        INVALID(-1),
        FOREST(0),
        WATER(1),
        MOUNTAIN(2);

        private final int value;

        Terrain(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public enum MoveType {
        ATTACK,
        DEFEND,
        SPELL,
        USEITEM
    }

    public static void main(String[] args) {
        System.out.println("Testing Enums");
        
        // Example usage of Terrain
        Terrain terrain = Terrain.FOREST;
        System.out.println("Terrain: " + terrain + ", Value: " + terrain.getValue());

        // Example usage of Direction
        Direction direction = Direction.UP;
        System.out.println("Direction: " + direction);

        // Example usage of MoveType
        MoveType moveType = MoveType.ATTACK;
        System.out.println("MoveType: " + moveType);
    }
}
