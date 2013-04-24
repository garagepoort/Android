package controller;

import be.cegeka.android.alarms.domain.exceptions.BusinessException;
import be.cegeka.android.alarms.domain.model.Facade;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;
import commandobjects.AlarmCommand;
import commandobjects.CommandObjectConverter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import utils.LoginChecker;

@Controller
public class OverviewController {

    @Autowired
    Facade organizer;

    @RequestMapping("/alarms")
    public ModelAndView showAlarms(HttpServletRequest request) throws BusinessException {
        if (LoginChecker.userLoggedInAndAdmin(request)) {
            List<AlarmTO> alarmsTOs = new ArrayList<AlarmTO>(organizer.getAllAlarms());
            List<AlarmCommand> alarmCOs = new LinkedList<AlarmCommand>();
            for (AlarmTO a : alarmsTOs) {
                alarmCOs.add(CommandObjectConverter.convertAlarmTOToAlarmCommandObject(a));
            }
            return new ModelAndView("Alarms", "alarms", alarmCOs);
        }
        return new ModelAndView("redirect:loginForm.htm?info='You have to be logged in as admin to view this page.'");
    }

    @RequestMapping("/users")
    public ModelAndView showUsers(HttpServletRequest request) throws BusinessException {
        if (LoginChecker.userLoggedInAndAdmin(request)) {
            List<UserTO> users = new ArrayList<UserTO>(organizer.getAllUsers());
            return new ModelAndView("Users", "users", users);
        }
        return new ModelAndView("redirect:loginForm.htm?info='You have to be logged in as admin to view this page.'");
    }
}
