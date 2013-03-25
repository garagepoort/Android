package controller;

import domain.AlarmOrganizer;
import entities.Alarm;
import entities.User;
import entities.UserAlarm;
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
import transferobjects.AlarmTO;
import transferobjects.UserTO;
import utils.LoginChecker;
import utils.TransferObjectConverter;

@Controller
public class StandardPageController {
    
    @Autowired
    AlarmOrganizer organizer;

    @RequestMapping("/home")
    public String goHome() {
        return "Home";
    }

    @RequestMapping("/about.htm")
    public ModelAndView goToAbout() {
        Map<String, String> model = createModelMapInfoPage("About", "About", "This is the webinterface for the Remote Alarm Manager.");
        return new ModelAndView("InfoPage", model);
    }

    @RequestMapping("/contact.htm")
    public String goToContact() {
        return "Contact";
    }

    @RequestMapping("/404")
    public ModelAndView goTo404() {
        Map<String, String> model = createModelMapInfoPage("404", "404", "The requested page was not found.");
        return new ModelAndView("InfoPage", model);
    }

    @RequestMapping("/exception")
    public ModelAndView goToException() {
        Map<String, String> model = createModelMapInfoPage("Oops!", "Oops!", "Something somewhere went wrong. My apologies. Please try again later.");
        return new ModelAndView("InfoPage", model);
    }

    @RequestMapping("/registerInfoPage")
    public ModelAndView goToRegisterInfoPage() {
        HashMap<String, String> model = createModelMapInfoPage("Register Info", "Info", "Thanks for creating an account! You can use this account to log in on your android device!");
        return new ModelAndView("InfoPage", model);
    }

    @RequestMapping("/userinfopage")
    public ModelAndView goToUserinfoPage(HttpServletRequest request) throws Exception {
        if (LoginChecker.userLoggedIn(request)) {
            // Create Model Map
            Map<String, Object> model = new HashMap<String, Object>();

            // Get Alarm to add to
            Integer id = ((UserTO) request.getSession().getAttribute("user")).getId();
            User user = organizer.getUser(id);
            UserTO userTO = TransferObjectConverter.getUserTO(user);

            // Create list of users to show and a list of users already linked
            List<AlarmTO> alarmTOsLinked = new ArrayList<AlarmTO>();
            for(UserAlarm ua : user.getUserAlarmList()){
                alarmTOsLinked.add(TransferObjectConverter.getAlarmTO(ua.getAlarmid()));
            }
            model.put("alarmsLinked", alarmTOsLinked);
            model.put("user", userTO);

            return new ModelAndView("UserInfoPage", model);
        }
        return new ModelAndView("redirect:loginForm.htm?info='You have to be logged in to view this page.'");
    }

    private HashMap<String, String> createModelMapInfoPage(String pageTitle, String title, String text) {
        HashMap<String, String> model = new HashMap<String, String>();
        model.put("pageTitle", pageTitle);
        model.put("title", title);
        model.put("text", text);
        return model;
    }
}
