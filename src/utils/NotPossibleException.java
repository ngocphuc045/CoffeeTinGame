package utils;

/**
 * OVERVIEW: a run-time exception that is (not expected to be) thrown 
 * by a method that could not perform its operation due to input 
 * data errors.
 * 
 * @author dmle
 *
 */
public class NotPossibleException extends RuntimeException {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public NotPossibleException(String s) {
    super(s);
  }
}
