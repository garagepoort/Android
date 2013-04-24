package controller;

import be.cegeka.android.alarms.domain.exceptions.BusinessException;
import be.cegeka.android.alarms.domain.model.Facade;
import be.cegeka.android.alarms.infrastructure.DatabaseException;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import commandobjects.AlarmCommand;
import commandobjects.CommandObjectConverter;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import utils.LoginChecker;
import validators.AlarmValidator;

@Controller
@RequestMapping("/alarmForm")
public class AlarmFormController {

    @Autowired
    Facade organizer;

    //Show Form
    @RequestMapping(method = RequestMethod.GET)
    public String initForm(ModelMap model, HttpServletRequest request) throws Exception {
        if (LoginChecker.userLoggedInAndAdmin(request)) {
            return "AlarmForm";
        }
        return "redirect:loginForm.htm?info='You have to be logged in as admin to view this page.'";
    }

    //Set Command Object
    @ModelAttribute("editAlarm")
    private AlarmCommand formBackingObject(HttpServletRequest request) throws ServletRequestBindingException, DatabaseException, BusinessException {
        Integer id = ServletRequestUtils.getIntParameter(request, "id");
        AlarmCommand alarmCommandObject = null;
        if (id != null && id != -1) {
            AlarmTO alarm = organizer.getAlarm(id);
            alarmCommandObject = CommandObjectConverter.convertAlarmTOToAlarmCommandObject(alarm);
        }
        return alarmCommandObject;
    }

    // Set submit processing
    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("editAlarm") AlarmCommand alarmCO, BindingResult result, HttpServletRequest request) throws Exception {
        if (LoginChecker.userLoggedInAndAdmin(request)) {
            AlarmValidator validator = new AlarmValidator();
            validator.validate(alarmCO, result);
            if (result.hasErrors()) {
                return "AlarmForm";
            } else {
                AlarmTO alarm = CommandObjectConverter.convertAlarmCommandObjectToAlarmTO(alarmCO);
                if (alarmCO.getId() == null || alarmCO.getId() == -1) {
                    organizer.addAlarm(alarm);
                } else {
                    organizer.updateAlarm(alarm);
                }
                return "forward:/alarms.htm";
            }
        } else {
            return "redirect:loginForm.htm?info='You have to be logged in as admin to view this page.'";
        }
    }

}
