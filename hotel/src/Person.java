public abstract class Person {
    private String name;
    private int age;
    private String ID;
    private String gender;
    private String password;
    private boolean isBaned;
    public Person(){
        isBaned = false;
    }

    public boolean getIsBaned() {
        return isBaned;
    }

    public void setIsBaned(boolean baned) {
        isBaned = baned;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public void personInfoPrint(){
        System.out.println("Name: " + getName());
        System.out.println("Age: " + getAge());
        System.out.println("ID: " + getID());
        System.out.println("Gender: " + getGender());
    }

}
