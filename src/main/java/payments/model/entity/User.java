package payments.model.entity;

import java.util.Date;

public class User extends BaseEntity{
    private String name;
    private String surname;
    private String email;
    private String password;
    private Date birthDate;
    private RoleType role;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public static class Builder{
        User instance = new User();

        public Builder setId(int id){
            instance.setId(id);
            return this;
        }

        public Builder setName(String name){
            instance.name = name;
            return this;
        }

        public Builder setSurname(String surname){
            instance.surname = surname;
            return this;
        }

        public Builder setEmail(String email){
            instance.email = email;
            return this;
        }

        public Builder setPassword(String password){
            instance.password=password;
            return this;
        }

        public Builder setBirthDate(Date birthDate){
            instance.birthDate = birthDate;
            return this;
        }

        public Builder setRole(RoleType role){
            instance.role = role;
            return this;
        }

        public User build(){
            return instance;
        }
    }
}
