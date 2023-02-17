import java.util.Optional;
import java.util.Map;

class Module implements Keyable {
    private final String name;
    private final ImmutableMap<String, Assessment> assessments;

    Module(String name) {
        this.name = name;
        this.assessments = new ImmutableMap<String, Assessment>();
    }

    Module(String name, ImmutableMap<String, Assessment> assessments) {
        this.name = name;
        this.assessments = assessments;
    }

    @Override
    public String getKey() {
        return this.name;
    }

    Module put(Assessment assessment) {
        ImmutableMap<String, Assessment> newMap = 
            this.assessments.put(assessment.getKey(), assessment);
        return new Module(this.name, newMap);
    }

    Optional<Assessment> get(String key) {
        return this.assessments.get(key);
    }

    @Override 
    public String toString() {
        String string = this.getKey() + ": {";
        boolean isFirst = true;
        for (Map.Entry<String, Assessment> e : this.assessments) {
            if (!isFirst) {
                string += ", ";
            }
            string += e.getValue();
            isFirst = false;
        }
        string += "}";
        return string;
    }
}
