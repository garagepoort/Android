/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import be.cegeka.android.alarms.domain.exceptions.BusinessException;
import be.cegeka.android.alarms.domain.model.Facade;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.LoginObject;
import be.cegeka.android.alarms.transferobjects.RepeatedAlarmTO;
import be.cegeka.android.alarms.transferobjects.ServerResult;
import be.cegeka.android.alarms.transferobjects.UserTO;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;


/**
 *
 * @author ivarv
 */
@WebService(serviceName = "AlarmManagerWebservice")
public class AlarmManagerWebservice
{
    private Facade facade = new Facade();
    
    

    // ONLY FOR TEST PURPOSES

    public void setFacade(Facade facade)
    {
        this.facade = facade;
    }


    /**
     * Web service operation
     */
    @WebMethod(operationName = "getAllAlarmsFromUser")
    public ArrayList<RepeatedAlarmTO> getAllAlarmsFromUser(@WebParam(name = "username") String username) throws BusinessException
    {
        //TODO write your implementation code here:
        try
        {
            UserTO userTO = facade.getUser(username);
            ArrayList<AlarmTO> alarms = new ArrayList<AlarmTO>(facade.getAlarmsForUser(userTO));
            ArrayList<RepeatedAlarmTO> rAlarms = new ArrayList<RepeatedAlarmTO>();
            for (AlarmTO a : alarms)
            {
                RepeatedAlarmTO rAlarm = new RepeatedAlarmTO(a);
                rAlarms.add(rAlarm);
            }
            return rAlarms;
        }
        catch (BusinessException e)
        {
            return null;
        }
    }


    /**
     * Web service operation
     */
    @WebMethod(operationName = "registerUser")
    public boolean registerUser(@WebParam(name = "email") String email, @WebParam(name = "gcmId") String gcmId) throws BusinessException
    {
        return facade.registerUser(email, gcmId);
    }


    /**
     * Web service operation
     */
    @WebMethod(operationName = "authenticate")
    public Boolean authenticate(@WebParam(name = "username") String username, @WebParam(name = "password") String password) throws BusinessException
    {
        UserTO user = facade.getUser(username);
        return facade.authenticateUser(user, password);
    }


    /**
     * Web service operation
     */
    @WebMethod(operationName = "login")
    public LoginObject login(@WebParam(name = "username") String username, @WebParam(name = "paswoord") String paswoord)
    {

//        return new LoginObject("test error");
        LoginObject loginObject = null;
        
        try
        {
            System.out.println(username + " " + paswoord);
            UserTO userTO = facade.getUser(username);
            if (facade.authenticateUser(userTO, paswoord))
            {
                ServerResult resultCode = ServerResult.SUCCESS;
                loginObject = new LoginObject(userTO, resultCode);
            }
            else
            {
                ServerResult resultCode = ServerResult.WRONG_USER_CREDENTIALS;
                loginObject = new LoginObject(resultCode);
            }
        }
        catch (BusinessException ex)
        {
            ServerResult resultCode = ServerResult.EXCEPTION;
            loginObject = new LoginObject(resultCode);
        }
        finally
        {
            return loginObject;
        }
    
    }
}


