package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.StorageService;
import storage.BankRepository;
import storage.entity.BankUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class BankSessionController {
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private StorageService storageService;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void user(@RequestParam String name, boolean admin,String password, Integer account ) {

        storageService.saveuser(name, admin, password, account);

    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String authenticate(@RequestParam String name, String password, HttpServletRequest request) {

        return storageService.authenticate(name,password, request);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestParam Integer account, HttpServletRequest request) {
        return storageService.getAddMoney(account, request);
    }


    @RequestMapping(value = "/deduct", method = RequestMethod.POST)
    public String deduct(@RequestParam Integer account, HttpServletRequest request) {
        return storageService.getDeductMoney(account, request);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(  HttpServletRequest request) {

        request.getSession().invalidate();
        return "logout";

    }

    @RequestMapping(value = "/findallusers", method = RequestMethod.POST)
    public List<BankUser> userfindbyadmin(HttpServletRequest request) {
        return storageService.listOfAllUsers( request);
    }

    @RequestMapping(value = "/name", method = RequestMethod.POST)
    public String finduser(@RequestParam String name ) {
        return storageService.searchname(name);

    }
}
