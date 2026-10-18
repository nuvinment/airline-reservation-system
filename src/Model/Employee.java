/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class Employee {
    private int employeeId;
    private String eName;
    private String eEmail;
    private String ePhoneNumber;
    private String eAddress;
    private String eUsername;
    private String ePassword;

    public Employee(int employeeId, String eName, String eEmail, String ePhoneNumber, String eAddress, String eUsername, String ePassword) {
        this.employeeId = employeeId;
        this.eName = eName;
        this.eEmail = eEmail;
        this.ePhoneNumber = ePhoneNumber;
        this.eAddress = eAddress;
        this.eUsername = eUsername;
        this.ePassword = ePassword;
    }

    // Getters and Setters
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String geteEmail() {
        return eEmail;
    }

    public void seteEmail(String eEmail) {
        this.eEmail = eEmail;
    }

    public String getePhoneNumber() {
        return ePhoneNumber;
    }

    public void setePhoneNumber(String ePhoneNumber) {
        this.ePhoneNumber = ePhoneNumber;
    }

    public String geteAddress() {
        return eAddress;
    }

    public void seteAddress(String eAddress) {
        this.eAddress = eAddress;
    }

    public String geteUsername() {
        return eUsername;
    }

    public void seteUsername(String eUsername) {
        this.eUsername = eUsername;
    }

    public String getePassword() {
        return ePassword;
    }

    public void setePassword(String ePassword) {
        this.ePassword = ePassword;
    }

    

    
}
