package main.ErrorMessages;

public class ErrorMessage {
	public static final String INVALID_TOOL_INPUT = "Please provide the number of the tool you would like to select";
	public static final String TOOL_OUTSIDE_RANGE_SELECTED = "Please input a number between 1 and %d";

	public static final String INVALID_CHECKOUT_DATE_INPUT = "Please input date in format of MM/DD/YYYY";
	public static final String CHECKOUT_DATE_IN_PAST = "Please input a checkout date either equal to or after the current date";

	public static final String INVALID_RENTAL_DURATION_INPUT = "Please input rental duration as whole number";
	public static final String LOWER_THAN_MINIMUM_RENTAL_DURATION = "Please input rental duration greater than %d";
	public static final String RENTAL_DURATION_OUTSIDE_VALID_RANGE = "Please input rental duration between %d and %d";

	public static final String INVALID_DISCOUNT_RATE_INPUT = "Please input discount rate as whole number";
	public static final String DISCOUNT_RATE_OUTSIDE_VALID_RANGE = "Please input discount rate between %d and %d";
}
