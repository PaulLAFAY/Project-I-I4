package commons;

public class User {

    private static int nbUser = 0;

    private String name;
    private int id;

    public User() {
        name = "default_name";
        id = nbUser;
        nbUser++;
    }

    public User(String name) {
        this.name = name;
        id = nbUser;
        nbUser++;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
