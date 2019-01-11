package com.sati.web.app;


import HibernateClasses.Employee;
import HibernateClasses.Pallete;
import HibernateClasses.ProductionLine;
import HibernateConnector.DataBaseConnector;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionScope
public class ApplicationController {
    private boolean loginCheck(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null)
            return false;
        else
            return true;
    }

    private void clearUserFromSession(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
    }

    private void clearPalleteFromSession(HttpServletRequest request) {
        request.getSession().removeAttribute("pallete");
    }


    //LOGIN FORM
    @GetMapping("/login")
    public String loginForm(Model model, HttpServletRequest request) {
        model.addAttribute("credentials", new Credentials());
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute Credentials credentials, Model model, HttpServletRequest request) {
        Employee employee = DataBaseConnector.authorizeUser(credentials.getLogin(), credentials.getPassword());
        if (employee != null) {
            int position = employee.getJobPosition();
            if (position == Positions.STOREMAN || position == Positions.STOREMAN_JUNIOR) {
                request.getSession().setAttribute("user", employee);
                return "redirect:storeman";
            } else if (position == Positions.HALL_MANAGER) {
                request.getSession().setAttribute("user", employee);
                return "redirect:hallManager";
            } else return "noAuthorization";
        } else return "loginFault";
    }
    //LOGIN FORM END

    @GetMapping("/storeman")
    public String storemanEmpty(Model model, HttpServletRequest request) {
        model.addAttribute("palleteNumber", new PalleteNumber());
        model.addAttribute("showDetails", false);
        model.addAttribute("allowAccept", false);
        return "storeman";
    }

    @PostMapping("/storeman")
    public String storemanWithPallete(@ModelAttribute PalleteNumber palleteNumber, Model model, HttpServletRequest request) {
        if (loginCheck(request)) {
            try {
                Pallete pallete = DataBaseConnector.getPallete(palleteNumber.getIdIntger());
                if (pallete != null) {
                    request.getSession().setAttribute("pallete", pallete);
                    int state = pallete.getState();
                    model.addAttribute("color",
                            state == States.PALLETE_CHECKED
                                    ? "background-color:lawngreen" : state >= States.PALLETE_STORED
                                    ? "background-color:khaki" : "background-color:coral");
                    model.addAttribute("allowAccept",
                            state == States.PALLETE_CHECKED);
                    model.addAttribute("showDetails", true);
                    model.addAttribute("prodName", pallete.getProductionRaportPart().getProductType().getProductName());
                    model.addAttribute("quantity", pallete.getQuantity());
                    model.addAttribute("netto", pallete.getNetto());
                    model.addAttribute("lot", pallete.getBatch());
                    model.addAttribute("expiry", States.timestampToStrDDMMYYYY(pallete.getExpiryDate()));
                    model.addAttribute("employee", pallete.getProductionRaportPart().getEmployee().getEmployeeID());
                    return "storeman";
                } else return "storeman";
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else clearUserFromSession(request);
        return "redirect:login";
    }

    @PostMapping("/acceptStoreman")
    public String acceptPalleteStoraman(Model model, HttpServletRequest request) {
        if (loginCheck(request)) {
            try {
                Pallete pallete = (Pallete) request.getSession().getAttribute("pallete");
                pallete.setState(States.PALLETE_STORED);
                DataBaseConnector.updateObject(pallete);
                clearPalleteFromSession(request);
                return "storeman";
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else clearUserFromSession(request);
        return "redirect:login";
    }

    @GetMapping("/hallManager")
    public String hallManagerEmpty(Model model, HttpServletRequest request) {
        model.addAttribute("palleteNumber", new PalleteNumber());
        model.addAttribute("showDetails", false);
        model.addAttribute("allowAccept", false);
        return "hallManager";
    }

    @PostMapping("/hallManager")
    public String hallManagerWithPallete(@ModelAttribute PalleteNumber palleteNumber, Model model, HttpServletRequest request) {
        if (loginCheck(request)) {
            try {
                Pallete pallete = DataBaseConnector.getPallete(palleteNumber.getIdIntger());
                if (pallete != null) {
                    request.getSession().setAttribute("pallete", pallete);
                    int state = pallete.getProductionRaportPart().getLabTestState();
                    model.addAttribute("color",
                            state == States.PRODUCTION_RAPORT_PART_ACCEPTED
                                    ? "background-color:lawngreen" : state >= States.PRODUCTION_RAPORT_PART_TO_STORE
                                    ? "background-color:khaki" : "background-color:coral");
                    model.addAttribute("allowAccept",
                            ((state == States.PRODUCTION_RAPORT_PART_ACCEPTED) && (pallete.getState() == States.PALLETE_WAITING)));
                    model.addAttribute("showDetails", true);
                    model.addAttribute("prodName", pallete.getProductionRaportPart().getProductType().getProductName());
                    model.addAttribute("quantity", pallete.getQuantity());
                    model.addAttribute("netto", pallete.getNetto());
                    model.addAttribute("lot", pallete.getBatch());
                    model.addAttribute("expiry", States.timestampToStrDDMMYYYY(pallete.getExpiryDate()));
                    model.addAttribute("employee", pallete.getProductionRaportPart().getEmployee().getEmployeeID());
                    return "hallManager";
                } else return "hallManager";
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else clearUserFromSession(request);
        return "redirect:login";
    }

    @PostMapping("/acceptHallManager")
    public String acceptPalleteHallManager(Model model, HttpServletRequest request) {
        if (loginCheck(request)) {
            try {
                Pallete pallete = (Pallete) request.getSession().getAttribute("pallete");
                pallete.setState(States.PALLETE_CHECKED);
                DataBaseConnector.updateObject(pallete);
                clearPalleteFromSession(request);
                return "hallManager";
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else clearUserFromSession(request);
        return "redirect:login";
    }


    @RequestMapping("/productionOrders")
    public String productionOrders(Model model) {
        return "productionOrder";
    }

    @GetMapping("/displayOrders")
    public String displayOrders(@RequestParam(value = "line", defaultValue = "0") Integer id, Model model, HttpServletRequest request) {
        ProductionLine productionLine = DataBaseConnector.getProductionLine(id);
        if (productionLine != null)
            model.addAttribute("currentProductionLine", DataBaseConnector.getProductionLine(id).getId());
        else
            model.addAttribute("currentProductionLine", 0);
        return "displayOrders";
    }


}
