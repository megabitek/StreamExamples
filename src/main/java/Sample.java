import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Sample {
    private final List<User> userList = Arrays.asList(
            new User(1, "Michael", "Robert", 37, "TR"),
            new User(2, "Mary", "Patricia", 11, "EN"),
            new User(3, "John", "Michael", 7, "FR"),
            new User(4, "Jennifer", "Linda", 77, "TR"),
            new User(5, "William", "Elizabeth", 23, "US"),
            new User(6, "Sue", "Jackson", 11, "IT"),
            new User(7, "Michael", "Tommy", 37, "EN")
    );

    public static void main(String... args) {
        Sample sample = new Sample();
        sample.testStringPool();
    }


/**Перебор всех элементов userList с помощью forEach() и вывод их в консоль.*/

    private void test1() {
        System.out.println("Test 1");
        userList.stream()
                .forEach(System.out::println);
    }
/**Перебор всех элементов с выполнением некоторой операции над каждым элементом списка и вывод их на консоль.**/
    private void test2() {
        System.out.println("Test 2");
        userList.stream()
                .map(u -> {
                    return new User(u.getId(),
                            "X " + u.getFirstName(),
                            "Y " + u.getLastName(),
                            u.getAge() + 10,
                    u.getNationality());
                })
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }



/**Сортировка списка по свойству age*/
    private void test3() {
        System.out.println("Test 3");
        userList.stream()
                .sorted(Comparator.comparing(User::getAge))
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }


/**Сортировка списка по нескольким свойствам: age, firstName, lastName**/
    private void test4() {
        System.out.println("Test 4");
        userList.stream()
                .sorted(Comparator.comparing(User::getAge)
                        .thenComparing(User::getFirstName)
                        .thenComparing(User::getLastName))
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }
/**Вычисление среднего возраста (age) и максимальной длины firstName.**/

    private void test5() {
        System.out.println("Test 5");
        double averageAge = userList.stream()
                .mapToInt(User::getAge)
                .summaryStatistics()
                .getAverage();
        System.out.print("averageAge: " + averageAge);
        int maxFirstNameLenght = userList.stream()
                .mapToInt((value) -> {
                    return value.getFirstName().length();
                })
                .summaryStatistics()
                .getMax();
        System.out.println(" maxFirstNameLenght: " + maxFirstNameLenght);
    }

    /**Проверка, что у всех User возраст (age) больше 6.*/

    private void test6() {
        System.out.println("Test 6");
        boolean isAllAgesGreaterThan6 = userList.stream()
                .allMatch(user -> user.getAge() > 6);
        System.out.println("isAllAgesGreaterThan6: " +     isAllAgesGreaterThan6);
    }

/**Проверка, есть ли кто-то с firstName, начинающийся с символа S. */
    private void test7() {
        System.out.println("Test 7");
        boolean isFirstCharS = userList.stream()
                .anyMatch(user -> user.getFirstName().charAt(0) == 'S');
        System.out.println("isFirstCharS " + isFirstCharS);
    }
/**Преобразование одной коллекцию в другую.**/
    private void test8() {
        System.out.println("Test 8");
        List<User> list = userList.stream()
                .collect(Collectors.toList());
        Set<User> set = userList.stream()
                .collect(Collectors.toSet());
        List<User> linkedList = userList.stream()
                .collect(Collectors.toCollection(LinkedList::new));
        Map<Long, User> map = userList.stream()
                .collect(Collectors.toMap(user -> user.getId(), user -> user));
    }


    /** Количество разных национальностей (nationality).**/
    private void test9() {
        long countDifferentNationalites = userList.stream()
                .map(User::getNationality)
                .distinct()
                .count();
        System.out.println("countDifferentNationalites: " + countDifferentNationalites);
    }



    private void flatMapExample(){

        String[][] array = new String[][]{{"a", "b"}, {"c", "d"}, {"e", "f"}};

        List<String> collect = Stream.of(array)     // Stream<String[]>
                .flatMap(Stream::of)                // Stream<String>
              //  .filter(x -> !"a".equals(x))        // filter out the a
                .collect(Collectors.toList());      // return a List

        collect.forEach(System.out::println);
    }
private void flatMapExample2(){

    Developer o1 = new Developer();
    o1.setName("mkyong");
    o1.addBook("Java 8 in Action");
    o1.addBook("Spring Boot in Action");
    o1.addBook("Effective Java (3nd Edition)");

    Developer o2 = new Developer();
    o2.setName("lap");
    o2.addBook("Learning Python, 5th Edition");
    o2.addBook("Effective Java (3nd Edition)");

    List<Developer> list = new ArrayList<>();
    list.add(o1);
    list.add(o2);

    // hmm....Set of Set...how to process?
        /*Set<Set<String>> collect = list.stream()
                .map(x -> x.getBook())
                .collect(Collectors.toSet());*/

    Set<String> collect =
            list.stream()
                    .map(x -> x.getBook())                              //  Stream<Set<String>>
                    .flatMap(x -> x.stream())                           //  Stream<String>
                 //   .filter(x -> x.toLowerCase().contains("python"))   //  filter python book
                    .collect(Collectors.toSet());

    List<String> coll = list.stream().map(dev->dev.getBook()).flatMap(books->books.stream()).map(x->x.toUpperCase()).collect(Collectors.toList());

   list.stream().mapToInt(dev->dev.getName().length()).forEach(x->System.out.println(x));
  //  coll.forEach(System.out::println);
    Predicate <Developer> longName =  dev->dev.getName().length()>4; // какое то условие связанное с объектом
Predicate <Developer> hasBook = dev->dev.getBook().size()>0;
    System.out.println(longName.negate().test(o2));
    longName.and(hasBook); //предикат и




}   private void  testPredicate(){
        BinaryOperator<Integer>  add=(x,y)-> x+y;
System.out.println(add.apply(3,5));

BiFunction<Integer, Integer, Integer> incr = (z, y)->z+y++;

BiFunction<Integer, Integer, Integer> andThen  = incr.andThen(z->z+89);

System.out.println(andThen.apply(5,3));

    }
private void consumerTest(){
    Consumer<List> addNull = x->x.add(null); // принимает объект ничего не возвращает

    Supplier<List> newList = ()->new ArrayList(); //ничего не принимает, возвращает объект

//    BinaryOperator<T> принимает в качестве параметра два объекта типа T,
//    выполняет над ними бинарную операцию и возвращает ее результат также в виде объекта типа T:
BinaryOperator <Developer> addBook = (dev1, dev2)-> {
    dev1.getBook().addAll(dev2.getBook());
    return dev1;
};
 UnaryOperator<Developer> NameToLowerCase = (dev)->{dev.setName(dev.getName().toLowerCase()); return dev; };

}

private void testStringPool(){
        String str1= "String";
        String str2 ="String";
        System.out.println(str1==str2);
        str2 = "string";
        System.out.println(str1);

     str1 = "TopJava";
     str2 = "Top" + "Java";

    System.out.println("Строка 1 равна строке 2? " + (str1 == str2));// true

     str1 = "TopJava";
     str2 = "Java";
     String str3 = "Top" + str2;

    System.out.println("Строка 1 равна строке 3? " + (str1 == str3));
    //Причиной получения false является то,
    // что интернирование происходит не во время работы приложения (runtime),
    // а во время компиляции. А т.к. значение строки str3 вычисляется во время выполнения приложения,
    // то на этапе компиляции оно не известно и потому, не добавляется в пул строк.
}
}
