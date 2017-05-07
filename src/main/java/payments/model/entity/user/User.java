package payments.model.entity.user;

import payments.model.entity.BaseEntity;

import java.util.Date;

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