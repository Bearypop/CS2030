package cs2030.simulator;

import java.util.List;
import cs2030.util.ImList;
import cs2030.util.Pair;
import java.util.function.Supplier;

public class Shop {
    private final ImList<Server> servers;
    private final boolean hasQueue;

    public Shop(List<Server> servers) {
        this.servers = ImList.<Server>of(servers);
        this.hasQueue = false;
    }

    public Shop(ImList<Server> servers) {
        this.servers = servers;
        this.hasQueue = false;
    }

    public Shop(int servers) {
        ImList<Server> newList = ImList.<Server>of();
        for (int i = 1; i <= servers; i++) {
            newList = newList.add(new Server(i));
        }
        this.servers = newList;
        this.hasQueue = false;
    }

    public Shop(int servers, int qmax) {
        ImList<Server> newList = ImList.<Server>of();
        for (int i = 1; i <= servers; i++) {
            newList = newList.add(new Server(i, qmax));
        }
        this.servers = newList;
        this.hasQueue = false;
    }


    public Shop(int servers, int qmax, Supplier<Double> restTimes) {
        ImList<Server> newList = ImList.<Server>of();
        for (int i = 1; i <= servers; i++) {
            newList = newList.add(new Server(i, qmax, restTimes));
        }
        this.servers = newList;
        this.hasQueue = false;
    }

    public Shop(int servers, int selfChecks, int qmax, Supplier<Double> restTimes) {
        ImList<Server> newList = ImList.<Server>of();
        for (int i = 1; i <= servers + selfChecks; i++) {
            if (i <= servers) {
                newList = newList.add(new Server(i, qmax, restTimes));
            } else {
                newList = newList.add(new SelfCheck(i, qmax, restTimes));
            }
        }
        this.servers = newList;
        this.hasQueue = false;
    }

    public Shop(Shop shop) {
        this.servers = ImList.<Server>of(shop.servers);
        this.hasQueue = shop.hasQueue;
    }

    public Shop(ImList<Server> servers, boolean hasQueue) {
        this.servers = servers;
        this.hasQueue = hasQueue;
    }

    public boolean hasAvailableServer() {
        for (Server i : this.servers) {
            if (i.isBusy() == false) {
                return true;
            }
        }
        return false;
    }

    // only call when there is an available server
    public Pair<Server, Shop> arrive() {
        ImList<Server> newList = ImList.<Server>of(this.servers);
        int i;
        for (i = 0; i < this.servers.size(); i++) {
            if (!this.servers.get(i).isBusy()) {
                break;
            }
        }
        return Pair.<Server, Shop>of(this.servers.get(i), this);
    }

    // call when there is no queue and no available server
    public Pair<Server, Shop> waits(int customerID) {
        ImList<Server> newList = ImList.<Server>of(this.servers);
        Server server = new Server(1);
        int i;
        for (i = 0; i < newList.size(); i++) {
            if (!newList.get(i).hasFullQueue()) {
                newList = newList.set(i, newList.get(i).addWait(customerID));
                server = newList.get(i);
                if (!server.isServer()) {
                    for (int j = i + 1; j < newList.size(); j++) {
                        newList = newList.set(j, newList.get(j).updateSharedQueue(server));
                    }
                }
                break;
            }
        }
        if (i == newList.size() - 1 && server.hasFullQueue()) {
            return Pair.<Server, Shop>of(server, new Shop(newList, true));
        } else {
            return Pair.<Server, Shop>of(server, new Shop(newList));
        }
    }

    public Pair<Server, Shop> serve(int serverID) {
        ImList<Server> newList = ImList.<Server>of(this.servers);
        Server newServer;
        if (newList.get(serverID - 1).isServer()) {
            newServer =  new Server(serverID, true,
                    newList.get(serverID - 1).hasWait(),
                    newList.get(serverID - 1).getWaitingQueue(),
                    newList.get(serverID - 1).getQmax(),
                    newList.get(serverID - 1).getRestTimes());
            newList = newList.set(serverID - 1, newServer);
        } else {
            newServer = new SelfCheck(serverID, true,
                    newList.get(serverID - 1).hasWait(),
                    newList.get(serverID - 1).getWaitingQueue(),
                    newList.get(serverID - 1).getQmax(),
                    newList.get(serverID - 1).getRestTimes());
            newList = newList.set(serverID - 1, newServer);
        }
        return Pair.<Server, Shop>of(newServer, new Shop(newList));
    }

    // call from Done event
    public Pair<Server, Shop> serveWait(int serverID) {
        ImList<Server> newList = ImList.<Server>of(this.servers);
        Server newServer = newList.get(serverID - 1).removeWait();
        newList = newList.set(serverID - 1, newServer);
        if (!newServer.isServer()) {
            for (int i = 0; i < newList.size(); i++) {
                if (!newList.get(i).isServer()) {
                    newList = newList.set(i, newList.get(i).updateSharedQueue(newServer));
                }
            }
        }
        return Pair.<Server, Shop>of(newServer, new Shop(newList));
    }

    //call from done event
    public Shop done(int serverID) {
        ImList<Server> newList = ImList.<Server>of(this.servers);
        if (newList.get(serverID - 1).isServer()) {
            newList = newList.set(serverID - 1, new Server(serverID, false, 
                        newList.get(serverID - 1).hasWait(), 
                        newList.get(serverID - 1).getWaitingQueue(),
                        newList.get(serverID - 1).getQmax(),
                        newList.get(serverID - 1).getRestTimes()));
        } else {
            newList = newList.set(serverID - 1, new SelfCheck(serverID, false, 
                        newList.get(serverID - 1).hasWait(), 
                        newList.get(serverID - 1).getWaitingQueue(),
                        newList.get(serverID - 1).getQmax(),
                        newList.get(serverID - 1).getRestTimes()));
        }
        return new Shop(newList);
    }

    public boolean hasQueue() {
        for (Server i : this.servers) {
            if (!i.hasFullQueue()) {
                return false;
            }
        }
        return true;
    }

    public boolean checkHasWait(int serverID) {
        return this.servers.get(serverID - 1).hasWait();
    }

    public int getWaitingCustomer(int serverID) {
        return this.servers.get(serverID - 1).getWaitingCustomer();
    }

    public double getRestTime(int serverID) {
        return this.servers.get(serverID - 1).getRestTimes().get();
    }

    public boolean checkIsServer(int serverID) {
        return this.servers.get(serverID - 1).isServer();
    }

    @Override
    public String toString() {
        return this.servers.toString();
    }
}
