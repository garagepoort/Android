package controller;

import be.cegeka.android.alarms.domain.exceptions.BusinessException;
import be.cegeka.android.alarms.domain.model.Facade;
import be.cegeka.android.alarms.infrastructure.DatabaseException;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;
import commandobjects.AlarmCommand;
import commandobjects.CommandObjectConverter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import utils.LoginChecker;

@Controller
public class AlarmUserRelationController {

    @Autowired
    private Facade organizer;

    @RequestMapping("/editAlarmUsers")
    public ModelAndView goToAddUserToAlarm(HttpServletRequest request) throws Exception {
        if (LoginChecker.userLoggedInAndAdmin(request)) {
            // Create Model Map
            Map<String, Object> model = new HashMap<String, Object>();

            // Get Alarm to add to
            Integer id = ServletRequestUtils.getIntParameter(request, "aID");
            AlarmTO alarm = organizer.getAlarm(id);
            AlarmCommand alarmCO = CommandObjectConverter.convertAlarmTOToAlarmCommandObject(alarm);

            // Create list of users to show and a list of users already linked
            List<UserTO> userTOsLinked = new LinkedList<UserTO>();
            List<UserTO> userTOsAvailable = new LinkedList<UserTO>();

            List<UserTO> allUsers = new ArrayList<UserTO>(organizer.getAllUsers());
            List<UserTO> usersLinked = new ArrayList<UserTO>(organizer.getUsersForAlarm(alarm));
            for (UserTO u : allUsers) {
                if (usersLinked.contains(u)) {
                    userTOsLinked.add(u);
                } else {
                    userTOsAvailable.add(u);
                }
            }

            model.put("usersAvailable", userTOsAvailable);
            model.put("usersLinked", userTOsLinked);
            model.put("alarm", alarmCO);

            return new ModelAndView("EditAlarmUsers", model);
        }
        return new ModelAndView("redirect:loginForm.htm?info='You have to be logged in as admin to view this page.'");

    }

    @RequestMapping("/addUserToAlarmAction")
    public ModelAndView addUserToAlarm(HttpServletRequest request) throws Exception {
        if (LoginChecker.userLoggedInAndAdmin(request)) {
            Integer aID = addAlarmUserRelation(request).get("aID");
            return new ModelAndView("redirect:editAlarmUsers.htm?aID=" + aID);
        }
        return new ModelAndView("redirect:loginForm.htm?info='You have to be logged in as admin to view this page.'");
    }

    @RequestMapping("/removeUserFromAlarm")
    public ModelAndView removeUserFromAlarm(HttpServletRequest request) throws Exception {
        if (LoginChecker.userLoggedInAndAdmin(request)) {
            Integer aID = removeAlarmUserRelation(request).get("aID");
            return new ModelAndView("redirect:editAlarmUsers.htm?aID=" + aID);
        }
        return new ModelAndView("redirect:loginForm.htm?info='You have to be logged in as admin to view this page.'");
    }

    @RequestMapping("/editUserAlarms")
    public ModelAndView goToEditUserAlarms(HttpServletRequest request) throws Exception {
        if (LoginChecker.userLoggedInAndAdmin(request)) {
            // Create Model Map
            Map<String, Object> model = new HashMap<String, Object>();

            // Get Alarm to add to
            Integer id = ServletRequestUtils.getIntParameter(request, "uID");
            UserTO userTO = organizer.getUserById(id);

            // Create list of users to show and a list of users already linked
            List<AlarmCommand> alarmCOsLinked = new LinkedList<AlarmCommand>();
            List<AlarmCommand> alarmCOsAvailable = new LinkedList<AlarmCommand>();

            List<AlarmTO> allAlarms = new ArrayList<AlarmTO>(organizer.getAllAlarms());
            List<AlarmTO> alarmsLinked = new ArrayList<AlarmTO>(organizer.getAlarmsForUser(userTO));
            for (AlarmTO a : allAlarms) {
                if (alarmsLinked.contains(a)) {
                    alarmCOsLinked.add(CommandObjectConverter.convertAlarmTOToAlarmCommandObject(a));
                } else {
                    alarmCOsAvailable.add(CommandObjectConverter.convertAlarmTOToAlarmCommandObject(a));
                }
            }

            model.put("alarmsAvailable", alarmCOsAvailable);
            model.put("alarmsLinked", alarmCOsLinked);
            model.put("user", userTO);

            return new ModelAndView("EditUserAlarms", model);
        }
        return new ModelAndView("redirect:loginForm.htm?info='You have to be logged in as admin to view this page.'");
    }

    @RequestMapping("/addAlarmToUserAction")
    public ModelAndView addAlarmToUserAction(HttpServletRequest request) throws Exception {
        if (LoginChecker.userLoggedInAndAdmin(request)) {
            Integer uID = addAlarmUserRelation(request).get("uID");
            return new ModelAndView("redirect:editUserAlarms.htm?uID=" + uID);
        }
        return new ModelAndView("redirect:loginForm.htm?info='You have to be logged in as admin to view this page.'");
    }

    @RequestMapping("/removeAlarmFromUser")
    public ModelAndView removeAlarmFromUser(HttpServletRequest request) throws Exception {
        if (LoginChecker.userLoggedInAndAdmin(request)) {
            Integer uID = removeAlarmUserRelation(request).get("uID");
            return new ModelAndView("redirect:editUserAlarms.htm?uID=" + uID);
        }
        return new ModelAndView("redirect:loginForm.htm?info='You have to be logged in as admin to view this page.'");
    }

    private Map<String, Integer> addAlarmUserRelation(HttpServletRequest request) throws DatabaseException, ServletRequestBindingException, BusinessException {
        Integer uID = ServletRequestUtils.getIntParameter(request, "uID");
        Integer aID = ServletRequestUtils.getIntParameter(request, "aID");
        UserTO userTO = organizer.getUserById(uID);
        AlarmTO alarmTO = organizer.getAlarm(aID);
        organizer.addAlarmToUser(alarmTO, userTO);
        Map<String, Integer> ids = new HashMap<String, Integer>();
        ids.put("uID", uID);
        ids.put("aID", aID);
        return ids;
    }

    private Map<String, Integer> removeAlarmUserRelation(HttpServletRequest request) throws ServletRequestBindingException, DatabaseException, BusinessException {
        Integer uID = ServletRequestUtils.getIntParameter(request, "uID");
        Integer aID = ServletRequestUtils.getIntParameter(request, "aID");
        UserTO userTO = organizer.getUserById(uID);
        AlarmTO alarmTO = organizer.getAlarm(aID);
        
        Map<String, Integer> ids = new HashMap<String, Integer>();
        ids.put("uID", uID);
        ids.put("aID", aID);
        return ids;
    }
}
