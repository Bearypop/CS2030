import java.util.List;

void findBestBooking(Request request, List<Driver> drivers) {
    ImList<Booking> bookings = new ImList<Booking>();
    for (Driver i : drivers) {
        bookings = bookings.add(new Booking(i, request));
        bookings = bookings.add(new Booking(i, request, true));
    }

    for (int i = 0; i < bookings.size() - 1; i++) {
        int smallestIndex = i;
        for (int j = i + 1; j < bookings.size(); j++) {
            if (bookings.get(j).compareTo(bookings.get(smallestIndex)) == -1) {
                smallestIndex = j;
            }
        }
        Booking tmp = bookings.get(i);
        bookings = bookings.set(i, bookings.get(smallestIndex));
        bookings = bookings.set(smallestIndex, tmp);
    }

    for (Booking i : bookings) {
        System.out.println(i);
    }
}

