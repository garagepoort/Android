package controller;

import be.cegeka.android.alarms.domain.exceptions.BusinessException;
import be.cegeka.android.alarms.domain.model.Facade;
import be.cegeka.android.alarms.infrastructure.DatabaseException;
import be.cegeka.android.alarms.transferobjects.UserTO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/loginForm")
public class LoginController {

    @Autowired
    Facade organizer;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView initForm(ModelMap model, HttpServletRequest request) throws Exception {
        String info = ServletRequestUtils.getStringParameter(request, "info");
        return new ModelAndView("Login", "info", info);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("login") UserTO userTO, HttpServletRequest request) throws DatabaseException, BusinessException {
        return loginAction(userTO, request);
    }

    private ModelAndView loginAction(UserTO userTO, HttpServletRequest request) throws DatabaseException, BusinessException {
        String username = userTO.getEmail();
        String password = userTO.getPaswoord();
        //TODO moet login worden.
        UserTO user = organizer.getUser(username);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return new ModelAndView("Home");
        } else {
            return new ModelAndView("Login", "error", "Username or password were wrong.");
        }
    }

    @ModelAttribute("login")
    private UserTO formBackingObject(HttpServletRequest request) {
        return new UserTO();
    }
}
