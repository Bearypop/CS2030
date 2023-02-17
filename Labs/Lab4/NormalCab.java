class NormalCab extends Driver {

    NormalCab(String licensePlate, int waitingTime) {
        super(licensePlate, waitingTime);
    }

    @Override
    Service selectService(Request request) {
        return (new JustRide().computeFare(request) < 
                new TakeACab().computeFare(request)) ? new JustRide() : new TakeACab();
    }

    @Override
    int getSelectedServiceFare(Request request) {
        return this.selectService(request).computeFare(request);
    }

    @Override
    Service notSelectedService(Request request) {
        return (new JustRide().computeFare(request) > 
                new TakeACab().computeFare(request)) ? new JustRide() : new TakeACab();
    }

    @Override
    int getNotSelectedServiceFare(Request request) {
        return this.notSelectedService(request).computeFare(request);
    }

    @Override
    public String toString() {
        return String.format("%s (%d mins away) NormalCab",
                super.getLicensePlate(), super.getWaitingTime());
    }
}
