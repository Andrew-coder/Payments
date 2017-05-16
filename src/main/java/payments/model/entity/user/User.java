package payments.model.entity.user;

import payments.model.entity.BaseEntity;

import java.util.Date;

/**
 * This class represents user in this system
 * Depending on the role, user can have different privileges
 */
public class User extends BaseEntity {
    private String name;
    private String surname;
    private String cellphone;
    private String password;
    private Date birthDate;
    private RoleType role;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCellphone() {
        return cellphone;
    }

    public String getPassword() {
        return password;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public RoleType getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (cellphone != null ? !cellphone.equals(user.cellphone) : user.cellphone != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (birthDate != null ? !birthDate.equals(user.birthDate) : user.birthDate != null) return false;
        return role == user.role;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (cellphone != null ? cellphone.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", password='" + password + '\'' +
                ", birthDate=" + birthDate +
                ", role=" + role +
                '}';
    }

    public static class Builder{
        User instance = new User();

        public Builder setId(long id){
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

        public Builder setCellphone(String cellphone){
            instance.cellphone = cellphone;
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