import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> lastNames = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");

        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                            names.get(new Random().nextInt(names.size())),
                            lastNames.get(new Random().nextInt(lastNames.size())),
                            new Random().nextInt(100),
                            Sex.values()[new Random().nextInt(Sex.values().length)],
                            Education.values()[new Random().nextInt(Education.values().length)]
                    )
            );
        }

        long underagePersons = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println("The number of underage persons: " + underagePersons);

        List<String> inducteesLastNames = persons.stream()
                .filter(person -> person.getAge() >= 18 && person.getAge() <= 27)
                .map(Person::getLastName)
                .collect(Collectors.toList());

        Predicate<Person> condition = person -> {
            if (person.getEducation() != Education.HIGHER || person.getAge() < 18) {
                return false;
            }
            if (person.getSex().equals(Sex.WOMAN) && person.getAge() <= 60) {
                return true;
            } else return person.getSex().equals(Sex.MAN) && person.getAge() <= 65;
        };

        List<Person> ableBodied = persons.stream()
                .filter(condition)
                .sorted(Comparator.comparing(Person::getLastName))
                .collect(Collectors.toList());

        ableBodied.stream()
                .limit(50)
                .forEach(System.out::println);
    }
}
