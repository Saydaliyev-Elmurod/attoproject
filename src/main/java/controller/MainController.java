package controller;

import container.ComponentContainer;
import db.DataBase;
import dto.Profile;
import enums.Role;
import enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repository.ProfileRepository;
import service.ProfileService;
@Setter
@Getter
public class MainController {
    private ProfileService profileService;
    private ProfileRepository profileRepository;
    private ProfileController profileController;
    private AdminController adminController;


    public void start() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        DataBase dataBase = (DataBase) context.getBean("dataBase");
        dataBase.init();
        while (true) {
            menu();
            int action = ComponentContainer.getAction();
            switch (action) {
                case 1 -> {
                    login();
                }
                case 2 -> {
                    registration();
                }
                case 0 -> {
                    System.exit(0);
                }
            }

        }
    }

    private void registration() {
        System.out.print("Enter name >> ");
        String name = ComponentContainer.stringScanner.next();
        System.out.print("Enter surname >> ");
        String surname = ComponentContainer.stringScanner.next();
        System.out.print("Enter phone >> ");
        String phone = ComponentContainer.stringScanner.next();
        System.out.print("Enter password >> ");
        String password = ComponentContainer.stringScanner.next();

        profileService.registration(name, surname, phone, password);

    }

    private void login() {
        System.out.print("Enter phone :");
        String phone = ComponentContainer.stringScanner.next();
        System.out.print("Enter password :");
        String password = ComponentContainer.stringScanner.next();
        Profile profile = profileService.login(phone, password);
        if (profile == null) {
            System.out.println("password or login incorrect");
            return;
        }
        if (profile.getStatus().equals(Status.BLOCK)) {
            System.out.println("PROFILE BLOCKED BY ADMINISTRATION");
            return;
        }
        if (profile.getRole().equals(Role.USER)) {
            profile.setStatus(Status.ACTIVE);
            profileRepository.updateProfileStatus(profile);
            profileController.start(profile);
        } else if (profile.getRole().equals(Role.ADMIN)) {
            adminController.start(profile);
        }
    }


    private void menu() {
        System.out.println("1.Login");
        System.out.println("2.Registration");
        System.out.println("0.Exit");
    }
}
