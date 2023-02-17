import java.util.Optional;

class Roster extends KeyableMap<Student> implements Keyable {

    Roster(String year) {
        super(year);
    }

    Roster(String year, ImmutableMap<String, Student> students) {
        super(year, students);
    }

    @Override
    Roster put(Student student) {
        ImmutableMap<String, Student> newMap = 
            super.getMap().put(student.getKey(), student);
        return new Roster(super.getKey(), newMap);
    }

    String getGrade(String studentName, String moduleName,
            String assessmentName) {
        return this.get(studentName).flatMap(x -> x.get(moduleName))
            .flatMap(x -> x.get(assessmentName))
            .map(x -> x.getGrade())
            .orElse("No such record: " + studentName + 
                    " " + moduleName + " " + assessmentName);
    }

    Roster add(String studentName, String moduleName, String assessmentName,
            String grade) {
        Student student = this.get(studentName).orElse(new Student(studentName));
        Module module = student.get(moduleName).orElse(new Module(moduleName));
        return this.put(student.put(module.put(new 
                        Assessment(assessmentName, grade))));                             
    }
}
