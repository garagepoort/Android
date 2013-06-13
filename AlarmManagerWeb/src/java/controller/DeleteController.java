package controller;

import be.cegeka.android.alarms.domain.model.Facade;
import be.cegeka.android.alarms.infrastructure.DatabaseException;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import utils.LoginChecker;

@Controller
public class DeleteController {

    @Autowired
    Facade organizer;

    @RequestMapping("/deleteAlarm")
    public ModelAndView deleteAlarm(HttpServletRequest request) throws Exception {
        if (LoginChecker.userLoggedInAndAdmin(request)) {
            Integer id = ServletRequestUtils.getIntParameter(request, "id");
            AlarmTO alarm = organizer.getAlarm(id);
            organizer.deleteAlarm(alarm);
            return new ModelAndView("redirect:alarms.htm");
        }
        return new ModelAndView("redirect:loginForm.htm?info='You have to be logged in as admin to view this page.'");
    }
    
    @RequestMapping("/deleteUser")
    public ModelAndView deleteUser(HttpServletRequest request) throws Exception {
        if (LoginChecker.userLoggedInAndAdmin(request)) {
            Integer id = ServletRequestUtils.getIntParameter(request, "uID");
            UserTO userTO = organizer.getUserById(id);
            organizer.deleteUser(userTO);
            return new ModelAndView("redirect:users.htm");
        }
        return new ModelAndView("redirect:loginForm.htm?info='You have to be logged in as admin to view this page.'");
    }
    
    @RequestMapping("/cleanupAlarms")
    public String cleanupAlarms() throws DatabaseException{
//        organizer.cleanUpAlarms();
/**
 * @todo
 * clean up alarms
 */
        return "redirect:alarms.htm";
    }
}
