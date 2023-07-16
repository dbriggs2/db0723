package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import main.RentalAgreementGenerator;
import main.ErrorMessages.ErrorMessage;
import main.domain.Tool;

class TestRentalAgreementGenerator {
	List<Tool> tools = Tool.getTools();

	@Test
	void testSelectTool_toolOutsideRangeSelected_throwsException() {
		String input = "0";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		Scanner scanner = new Scanner(System.in);

		Exception e = assertThrows(Exception.class, () -> RentalAgreementGenerator.selectTool(tools, scanner));
		String expected = String.format(ErrorMessage.TOOL_OUTSIDE_RANGE_SELECTED, tools.size());
		String actual = e.getMessage();
		assertEquals(expected, actual);
		;
	}

	@Test
	void testSelectTool_validToolSelected_returnsToolSelected() throws Exception {
		String input = "1";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		Scanner scanner = new Scanner(System.in);

		Tool expected = tools.get(0);
		Tool actual = RentalAgreementGenerator.selectTool(tools, scanner);
		assertEquals(expected, actual);
	}

	@Test
	void testSelectTool_nonNumberInputProvided_throwsException() {
		String input = "abcd";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		Scanner scanner = new Scanner(System.in);

		Exception e = assertThrows(Exception.class, () -> RentalAgreementGenerator.selectTool(tools, scanner));
		String expected = ErrorMessage.INVALID_TOOL_INPUT;
		String actual = e.getMessage();
		assertEquals(expected, actual);
	}

	@Test
	void testInputCheckoutDate_nonDateInputProvided_throwsException() {
		String input = "abcd";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		Scanner scanner = new Scanner(System.in);

		Exception e = assertThrows(Exception.class, () -> RentalAgreementGenerator.inputCheckoutDate(scanner));
		String expected = ErrorMessage.INVALID_CHECKOUT_DATE_INPUT;
		String actual = e.getMessage();
		assertEquals(expected, actual);
	}

	@Test
	void testInputCheckoutDate_invalidDateFormat_throwsException() {
		String input = "12-12-2022";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		Scanner scanner = new Scanner(System.in);

		Exception e = assertThrows(Exception.class, () -> RentalAgreementGenerator.inputCheckoutDate(scanner));
		String expected = ErrorMessage.INVALID_CHECKOUT_DATE_INPUT;
		String actual = e.getMessage();
		assertEquals(expected, actual);
	}

	@Test
	void testInputCheckoutDate_validCheckoutDateProvided_returnsCheckoutDate() throws Exception {
		String input = "12/12/2022";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		Scanner scanner = new Scanner(System.in);

		LocalDate expected = LocalDate.of(2022, 12, 12);
		LocalDate actual = RentalAgreementGenerator.inputCheckoutDate(scanner);

		assertEquals(expected, actual);
	}

	@Test
	void testInputDiscountRate_nonNumberInputProvided_throwsException() {
		String input = "abcd";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		Scanner scanner = new Scanner(System.in);

		Exception e = assertThrows(Exception.class, () -> RentalAgreementGenerator.inputDiscountRate(scanner));
		String expected = ErrorMessage.INVALID_DISCOUNT_RATE_INPUT;
		String actual = e.getMessage();
		assertEquals(expected, actual);
	}

	@Test
	void testInputDiscountRate_decimalRateProvided_throwsException() {
		String input = "10.2";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		Scanner scanner = new Scanner(System.in);

		Exception e = assertThrows(Exception.class, () -> RentalAgreementGenerator.inputDiscountRate(scanner));
		String expected = ErrorMessage.INVALID_DISCOUNT_RATE_INPUT;
		String actual = e.getMessage();
		assertEquals(expected, actual);
	}

	@Test
	void testInputDiscountRate_excessiveDiscountRateProvided_throwsException() { // scenario 1
		String input = "101";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		Scanner scanner = new Scanner(System.in);

		Exception e = assertThrows(Exception.class, () -> RentalAgreementGenerator.inputDiscountRate(scanner));
		String expected = String.format(ErrorMessage.DISCOUNT_RATE_OUTSIDE_VALID_RANGE, RentalAgreementGenerator.MIN_DISCOUNT_RATE, RentalAgreementGenerator.MAX_DISCOUNT_RATE);
		String actual = e.getMessage();
		assertEquals(expected, actual);
	}

	@Test
	void testInputDiscountRate_negativeDiscountRateProvided_throwsException() {
		String input = "-17";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		Scanner scanner = new Scanner(System.in);

		Exception e = assertThrows(Exception.class, () -> RentalAgreementGenerator.inputDiscountRate(scanner));
		String expected = String.format(ErrorMessage.DISCOUNT_RATE_OUTSIDE_VALID_RANGE, RentalAgreementGenerator.MIN_DISCOUNT_RATE, RentalAgreementGenerator.MAX_DISCOUNT_RATE);
		String actual = e.getMessage();
		assertEquals(expected, actual);
	}

	@Test
	void testInputDiscountRate_validDiscountRateProvided_returnsDiscountRate() throws Exception {
		String input = "20";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		Scanner scanner = new Scanner(System.in);

		int expected = 20;
		int actual = RentalAgreementGenerator.inputDiscountRate(scanner);

		assertEquals(expected, actual);
	}

	@Test
	void testInputRentalDuration_nonNumberInputProvided_throwsException() {
		String input = "abcd";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		Scanner scanner = new Scanner(System.in);

		Exception e = assertThrows(Exception.class, () -> RentalAgreementGenerator.inputRentalDuration(scanner));
		String expected = ErrorMessage.INVALID_RENTAL_DURATION_INPUT;
		String actual = e.getMessage();
		assertEquals(expected, actual);
	}

	@Test
	void testInputRentalDuration_lowerThanMinimumNumberProvided_throwsException() {
		String input = "0";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		Scanner scanner = new Scanner(System.in);

		Exception e = assertThrows(Exception.class, () -> RentalAgreementGenerator.inputRentalDuration(scanner));
		String expected = String.format(ErrorMessage.LOWER_THAN_MINIMUM_RENTAL_DURATION, RentalAgreementGenerator.MIN_RENTAL_DURATION);
		String actual = e.getMessage();
		assertEquals(expected, actual);

	}

	@Test
	void testInputRentalDuration_validRentalDurationProvided_returnsRentalDuration() throws Exception {
		String input = "20";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		Scanner scanner = new Scanner(System.in);

		int expected = 20;
		int actual = RentalAgreementGenerator.inputRentalDuration(scanner);

		assertEquals(expected, actual);
	}
}
