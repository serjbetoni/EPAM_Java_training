package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bsh.EvalError;
import bsh.Interpreter;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.servlet.http.HttpServletResponse.*;

public class StatefulCalcServlet extends HttpServlet {
    private static final long serialVersionUID = 1;
    private static final String EXPRESSION = "expression";
    private static final String DELIMITER = "\\A";
    private static final int LAST_CHAR_OF_URI = 6;

    private static String calc(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String exprOrigin = (String) session.getAttribute(EXPRESSION);
        StringBuilder expression = new StringBuilder();
        for (int i = 0; i < exprOrigin.length(); ++i) {
            char c = exprOrigin.charAt(i);
            if ((c >= 'a') && (c <= 'z')) {
                String attributeValue = (String) session.getAttribute(String.valueOf(c));
                if (attributeValue == null) {
                    resp.sendError(SC_CONFLICT, "Conflict: lack of data");
                    return null;
                }
                char value = attributeValue.charAt(0);
                if ((value >= 'a') && (value <= 'z')) {
                    expression.append(session.getAttribute(String.valueOf(value)));
                } else {
                    expression.append(session.getAttribute(String.valueOf(c)));
                }
            } else {
                expression.append(c);
            }
        }
        Interpreter interpreter = new Interpreter();
        try {
            interpreter.eval("result = " + expression);
        } catch (EvalError e1) {
            throw new IllegalArgumentException(e1);
        }
        Integer integer;
        try {
            integer = (Integer) interpreter.get("result");
        } catch (EvalError e2) {
            throw new IllegalArgumentException(e2);
        }
        return Integer.toString(integer);
    }

    private static void processExpression(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String expression = inputStreamToString(req.getInputStream());
        if (session.getAttribute(EXPRESSION) == null) {
            resp.setStatus(SC_CREATED);
            resp.addHeader("Location: ", "/calc/expression");
        } else {
            resp.setStatus(SC_OK);
        }
        session.setAttribute(EXPRESSION, expression);
    }

    private static void processVariable(HttpServletRequest req, HttpServletResponse resp, char variableName)
            throws IOException {
        HttpSession session = req.getSession();
        String variableValue = inputStreamToString(req.getInputStream());
        if (session.getAttribute(String.valueOf(variableName)) == null) {
            resp.setStatus(SC_CREATED);
            resp.addHeader("Location: ", "/calc/" + variableName);
        } else {
            resp.setStatus(SC_OK);
        }
        session.setAttribute(String.valueOf(variableName), variableValue);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        String paramOfUri = uri.substring(LAST_CHAR_OF_URI);
        if (paramOfUri.equals(EXPRESSION)) {
            processExpression(req, resp);
        } else if ((paramOfUri.length() == 1) && ((paramOfUri.charAt(0) >= 'a') && (paramOfUri.charAt(0) <= 'z'))) {
            processVariable(req, resp, paramOfUri.charAt(0));
        } else {
            throw new IllegalStateException();
        }
    }

    private static String inputStreamToString(InputStream inputStream) {
        try (Scanner scanner = new Scanner(inputStream, UTF_8)) {
            if (scanner.hasNext()) {
                return scanner.useDelimiter(DELIMITER).next();
            } else {
                return "";
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String uri = req.getRequestURI();
        String paramOfUri = uri.substring(LAST_CHAR_OF_URI);
        HttpSession session = req.getSession();
        session.removeAttribute(paramOfUri);
        resp.setStatus(SC_NO_CONTENT);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String res = calc(req, resp);
        if (res == null) {
            return;
        }
        PrintWriter writer = resp.getWriter();
        writer.write(res);
    }
}
