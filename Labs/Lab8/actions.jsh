UnaryOperator<Room> takeSword = room -> {
    if (!room.contains("Sword")) {
        System.out.println("--> There is no sword.");
        return room;
    }

    if (room.swordTaken()) {
        System.out.println("--> You already have sword.");
        return room;
    } else {
        System.out.println("--> You have taken sword.");
        return new Room(room, true);
    }
};

UnaryOperator<Room> dropSword = room -> {
    System.out.println("--> You have dropped sword.");
    return new Room(room, false);
}

UnaryOperator<Room> killTroll = room -> {
    if (!room.contains("Troll")) {
        System.out.println("--> There is no troll.");
        return room;
    }

    if (!room.swordTaken()) {
        System.out.println("--> You have no sword.");
        return room;
    }

    System.out.println("--> Troll is killed.");
    return room.remove("Troll");
};
