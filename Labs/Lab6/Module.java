class Module extends KeyableMap<Assessment> implements Keyable {

    Module(String name) {
        super(name);
    }

    Module(String name, ImmutableMap<String, Assessment> assessments) {
        super(name, assessments);
    }   

    @Override
    Module put(Assessment assessment) {
        ImmutableMap<String, Assessment> newMap = 
            super.getMap().put(assessment.getKey(), assessment);
        return new Module(super.getKey(), newMap);
    }
}
