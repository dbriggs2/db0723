package main;

import java.util.Scanner;

import main.ErrorMessages.ErrorMessage;
import main.dates.DateHelper;
import main.domain.RentalAgreement;
import main.domain.Tool;

import java.time.LocalDate;
import java.util.List;

public class RentalAgreementGenerator {

	public static final Integer MIN_RENTAL_DURATION = 1;
	public static final Integer MAX_RENTAL_DURATION = null; // no limit currently

	public static final int MIN_DISCOUNT_RATE = 0;
	public static final int MAX_DISCOUNT_RATE = 100;

	private static final boolean REQUIRE_FUTURE_RENTAL_DATE_FEATURE_TOGGLE = false;

	private static final String RENTAL_DURATION_PROMPT = "Input rental duration in number of days (include holidays and weekends): ";
	private static final String DISCOUNT_RATE_PROMPT = "Input discount rate as whole number (i.e. 20% -> 20): ";
	private static final String CHECKOUT_DATE_PROMPT = "Input check out date (MM/DD/YYYY): ";
	private static final String TOOLS_HEADER = "Available Tools: ";
	private static final String SELECT_TOOL_PROMPT = "Select tool code: ";

	private static final String RENTAL_DURATION_HEADER = "\nRental Agreement:";
	private static final String CLOSING_PROGRAM_NOTICE = "Closing program due to input error";

	public static void main(String[] args) throws Exception {
		List<Tool> tools = Tool.getTools();
		Scanner scanner = new Scanner(System.in);
		Tool selectedTool = null;
		LocalDate checkoutDate = null;
		int rentalDurationInDays = 0;
		int discountRateAsWholeNumber = 0;
		
		try {
			selectedTool = selectTool(tools, scanner);
			checkoutDate = inputCheckoutDate(scanner);
			rentalDurationInDays = inputRentalDuration(scanner);
			discountRateAsWholeNumber = inputDiscountRate(scanner);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(CLOSING_PROGRAM_NOTICE);
			System.exit(-1);
		}
		
		RentalAgreement rentalAgreement = new RentalAgreement(selectedTool, rentalDurationInDays, checkoutDate,
				(double) discountRateAsWholeNumber);
		System.out.println(RENTAL_DURATION_HEADER);
		System.out.println(rentalAgreement);
	}

	/*
	 * Fetches input from user for rental duration and throws an Exception if the
	 * input is invalid (i.e. contains letters) or is outside of the allowed range
	 * of rental duration.
	 * 
	 * If the input is valid it will return the rental duration as a int
	 */
	public static int inputRentalDuration(Scanner scanner) throws Exception {
		System.out.print(RENTAL_DURATION_PROMPT);
		int rentalDuration = 0;
		try {
			String input = scanner.next();
			rentalDuration = Integer.parseInt(input);
		} catch (Exception e) {
			throw new Exception(ErrorMessage.INVALID_RENTAL_DURATION_INPUT);
		}

		if (rentalDuration < MIN_RENTAL_DURATION && MAX_RENTAL_DURATION == null) {
			throw new Exception(String.format(ErrorMessage.LOWER_THAN_MINIMUM_RENTAL_DURATION, MIN_RENTAL_DURATION));
		} else if (rentalDuration < MIN_RENTAL_DURATION
				|| (MAX_RENTAL_DURATION != null && rentalDuration > MAX_RENTAL_DURATION)) {
			throw new Exception(String.format(ErrorMessage.RENTAL_DURATION_OUTSIDE_VALID_RANGE, MIN_RENTAL_DURATION,
					MAX_RENTAL_DURATION));
		}

		return rentalDuration;
	}

	/*
	 * Fetches input from user for discount rate and throws an Exception if the
	 * input is invalid (i.e. contains letters / decimals) or outside of the allowed
	 * range of rental duration.
	 * 
	 * If the input is valid it will return the discount rate as a int
	 */
	public static int inputDiscountRate(Scanner scanner) throws Exception {
		System.out.print(DISCOUNT_RATE_PROMPT);
		int discountRate = 0;
		try {
			String input = scanner.next();
			discountRate = Integer.parseInt(input);
		} catch (Exception e) {
			throw new Exception(ErrorMessage.INVALID_DISCOUNT_RATE_INPUT);
		}

		if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
			throw new Exception(String.format(ErrorMessage.DISCOUNT_RATE_OUTSIDE_VALID_RANGE, MIN_DISCOUNT_RATE,
					MAX_DISCOUNT_RATE));
		}

		return discountRate;
	}

	/*
	 * Fetches input from user for checkout date and throws an Exception if the
	 * input is invalid (i.e. not in MM/DD/YYYY format) or if the checkout date is
	 * in the past (requires feature toggle to be enabled)
	 * 
	 * If the input is valid it will return the checkout date as a LocalDate
	 */
	public static LocalDate inputCheckoutDate(Scanner scanner) throws Exception {
		System.out.print(CHECKOUT_DATE_PROMPT);
		LocalDate checkoutDate = null;
		String input = null;
		try {
			input = scanner.next();
			checkoutDate = LocalDate.parse(input, DateHelper.DATE_FORMATTER);
		} catch (Exception e) {
			throw new Exception(ErrorMessage.INVALID_CHECKOUT_DATE_INPUT);
		}

		if (REQUIRE_FUTURE_RENTAL_DATE_FEATURE_TOGGLE) {
			if (checkoutDate.isBefore(LocalDate.now())) {
				throw new Exception(ErrorMessage.CHECKOUT_DATE_IN_PAST);
			}
		}

		return checkoutDate;
	}

	/*
	 * Fetches input from user for tool to rent and throws an Exception if the input
	 * is invalid (i.e. contains letters) or if the selected tool is not one of the
	 * displayed options
	 * 
	 * If the input is valid it will return the selected tool as a Tool
	 */
	public static Tool selectTool(List<Tool> tools, Scanner scanner) throws Exception {
		System.out.println(TOOLS_HEADER);
		for (int i = 1; i < tools.size() + 1; i++) {
			Tool tool = tools.get(i - 1);
			System.out.println(i + ". " + tool.getBrand() + " " + tool.getTypeInfo().getToolType());
		}

		System.out.print(SELECT_TOOL_PROMPT);
		int selectedToolNumber = 0;
		try {
			selectedToolNumber = scanner.nextInt();
		} catch (Exception e) {
			throw new Exception(ErrorMessage.INVALID_TOOL_INPUT);
		}

		if (selectedToolNumber < 1 || selectedToolNumber > tools.size()) {
			throw new Exception(String.format(ErrorMessage.TOOL_OUTSIDE_RANGE_SELECTED, tools.size()));
		}
		int selectedToolIndex = selectedToolNumber - 1;
		return tools.get(selectedToolIndex);
	}
}
