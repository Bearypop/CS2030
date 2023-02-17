import java.util.function.UnaryOperator;

class Room {
    private final String name;
    private final ImList<Thing> things;
    private final boolean swordTaken;
    private final Room previousRoom;

    Room(String name) {
        this.name = name;
        this.things = ImList.<Thing>of();
        this.swordTaken = false;
        this.previousRoom = this;
    }

    Room(Room room, Thing thing) {
        this.name = room.name;
        this.things = room.things.add(thing);
        this.swordTaken = room.swordTaken;
        this.previousRoom = room.previousRoom;
    }

    Room(Room room, ImList<Thing> things) {
        this.name = room.name;
        this.things = things;
        this.swordTaken = room.swordTaken;
        this.previousRoom = room.previousRoom;
    }

    Room(Room room, boolean swordTaken) {
        this.name = room.name;
        this.things = room.things;
        this.swordTaken = swordTaken;
        this.previousRoom = room.previousRoom;
    }

    Room(String name, Room room) {
        this.name = name;
        this.things = room.things;
        this.swordTaken = room.swordTaken;
        this.previousRoom = room.previousRoom;
    }

    Room(Room room, Room previousRoom) {
        this.name = room.name;
        this.things = room.things;
        this.swordTaken = room.swordTaken;
        this.previousRoom = previousRoom;
    }

    Room add(Thing thing) {
        return new Room(this, thing);
    }

    Room remove(String thingName) {
        int index = 0;
        for (Thing i : this.things) {
            if (i.identify().equals(thingName)) {
                break;
            }
            index++;
        }
        return new Room(this, this.things.remove(index).second());
    }

    boolean contains(String thingName) {
        for (Thing i : this.things) {
            if (i.identify().equals(thingName)) {
                return true;
            }
        }
        return false;
    }

    boolean swordTaken() {
        return this.swordTaken;
    }

    Room tick() {
        ImList<Thing> newList = ImList.<Thing>of();
        for (Thing i : this.things) {
            newList = newList.add(i.tick());
        }
        return new Room(this, newList);
    }

    Room tick(UnaryOperator<Room> ticker) {
        return ticker.apply(this.tick());
    }

    Room go(UnaryOperator<Room> goer) {
        if (this.swordTaken()) {
            Room room = new Room(goer.apply(this), ImList.<Thing>of()
                    .add(new Sword()).addAll(goer.apply(this).things));
            return new Room(new Room(room, true), this.remove("Sword"));
        }
        return new Room(goer.apply(this), this);
    }

    Room back() {
        if (this.previousRoom.name.equals(this.name)) {
            return this;
        }
        if (this.swordTaken()) {
            return new Room(new Room(this.previousRoom.tick(), this.previousRoom.tick().things
                        .add(new Sword())), true);
        }
        return new Room(this.previousRoom.tick(), this.swordTaken);
    }

    @Override
    public String toString() {
        String string = "@" + this.name;
        for (Thing i : things) {
            string += "\n" + i;
        }
        return string;
    }
}
