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
import utils.LoginChecker;

/**
 *
 * @author ivarv
 */
@Controller
public class UpgradeDowngradeController {

    @Autowired
    Facade organizer;

    @RequestMapping("/downgradeUser")
    public String downgradeUser(HttpServletRequest request) throws Exception {
        if (LoginChecker.userLoggedInAndAdmin(request)) {
            Integer tId = ServletRequestUtils.getIntParameter(request, "uID");
            UserTO user = organizer.getUserById(tId);
            organizer.downgradeUser(user);
            return "redirect:users.htm";
        }
        return "redirect:login.htm";
    }

    @RequestMapping("/upgradeUser")
    public String upgradeUser(HttpServletRequest request) throws Exception {
        if (LoginChecker.userLoggedInAndAdmin(request)) {
            Integer tId = ServletRequestUtils.getIntParameter(request, "uID");
            UserTO user = organizer.getUserById(tId);
            organizer.upgradeUser(user);
            return "redirect:users.htm";
        }
        return "redirect:login.htm";
    }
}
