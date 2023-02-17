import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        Roster roster = new Roster("AY2122");

        for (int i = 0; i < n; i++) {
            String record = scanner.nextLine();
            record = record.replaceAll("\\s+", " ");
            ImList<Integer> whiteSpaceIndexes = new ImList<Integer>();

            for (int j = 0; j < record.length(); j++) {
                if (Character.isWhitespace(record.charAt(j))) {
                    whiteSpaceIndexes = whiteSpaceIndexes.add(j);
                }
            }

            String studentName = record.substring(0, whiteSpaceIndexes.get(0));
            String moduleName = record.substring(whiteSpaceIndexes.get(0) + 1,
                    whiteSpaceIndexes.get(1));
            String assessmentName = record.substring(whiteSpaceIndexes.get(1) + 1,
                    whiteSpaceIndexes.get(2));
            String grade = record.substring(whiteSpaceIndexes.get(2) + 1);
            roster = roster.add(studentName, moduleName, assessmentName, grade);
        }

        while (scanner.hasNext()) {
            String query = scanner.nextLine();
            query = query.replaceAll("\\s+", " ");
            ImList<Integer> whiteSpaceIndexes = new ImList<Integer>();

            for (int i = 0; i < query.length(); i++) {
                if (Character.isWhitespace(query.charAt(i))) {
                    whiteSpaceIndexes = whiteSpaceIndexes.add(i);
                }
            }

            String studentName = query.substring(0, whiteSpaceIndexes.get(0));
            String moduleName = query.substring(whiteSpaceIndexes.get(0) + 1,
                    whiteSpaceIndexes.get(1));
            String assessmentName = query.substring(whiteSpaceIndexes.get(1) + 1);
            System.out.println(roster.getGrade(studentName, moduleName, assessmentName));
        }
    }
}
