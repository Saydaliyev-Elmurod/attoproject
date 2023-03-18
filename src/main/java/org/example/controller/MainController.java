package org.example.controller;

import org.example.config.Config;
import org.example.container.ComponentContainer;
import org.example.db.DataBase;
import org.example.dto.Profile;
import org.example.enums.Role;
import org.example.enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.example.repository.ProfileRepository;
import org.example.service.ProfileService;

@Setter
@Getter
@Controller
public class MainController {
    @Autowired
    private  ProfileService profileService;
    @Autowired
    private  ProfileRepository profileRepository;
    @Autowired

    private  ProfileController profileController;
    @Autowired

    private  AdminController adminController;
    public void start() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
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
