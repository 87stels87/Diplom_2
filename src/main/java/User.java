import com.github.javafaker.Faker;

public class User {
    private String name;
    private String password;
    private String email;

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public User(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public static User getRandomFullUser() {
        Faker faker = new Faker();
        final String name = faker.pokemon().name();
        final String password = faker.lorem().characters(10);
        final String email = faker.internet().emailAddress();
        return new User(name, password, email);
    }

    public static User getRandomUserWithoutPassword() {
        Faker faker = new Faker();
        final String name = faker.gameOfThrones().dragon();
        final String email = faker.internet().emailAddress();
        final String password = null;
        return new User(name, password, email);
    }

    public static User getRandomUserWithoutName() {
        Faker faker = new Faker();
        final String password = faker.lorem().characters(10);
        final String email = faker.internet().emailAddress();
        final String name = null;
        return new User(name, password, email);
    }

    public static User getRandomUserWithoutEmail() {
        Faker faker = new Faker();
        final String name = faker.gameOfThrones().dragon();
        final String password = faker.lorem().characters(10);
        final String email = null;
        return new User(name, password, email);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
