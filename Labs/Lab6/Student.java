class Student extends KeyableMap<Module> implements Keyable {
   
    Student(String name) {
        super(name);
    }

    Student(String name, ImmutableMap<String, Module> modules) {
        super(name, modules);
    }

    @Override
    Student put(Module module) {
        ImmutableMap<String, Module> newMap = 
            super.getMap().put(module.getKey(), module);
        return new Student(super.getKey(), newMap);
    }
}
