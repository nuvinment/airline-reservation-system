// FileName: /Customer.java
// FileContents:
package Model;

public class Customer {
    private int customerId;
    private String cFirstName;
    private String cLastName;
    private String cUsername;
    private String gender;
    private String nic; // Changed from nicNo to nic for consistency with DAO
    private String cEmail;
    private String cPhoneNo;
    private String cAddress;
    private String password;

    // ✅ ADDED: No-argument constructor to allow instantiation before setting properties
    public Customer() {
    }

    // Your existing "Empty constructor" - this one is a bit misleading in its name
    // It takes 4 arguments but doesn't assign them to fields.
    // If this is truly meant to be an "empty" constructor, it should be `public Customer() {}`.
    // If it's meant to initialize some fields, it should assign them.
    // For the purpose of fixing the DAO error, the `public Customer() {}` above is sufficient.
    // I'm keeping this one as is, assuming it has a specific, albeit unusual, purpose.
    public Customer(int aInt, String string, String string1, String string2) {
    }

    // Full Constructor (for retrieving from DB)
    public Customer(int customerId, String cFirstName, String cLastName, String cUsername,
                  String gender, String nic, String cEmail, String cPhoneNo,
                  String cAddress, String password) {
        this.customerId = customerId;
        this.cFirstName = cFirstName;
        this.cLastName = cLastName;
        this.cUsername = cUsername;
        this.gender = gender;
        this.nic = nic;
        this.cEmail = cEmail;
        this.cPhoneNo = cPhoneNo;
        this.cAddress = cAddress;
        this.password = password;
    }

    // Constructor without ID (for adding new customers)
    public Customer(String cFirstName, String cLastName, String cUsername,
                  String gender, String nic, String cEmail, String cPhoneNo,
                  String cAddress, String password) {
        this.cFirstName = cFirstName;
        this.cLastName = cLastName;
        this.cUsername = cUsername;
        this.gender = gender;
        this.nic = nic;
        this.cEmail = cEmail;
        this.cPhoneNo = cPhoneNo;
        this.cAddress = cAddress;
        this.password = password;
    }

    // Getters
    public int getCustomerId() {
        return customerId;
    }

    public String getcFirstName() {
        return cFirstName;
    }

    public String getcLastName() {
        return cLastName;
    }

    public String getcUsername() {
        return cUsername;
    }

    public String getGender() {
        return gender;
    }

    public String getNic() {
        return nic;
    }

    public String getcEmail() {
        return cEmail;
    }

    public String getcPhoneNo() {
        return cPhoneNo;
    }

    public String getcAddress() {
        return cAddress;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setcFirstName(String cFirstName) {
        this.cFirstName = cFirstName;
    }

    public void setcLastName(String cLastName) {
        this.cLastName = cLastName;
    }

    public void setcUsername(String cUsername) {
        this.cUsername = cUsername;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public void setcEmail(String cEmail) {
        this.cEmail = cEmail;
    }

    public void setcPhoneNo(String cPhoneNo) {
        this.cPhoneNo = cPhoneNo;
    }

    public void setcAddress(String cAddress) {
        this.cAddress = cAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", cFirstName='" + cFirstName + '\'' +
                ", cLastName='" + cLastName + '\'' +
                ", cUsername='" + cUsername + '\'' +
                ", gender='" + gender + '\'' +
                ", nic='" + nic + '\'' +
                ", cEmail='" + cEmail + '\'' +
                ", cPhoneNo='" + cPhoneNo + '\'' +
                ", cAddress='" + cAddress + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
