class Troll implements Thing {
    private static final String name = "Troll";
    private final String state;
    private final int stateIndex;

    Troll() {
        this.state = "Troll lurks in the shadows.";
        this.stateIndex = 0;
    }

    Troll(int stateIndex) {
        this.stateIndex = stateIndex;
        if (stateIndex == 0) {
            this.state = "Troll lurks in the shadows.";
        } else if (stateIndex == 1) {
            this.state = "Troll is getting hungry.";
        } else if (stateIndex == 2) {
            this.state = "Troll is VERY hungry.";
        } else if (stateIndex == 1 + 2) {
            this.state = "Troll is SUPER HUNGRY and is about to ATTACK!";
        } else {
            this.state = "Troll attacks!";
        }
    }

    @Override
    public String identify() {
        return this.name;
    }

    @Override
    public Troll tick() {
        return new Troll(this.stateIndex + 1);
    }

    @Override
    public String toString() {
        return this.state;
    }
}
