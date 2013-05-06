package be.cegeka.android.alarms.transferobjects;

import java.io.Serializable;


public class LoginObject implements Serializable
{
    private UserTO userTO;
    private ServerResult error;


    public LoginObject(){}
    
    public LoginObject(ServerResult error)
    {
        this.userTO = null;
        this.error = error;
    }


    public LoginObject(UserTO userTO, ServerResult error)
    {
        this.userTO = userTO;
        this.error = error;
    }


    public UserTO getUserTO()
    {
        return userTO;
    }


    public ServerResult getError()
    {
        return error;
    }


    public void setUserTO(UserTO userTO)
    {
        this.userTO = userTO;
    }


    public void setError(ServerResult error)
    {
        this.error = error;
    }
    
    
}


