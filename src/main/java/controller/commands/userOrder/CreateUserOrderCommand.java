package controller.commands.userOrder;

import controller.commands.Command;
import model.entities.UserOrder;
import model.extras.Localization;
import model.services.UserOrderService;
import model.services.service.UserOrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.AttributesHolder.*;
import static model.constants.MsgHolder.CREATE_USER_ORDER_SUCCESSFUL_MSG;
import static model.constants.UrlHolder.USER_ORDER_DESTINATION_PAGE;

/**
 * This class represents creating UserOrder command.
 *
 * @author dyvakyurii@gmail.com
 */
public class CreateUserOrderCommand implements Command {

    private UserOrderService userOrderService = UserOrderServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        UserOrder userOrder = UserOrder.builder()
                .setUserId(Integer.parseInt((request.getParameter(USER_ID_ATTRIBUTE))))
                .setOrderId(Integer.parseInt((request.getParameter(ORDER_ID_ATTRIBUTE))))
                .build();
        userOrderService.create(userOrder);

        request.setAttribute(USER_ORDERS_LIST_ATTRIBUTE, userOrderService.getAll());

        String message = Localization.getLocalizedMessage(request, CREATE_USER_ORDER_SUCCESSFUL_MSG);
        request.setAttribute(RESULT_ATTRIBUTE, message);

        return USER_ORDER_DESTINATION_PAGE;
    }
}
