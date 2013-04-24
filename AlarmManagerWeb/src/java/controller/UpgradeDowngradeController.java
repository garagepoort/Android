/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import be.cegeka.android.alarms.domain.model.Facade;
import be.cegeka.android.alarms.transferobjects.UserTO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ivarv
 */
@Controller
public class UpgradeDowngradeController {
    
    @Autowired
    Facade organizer;
    
    @RequestMapping("/downgradeUser")
    public String downgradeUser(HttpServletRequest request) throws Exception{
        Integer tId = ServletRequestUtils.getIntParameter(request, "uID");
        UserTO user = organizer.getUserById(tId);
        HttpSession session = request.getSession();
        UserTO sourceTO = (UserTO) session.getAttribute("user");
        if(sourceTO == null){
            return "redirect:loginForm.htm?info='You have to be logged in as admin to view this page.'";
        }
        user.setAdmin(false);
        organizer.updateUser(user);
        return "redirect:users.htm";
    }
    
    @RequestMapping("/upgradeUser")
    public String upgradeUser(HttpServletRequest request) throws Exception {
        Integer tId = ServletRequestUtils.getIntParameter(request, "uID");
        UserTO user = organizer.getUserById(tId);
        HttpSession session = request.getSession();
        UserTO sourceTO = (UserTO) session.getAttribute("user");
        if(sourceTO == null){
            return "redirect:loginForm.htm?info='You have to be logged in as admin to view this page.'";
        }
        user.setAdmin(true);
        organizer.updateUser(user);
        return "redirect:users.htm";
    }
}
