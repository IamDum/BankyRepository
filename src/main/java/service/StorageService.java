package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import storage.BankRepository;
import storage.entity.BankUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class StorageService {

    @Autowired
    private BankRepository bankRepository;

    public String saveuser(String name, boolean admin, String password, Integer account) {

        BankUser people = new BankUser();
        people.setName(name);
        people.setPassword(password);
        people.setAccount(account);
        people.setAdmin(admin);

        bankRepository.save(people);
        return "register";
    }

    public String authenticate(String name, String password, HttpServletRequest request) {
        Optional<BankUser> userFromDB = bankRepository.findByName(name);
        if (userFromDB.isPresent()) {
            BankUser user = userFromDB.get();
            if (user.getPassword().equals(password)) {

                boolean isAdmin = user.isAdmin();
                request.getSession().setAttribute("authenticate", "yes");
                request.getSession().setAttribute("name", name);
                request.getSession().setAttribute("isAdmin", isAdmin);
                return "authenticate sucessful ";

            }
            return "user not found";
        }
        return "admin not authenticate";
    }

    public String getAddMoney(Integer addmoneyinaccount, HttpServletRequest request) {
        String isAuthenticated = (String) request.getSession().getAttribute("authenticate");
        if (isAuthenticated != null) {
            if (isAuthenticated.equals("yes")) {
                String name = (String) request.getSession().getAttribute("name");
                Optional<BankUser> userFromDB = bankRepository.findByName(name);
                if (userFromDB.isPresent()) {
                    BankUser user = userFromDB.get();
                    Integer newMoney = user.getAccount() + addmoneyinaccount;
                    user.setAccount(newMoney);
                    bankRepository.save(user);
                    return "money added";

                }
            }
        }
        return " not authenticate";
    }

    public String getDeductMoney(Integer deductmoneyfromaccount, HttpServletRequest request) {
        String isAuthenticated = (String) request.getSession().getAttribute("authenticate");
        if (isAuthenticated != null) {
            if (isAuthenticated.equals("yes")) {
                String name = (String) request.getSession().getAttribute("name");
                Optional<BankUser> userFromDB = bankRepository.findByName(name);
                if (userFromDB.isPresent()) {
                    BankUser user = userFromDB.get();
                    Integer deductMoney = user.getAccount() - deductmoneyfromaccount;
                    user.setAccount(deductMoney);
                    bankRepository.save(user);
                    return "money deducted";

                }
            }
        }
        return " not authenticate";
    }

    public List<BankUser> listOfAllUsers(HttpServletRequest request) {
        String isAuthenticated = (String) request.getSession().getAttribute("authenticate");
        if (isAuthenticated != null) {
            if (isAuthenticated.equals("yes")) {
                String name = (String) request.getSession().getAttribute("name");
                Optional<BankUser> userFromDB = bankRepository.findByName(name);
                if (userFromDB.isPresent()) {
                    BankUser user = userFromDB.get();
                    if (user.isAdmin()) {
                        return bankRepository.findAll();
                    } else {
                        throw new RuntimeException("This user is not admin");
                    }
                }
            }
        }
        throw new RuntimeException("Not authenticated");
    }

    public String searchname(String name) {
        Optional<BankUser> userFromDB = bankRepository.findByName(name);

        if (userFromDB.isPresent()) {
            BankUser user = userFromDB.get();
            if (user.getName().equals(name)) {
                if (user.isAdmin()) {
                    return "user is super admin";
                }
                return "user found";

            }
        }
        return "user not found";
    }
}



