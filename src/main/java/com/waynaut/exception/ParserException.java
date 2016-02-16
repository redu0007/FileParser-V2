
package com.waynaut.exception;

/**
 *
 * @author Rahman Md Redwanur
 */
public class ParserException extends Exception{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String exceptionCode;

    /**
     * Custom exception to understand a error message, code and trace for further analysis.
     * @param userException user understandable error message
     * @param exp For tracing classes of application. 
     * @param exceptionCode For users, to solve the problem using error message code that described in corresponding classes.
     */
    public ParserException(String userException, Throwable exp, String exceptionCode) {
        super(userException,exp);

        this.exceptionCode = exceptionCode;
    }
    
    /**
     * Get exception code.
     * @return 
     */
    public String getExceptionCode() {
        return exceptionCode;
    }
    
    /**
     * Use to get Calling method name
     * @return 
     */
    public static String getCallingMethod() {
        StackTraceElement[] elements = new Throwable().getStackTrace();
        return elements.length > 1 ? elements[1].getMethodName() : "";
    }
}
