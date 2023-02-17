class Sword implements Thing {
    private static final String name = "Sword";
    private final String state;
    private final int stateIndex;

    Sword() {
        this.state = "Sword is shimmering.";
        this.stateIndex = 0;
    }

    Sword(int stateIndex) {
        this();
    }

    @Override
    public String identify() {
        return this.name;
    }

    @Override
    public Sword tick() {
        return new Sword(this.stateIndex + 1);
    }

    @Override
    public String toString() {
        return this.state;
    }
}
