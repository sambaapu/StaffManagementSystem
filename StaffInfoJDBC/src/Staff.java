import java.util.Objects;

public class Staff implements Comparable<Staff> {
    private String id;
    private String firstName;
    private String lastName;
    private char mi;
    private int age;
    private String address;
    private String city;
    private String state;
    private String telephone;
    private String email;

    public Staff(String id, String lastName, String firstName, char mi, int age, String address, String city, String state, String telephone, String email) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.mi = mi;
        this.age = age;
        this.address = address;
        this.city = city;
        this.state = state;
        this.telephone = telephone;
        this.email = email;
    }

    // getters
    public String getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public char getMi() {
        return mi;
    }
    public int getAge() {
        return age;
    }
    public String getAddress() {
        return address;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public String getTelephone() {
        return telephone;
    }
    public String getEmail() {
        return email;
    }
    // setters
    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMi(char mi) {
        this.mi = mi;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int compareTo(Staff other) {
        return Integer.compare(this.age, other.age);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return age == staff.age && mi == staff.mi && Objects.equals(id, staff.id) && Objects.equals(lastName, staff.lastName) && Objects.equals(firstName, staff.firstName) && Objects.equals(address, staff.address) && Objects.equals(city, staff.city) && Objects.equals(state, staff.state) && Objects.equals(telephone, staff.telephone) && Objects.equals(email, staff.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, mi, age, address, city, state, telephone, email);
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id='" + id + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", mi=" + mi +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

