import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static List<User> users = new ArrayList<>();

    static {
        users.add(new User(8,"bm",28,Byte.valueOf("2")));
        users.add(new User(5,"good",25,Byte.valueOf("2")));
        users.add(new User(1,"smy",20,Byte.valueOf("2")));
        users.add(new User(2,"xiongda",21,Byte.valueOf("1")));
        users.add(new User(6,"xblixqq",26,Byte.valueOf("1")));
        users.add(new User(7,"qtq",27,Byte.valueOf("1")));
        users.add(new User(11,"fd",10,Byte.valueOf("2")));
        users.add(new User(3,"xionger",23,Byte.valueOf("2")));
        users.add(new User(10,"jm",30,Byte.valueOf("1")));
        users.add(new User(4,"xiongsan",24,Byte.valueOf("1")));
        users.add(new User(12,"dd",15,(byte)0));
        users.add(new User(9,"tg",29,Byte.valueOf("2")));
    }

    public static void main(String[] args) {

/**      users.stream().filter((p)->p.getId()>=5).forEach((p)-> System.out.println(p));
*       (p1,p2)->p1.getId()-p2.getId()*/
        users.stream().filter((p)->p.getAge()>20).sorted(Comparator.comparingInt((p)->p.getId())).forEach((p)-> System.out.println(p));
        users.stream().map((p) -> p.getName()).collect(Collectors.toSet()).forEach(s-> System.out.println(s));
/*        for (Object object : objects) {
            System.out.println(object);
        }*/
    }

}
class User{

    private Integer id;

    private String name;

    private Integer age;

    private byte sex;

    public User(Integer id, String name, Integer age, byte sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
