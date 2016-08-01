package com.borrowed.contact.controller;

import com.borrowed.contact.dao.ContactDAO;
import com.borrowed.contact.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@Controller
public class HomeController {

    @Autowired
    private ContactDAO contactDAO;

    @RequestMapping(value = "/")
    public ModelAndView listContact(ModelAndView model) throws IOException {
        List<User> listUser = contactDAO.list();
        model.addObject("listContact", listUser);
        model.setViewName("home");

        return model;
    }

    @RequestMapping(value = "/newContact", method = RequestMethod.GET)
    public ModelAndView newContact(ModelAndView model) {
        User newUser = new User();
        model.addObject("contact", newUser);
        model.setViewName("ContactForm");
        return model;
    }

    @RequestMapping(value = "/saveContact", method = RequestMethod.POST)
    public ModelAndView saveContact(@ModelAttribute User user) {
        contactDAO.saveOrUpdate(user);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/deleteContact", method = RequestMethod.GET)
    public ModelAndView deleteContact(HttpServletRequest request) {
        int contactId = Integer.parseInt(request.getParameter("id"));
        contactDAO.delete(contactId);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/editContact", method = RequestMethod.GET)
    public ModelAndView editContact(HttpServletRequest request) {
        int contactId = Integer.parseInt(request.getParameter("id"));
        User user = contactDAO.get(contactId);
        ModelAndView model = new ModelAndView("ContactForm");
        model.addObject("contact", user);

        return model;
    }
}
