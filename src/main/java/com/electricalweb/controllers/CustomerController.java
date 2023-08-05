package com.electricalweb.controllers;

import com.electricalweb.validators.EmailValidator;
import com.electricalweb.validators.StringValidator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CustomerController", urlPatterns = "/processcustomer")
public class CustomerController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestCustomer customer = RequestCustomer.fromRequestParameters(request);
        customer.setAsRequestAttributes(request);
        List<String> violations = customer.validate();

        if (!violations.isEmpty()) {
            request.setAttribute("violations", violations);
        }

        String url = determineUrl(violations);
        forwardResponse(url, request, response);
    }

    private String determineUrl(List<String> violations) {
        if (!violations.isEmpty()) {
            return "/";
        } else {
            return "/WEB-INF/views/customerinfo.jsp";
        }
    }

    private void forwardResponse(String url, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class RequestCustomer {

        private final String firstName;
        private final String lastName;
        private final String email;

        private RequestCustomer(String firstName, String lastName, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }

        public static RequestCustomer fromRequestParameters(HttpServletRequest request) {
            return new RequestCustomer(
                    request.getParameter("firstname"),
                    request.getParameter("lastname"),
                    request.getParameter("email"));
        }

        public void setAsRequestAttributes(HttpServletRequest request) {
            request.setAttribute("firstname", firstName);
            request.setAttribute("lastname", lastName);
            request.setAttribute("email", email);
        }

        public List<String> validate() {
            List<String> violations = new ArrayList<>();
            if (!StringValidator.validate(firstName)) {
                violations.add("First Name is mandatory");
            }
            if (!StringValidator.validate(lastName)) {
                violations.add("Last Name is mandatory");
            }
            if (!EmailValidator.validate(email)) {
                violations.add("Email must be a well-formed address");
            }
            return violations;
        }
    }
}
