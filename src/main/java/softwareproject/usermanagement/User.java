package softwareproject.usermanagement;

public class User {
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private String email;
    private String imagePath;
    private String userType;

    public User() {
        this.username = "";
        this.firstName = "";
        this.lastName = "";
        this.phoneNumber = "";
        this.password = "";
        this.email = "";
        this.imagePath = "";
        this.userType = "";
    }

    public User(String username, String firstName, String lastName,
                String phoneNumber, String password, String email,
                String imagePath, String userType) {
        setUsername(username);
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
        setPassword(password);
        setEmail(email);
        setImagePath(imagePath);
        setUserType(userType);
    }

    public User(User user) {
        this(user.getUsername(), user.getFirstName(), user.getLastName(),
                user.getPhoneNumber(), user.getPassword(), user.getEmail(),
                user.getImagePath(), user.getUserType());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getImagePath() {
        return (imagePath.isEmpty() ? "C:\\Users\\User\\IdeaProjects\\software_project\\src\\main\\resources\\nouser.png" : imagePath);
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }



    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
