import java.util.List;

void serveCruises(List<Cruise> cruises) {
    ImList<Loader> loaders = new ImList<Loader>();
    for (Cruise i : cruises) {
        int loadersNeeded = i.getNumOfLoadersRequired();
        for (int j = 0; j < loaders.size(); j++) {
            if (loaders.get(j).canServe(i) && loadersNeeded > 0) {
                loaders = loaders.set(j, loaders.get(j).serve(i));
                System.out.println(loaders.get(j));
                loadersNeeded--;
            }
        }

        if (loadersNeeded > 0) {
            for (int h = 0; h < loadersNeeded; h++) {
                System.out.println(new Loader(loaders.size() + 1, i));
                loaders = loaders.add(new Loader(loaders.size() + 1, i)); 
            }
        }
    }
}
