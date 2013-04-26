/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import be.cegeka.android.alarms.domain.exceptions.BusinessException;
import be.cegeka.android.alarms.domain.model.Facade;
import be.cegeka.android.alarms.transferobjects.UserTO;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import validators.UserValidator;

@Controller
@RequestMapping("/registerForm")
public class RegisterFormController {

    @Autowired
    Facade organizer;

    @RequestMapping(method = RequestMethod.GET)
    public String initForm(ModelMap model) throws Exception {
        return "Register";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String register(@ModelAttribute("registerUser") UserTO rUser, HttpServletRequest request, BindingResult result) throws BusinessException {
        rUser.setAdmin(Boolean.TRUE);
        UserValidator validator = new UserValidator();
        validator.validate(rUser, result);
        if (result.hasErrors()) {
            return "Register";
        } else {
            // TODO: Setuserid moet weg.
            //rUser.setUserid(800);
            organizer.addUser(rUser);
            return "Home";
        }
    }

    @ModelAttribute("registerUser")
    private UserTO formBackingObject(HttpServletRequest request) {
        return new UserTO();
    }
}
