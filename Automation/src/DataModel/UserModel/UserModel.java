package DataModel.UserModel;

import DataModel.GlobalVariable;
import DataModel.ProjectConfigurationManage.ProjectConfiguration;

import java.util.Properties;

public class UserModel{
    private final String userName;
    private final String password;

    public UserModel(){
        Properties props = ProjectConfiguration.readPropertyData(GlobalVariable.CREDENTIAL_DATA);
        this.userName = props.getProperty("username");
        this.password = props.getProperty("password");
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }
}
