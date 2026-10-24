package main.java;

import main.java.daos.UserDao;
import main.java.dtos.users.CreateNewUser;
import main.java.enums.UsertTypeEnum;

public class Bootstrap {
    static void seedDefaultAdminUser (AppContext appContext) {
        try {
            var userDao = new UserDao(appContext.getConnection());
            var defaultAdminUser = userDao.findUserByIdentificationNumber("admin");

            if (defaultAdminUser == null) {
                userDao.createUser(
                        new CreateNewUser(
                                "admin",
                                "Default",
                                "Admin User",
                                "00000000",
                                "No Address",
                                "admin",
                                UsertTypeEnum.ADMIN
                        )
                );
            }
        } catch (Exception e) {
            System.out.println("Failed to seed default admin user: " + e.getMessage());
            System.exit(1);
        }
    }
}
