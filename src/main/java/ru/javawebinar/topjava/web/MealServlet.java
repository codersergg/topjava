package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.repo.MealRepo;
import ru.javawebinar.topjava.repo.MealRepoRAMImpl;
import ru.javawebinar.topjava.model.MealTo;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    MealRepo daoMeal = new MealRepoRAMImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            daoMeal.delete(id);
        } else if (action != null && action.equalsIgnoreCase("edit")) {
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("formMeal", id);
            request.getRequestDispatcher("/formMeal.jsp").forward(request, response);
        }
        List<MealTo> meals = daoMeal.getAll();
        request.setAttribute("meals", meals);
        log.debug("forward to meals");
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));

        daoMeal.edit(id);
        doGet(request, response);
    }

}
