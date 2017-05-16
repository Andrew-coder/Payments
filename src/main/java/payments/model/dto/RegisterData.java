package payments.model.dto;

/**
 * This class represents DTO to transfer register data to UI
 */
public class RegisterData extends LoginData {
    private String name;
    private String surname;
    private String birthDate;
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public static class Builder{
        RegisterData instance = new RegisterData();

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

        public Builder setBirthDate(String birthDate){
            instance.birthDate = birthDate;
            return this;
        }

        public RegisterData build(){
            return instance;
        }
    }
}
