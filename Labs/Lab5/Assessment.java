class Assessment implements Keyable {
    private final String title;
    private final String grade;

    Assessment(String title, String grade) {
        this.title = title;
        this.grade = grade;
    }

    String getGrade() {
        return this.grade;
    }

    @Override
    public String getKey() {
        return this.title;
    }

    @Override
    public String toString() {
        return "{" + this.title + ": " + this.grade + "}";
    }
}
