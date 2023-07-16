package test.domain;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import main.domain.RentalAgreement;
import main.domain.Tool;

class TestRentalAgreement {
	private List<Tool> tools = Tool.getTools();
	private final Tool CHNS = tools.get(0);
	private final Tool LADW = tools.get(1);
	private final Tool JAKD = tools.get(2);
	private final Tool JAKR = tools.get(3);

	// test scenario 1 would go here but 101 is not a valid discount rate and as
	// such it was tested
	// in the testDriver class in the inputDiscountRate method

	@Test
	void testCalculateFinalCharge_scenario2_returnCorrectAgreement() {
		RentalAgreement expected = new RentalAgreement(LADW, 3, LocalDate.of(2020, 7, 2), 10.0,
				LocalDate.of(2020, 7, 5), 2, 3.98, 0.40, 3.58);
		RentalAgreement actual = new RentalAgreement(LADW, 3, LocalDate.of(2020, 7, 2), 10.0);
		assertEquals(expected, actual);
	}

	@Test
	void testCalculateFinalCharge_scenario3_returnCorrectAgreement() {
		RentalAgreement expected = new RentalAgreement(CHNS, 5, LocalDate.of(2015, 7, 2), 25.0,
				LocalDate.of(2015, 7, 7), 3, 4.47, 1.12, 3.35);
		RentalAgreement actual = new RentalAgreement(CHNS, 5, LocalDate.of(2015, 7, 2), 25.0);

		assertEquals(expected, actual);
	}

	@Test
	void testCalculateFinalCharge_scenario4_returnCorrectAgreement() {
		RentalAgreement expected = new RentalAgreement(JAKD, 6, LocalDate.of(2015, 9, 3), 0.0, LocalDate.of(2015, 9, 9),
				3, 8.97, 0.0, 8.97);
		RentalAgreement actual = new RentalAgreement(JAKD, 6, LocalDate.of(2015, 9, 3), 0.0);

		assertEquals(expected, actual);
	}

	@Test
	void testCalculateFinalCharge_scenario5_returnCorrectAgreement() {
		RentalAgreement expected = new RentalAgreement(JAKR, 9, LocalDate.of(2015, 7, 2), 0.0,
				LocalDate.of(2015, 7, 11), 6, 17.94, 0, 17.94);
		RentalAgreement actual = new RentalAgreement(JAKR, 9, LocalDate.of(2015, 7, 2), 0.0);

		assertEquals(expected, actual);
	}

	@Test
	void testCalculateFinalCharge_scenario6_returnCorrectAgreement() {
		RentalAgreement expected = new RentalAgreement(JAKR, 4, LocalDate.of(2020, 7, 2), 50.0,
				LocalDate.of(2020, 7, 6), 1, 2.99, 1.50, 1.49);
		RentalAgreement actual = new RentalAgreement(JAKR, 4, LocalDate.of(2020, 7, 2), 50.0);

		assertEquals(expected, actual);
	}

	@Test
	void testCalculateNumOfChargeableDays_weekdaysChargeableWeekendsNotChargeableHolidaysChargeable_returnCorrectNumber() {
		int expected = 5;
		int actual = new RentalAgreement(CHNS, 7, LocalDate.of(2023, 7, 1), 20).calculateNumOfChargeableDays();

		assertEquals(expected, actual);
	}

	@Test
	void testCalculateNumOfChargeableDays_weekdaysChargeableWeekendsChargeableHolidaysNotChargeable_returnCorrectNumber() {
		int expected = 6;
		int actual = new RentalAgreement(LADW, 7, LocalDate.of(2023, 7, 1), 20).calculateNumOfChargeableDays();

		assertEquals(expected, actual);
	}

}
