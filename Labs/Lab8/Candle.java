class Candle implements Thing {
    private static final String name = "Candle";
    private final String state;
    private final int stateIndex;

    Candle() {
        this.state = "Candle flickers.";
        this.stateIndex = 0;
    }

    Candle(int stateIndex) {
        this.stateIndex = stateIndex;
        if (stateIndex == 0) {
            this.state = "Candle flickers.";
        } else if (stateIndex == 1) {
            this.state = "Candle is getting shorter.";
        } else if (stateIndex == 2) {
            this.state = "Candle is about to burn out.";
        } else {
            this.state = "Candle has burned out.";
        }
    }

    @Override
    public String identify() {
        return this.name;
    }

    @Override
    public Candle tick() {
        return new Candle(this.stateIndex + 1);
    }

    @Override
    public String toString() {
        return this.state;
    }
}
