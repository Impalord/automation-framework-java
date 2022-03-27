package DataModel.ProjectConfigurationManage;

public class ProjectConfigHolder {
    private String url;
    private String browser;

    public void setUrl(String url){
        this.url = url;
    }

    public void setBrowser(String browser){
        this.browser = browser;
    }

    public String getUrl(){
        return url;
    }

    public String getBrowser() {
        return browser;
    }
}
