package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogoutController {
    
    @RequestMapping("/logout")
    public ModelAndView logOutAction(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("user") != null){
            session.invalidate();
        }
        return new ModelAndView("Home");
    }
    
}
