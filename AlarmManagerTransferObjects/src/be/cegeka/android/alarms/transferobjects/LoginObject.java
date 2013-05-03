package be.cegeka.android.alarms.transferobjects;


public class LoginObject
{
    private UserTO userTO;
    private String error;


    public LoginObject(String error)
    {
        this.userTO = null;
        this.error = error;
    }


    public LoginObject(UserTO userTO, String error)
    {
        this.userTO = userTO;
        this.error = error;
    }


    public UserTO getUserTO()
    {
        return userTO;
    }


    public String getError()
    {
        return error;
    }
}
