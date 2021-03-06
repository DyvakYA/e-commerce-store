package controller.commands.user;

import controller.commands.Command;
import controller.commands.pageconstructor.RespondFactory;
import model.entities.User;
import model.extras.Localization;
import model.services.ProductService;
import model.services.UserService;
import model.services.service.ProductServiceImpl;
import model.services.service.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static model.constants.AttributesHolder.*;
import static model.constants.ErrorMsgHolder.LOGIN_USER_ERROR_MSG;
import static model.constants.MsgHolder.LOGIN_USER_SUCCESSFUL_MSG;
import static model.constants.UrlHolder.INDEX;

/**
 * This class represents login for User command.
 *
 * @author dyvakyurii@gmail.com
 */
public class LoginCommand implements Command {

    private static final Logger log = Logger.getLogger(LoginCommand.class);

    private static final String USER_LOGGED_IN = "%s id=%s LOGGED IN.";

    private UserService userService = UserServiceImpl.getInstance();
    private ProductService productService = ProductServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String result = null;
        String destinationPage = INDEX;
        String email = request.getParameter(USER_EMAIL_ATTRIBUTE);
        String password = request.getParameter(USER_AUTHENTICATE_ATTRIBUTE);

        log.info(email + " : " + password);
        if (email != null && password != null) {
            Optional<User> optionalUser = userService.login(email, password);
            log.info("Service return -  " + optionalUser);


            if (optionalUser != null) {
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    log.info(String.format(USER_LOGGED_IN, user.getEmail(), user.getId()));
                    result = Localization.getLocalizedMessage(request, LOGIN_USER_SUCCESSFUL_MSG) + user.getEmail();
                    request.getSession().setAttribute(USER_SESSION_ATTRIBUTE, user);
                    request.setAttribute(PRODUCTS_LIST_ATTRIBUTE, productService.getAll());
                    destinationPage = RespondFactory.builder()
                            .request(request)
                            .page("product")
                            .build()
                            .createPageFactory();
                }

            } else {
                result = Localization.getLocalizedMessage(request, LOGIN_USER_ERROR_MSG);
            }
            request.setAttribute(RESULT_ATTRIBUTE, result);

        }
        return destinationPage;
    }
}
