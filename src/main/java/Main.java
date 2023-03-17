


import controller.MainController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {
    public static void main(String[] args) {
//        MainController atto = new MainController();
//        atto.start();
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        MainController mainController = (MainController) context.getBean("mainController");
        mainController.start();
    }


}