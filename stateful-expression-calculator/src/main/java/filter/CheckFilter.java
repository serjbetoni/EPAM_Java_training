package filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

public class CheckFilter implements Filter {
    private static final String EXPRESSION = "expression";
    private static final String DELIMITER = "\\A";
    private static final int VARIABLE_VALUES_RANGE = 10000;
    private static final int VARIABLE_VALUES_RANGE_NEG = -10000;
    private static final int LAST_CHAR_OF_URI = 6;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        String paramOfUri = uri.substring(LAST_CHAR_OF_URI);
        MultiReadHttpServletRequest wrappedRequest = new MultiReadHttpServletRequest(req);
        if (paramOfUri.equals(EXPRESSION)) {
            boolean checkExpression = processExpression(wrappedRequest, resp);
            if (!checkExpression) {
                return;
            }
        }
        if (!processVariable(wrappedRequest, resp)) {
            return;
        }
        chain.doFilter(wrappedRequest, response);
    }

    private static boolean processExpression(ServletRequest request, ServletResponse response) throws IOException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            String expression = inputStreamToString(req.getInputStream());
            final boolean checking = checkExpression(expression);
            if (!checking) {
                resp.sendError(SC_BAD_REQUEST, "BAD format");
            }
            return checking;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
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

    public static boolean checkExpression(String expression) {
        String check = expression.replace(" ", "");
        for (int i = 0; i < check.length(); i++) {
            char c = check.charAt(i);
            if (checkMathChar(c)) {
                break;
            }
            if (i == check.length() - 1) {
                return false;
            }
        }
        checkNotExpressionSymbol(check);
        return true;
    }

    public static boolean checkNotExpressionSymbol(String check) {
        for (int i = 0; i < check.length(); i++) {
            char c = check.charAt(i);
            if (!(checkMathChar(c) || checkDigitChar(c) || checkLetterChar(c))) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkLetterChar(char c) {
        return ((c >= 'a') && (c <= 'z'));
    }

    public static boolean checkDigitChar(char c) {
        return ((c >= '0') && (c <= '9'));
    }

    public static boolean checkMathChar(char c) {
        switch (c) {
            case ('-'):
            case ('+'):
            case ('*'):
            case ('/'):
            case ('('):
            case (')'):
                return true;
            default:
                return false;
        }
    }

    private static boolean processVariable(ServletRequest request, ServletResponse response)
            throws IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String variableValue = inputStreamToString(req.getInputStream());
        try {
            int varValue = Integer.parseInt(variableValue);
            if ((varValue < VARIABLE_VALUES_RANGE_NEG) || (varValue > VARIABLE_VALUES_RANGE)) {
                resp.sendError(SC_FORBIDDEN, "Forbidden: too big or too small");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void destroy() {
        //Override abstract method
    }

    @Override
    public void init(FilterConfig filterConfig) {
        //Override abstract method
    }
}
