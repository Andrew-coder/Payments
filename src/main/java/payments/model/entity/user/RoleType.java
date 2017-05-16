package payments.model.entity.user;

/**
 * This class defines different roles of the user
 */
public enum RoleType {
    USER("user"),
    ADMIN("admin");

    private String roleName;

    RoleType(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName(){
        return roleName;
    }

    public static RoleType getRole(String roleName){
        for(RoleType roleType:values()) {
            if (roleType.getRoleName().equals(roleName))
                return roleType;
        }
        return null;
    }
}
