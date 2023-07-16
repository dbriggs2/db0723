package main.domain;

import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import main.dates.DateHelper;

public class RentalAgreement {
	private final Locale LOCALE = new Locale.Builder().setLanguage("en").setRegion("us").build();
	private final NumberFormat MONEY_FORMATTER = NumberFormat.getCurrencyInstance(LOCALE);
	private NumberFormat PERCENT_FORMATTER = NumberFormat.getPercentInstance(LOCALE);
	private final double DELTA = .00000000000001;

	// provided via user input
	private Tool tool;
	private int rentalDurationInDays;
	private LocalDate checkoutDate;
	private double discountRateAsDecimal;

	// calculated via above fields
	private LocalDate dueDate;
	private int numOfChargeableDays;
	private double preDiscountCharge;
	private double discountAmount;
	private double finalCharge;

	public RentalAgreement(Tool tool, int rentalDurationInDays, LocalDate checkoutDate,
			double discountRateAsWholeNumber, LocalDate dueDate, int numOfChargeableDays, double preDiscountCharge,
			double discountAmount, double finalCharge) {
		this.tool = tool;
		this.rentalDurationInDays = rentalDurationInDays;
		this.checkoutDate = checkoutDate;
		this.discountRateAsDecimal = discountRateAsWholeNumber / 100;
		this.dueDate = dueDate;
		this.numOfChargeableDays = numOfChargeableDays;
		this.preDiscountCharge = preDiscountCharge;
		this.discountAmount = discountAmount;
		this.finalCharge = finalCharge;
	}

	public RentalAgreement(Tool tool, int rentalDurationInDays, LocalDate checkoutDate,
			double discountRateAsWholeNumber) {
		this.tool = tool;
		this.rentalDurationInDays = rentalDurationInDays;
		this.checkoutDate = checkoutDate;
		this.discountRateAsDecimal = discountRateAsWholeNumber / 100;
		this.dueDate = checkoutDate.plusDays(rentalDurationInDays);

		this.calculateFinalCharge();
	}

	private void calculateFinalCharge() {
		this.numOfChargeableDays = calculateNumOfChargeableDays();
		this.preDiscountCharge = numOfChargeableDays * this.tool.getTypeInfo().getDailyCharge();
		// round up discount amount to 2 decimal places to avoid rounding errors
		this.discountAmount = Double.parseDouble(String.format("%.2f", discountRateAsDecimal * preDiscountCharge));
		this.finalCharge = this.preDiscountCharge - this.discountAmount;
	}

	/*
	 * Returns number of chargeable days for the rental agreement. The tool type
	 * designates whether a weekday, weekend, or holiday is chargeable or not
	 */
	public int calculateNumOfChargeableDays() {
		Set<LocalDate> datesOfHolidays = DateHelper.getDatesOfHolidays(checkoutDate, dueDate);

		Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
				|| date.getDayOfWeek() == DayOfWeek.SUNDAY;
		Predicate<LocalDate> isHoliday = date -> datesOfHolidays.contains(date);

		Stream<LocalDate> datesOfRental = checkoutDate.datesUntil(dueDate);
		int numberOfDays = (int) datesOfRental.count();
//		System.out.println(String.format("DEBUG: numberOfDays: %d", numberOfDays));
		
		int numberOfWeekendDays = 0;
		if (!tool.getTypeInfo().isChargeableOnWeekend()) {
			datesOfRental = checkoutDate.datesUntil(dueDate);
			numberOfWeekendDays = (int) datesOfRental.filter(isWeekend).count();
		}
//		System.out.println(String.format("DEBUG: numberOfWeekendDays: %d", numberOfWeekendDays));

		int numberOfHolidays = 0;
		if (!tool.getTypeInfo().isChargeableOnHoliday()) {
			datesOfRental = checkoutDate.datesUntil(dueDate);
			numberOfHolidays = (int) datesOfRental.filter(isHoliday).count();
		}
//		System.out.println(String.format("DEBUG: numberOfHolidays: %d", numberOfHolidays));


		return numberOfDays - numberOfWeekendDays - numberOfHolidays;
	}

	@Override
	public String toString() {
		return "Tool code: " + tool.getCode() + "\n" + "Tool type: " + tool.getTypeInfo().getToolType() + "\n"
				+ "Tool brand: " + tool.getBrand() + "\n" + "Rental duration : " + rentalDurationInDays + "\n"
				+ "Checkout date: " + checkoutDate.format(DateHelper.DATE_FORMATTER) + "\n" + "Due date: "
				+ dueDate.format(DateHelper.DATE_FORMATTER) + "\n" + "Daily rental charge: "
				+ MONEY_FORMATTER.format(tool.getTypeInfo().getDailyCharge()) + "\n" + "Number of Chargeable Days: "
				+ numOfChargeableDays + "\n" + "Pre discount amount: " + MONEY_FORMATTER.format(preDiscountCharge)
				+ "\n" + "Discount rate: " + PERCENT_FORMATTER.format(discountRateAsDecimal) + "\n"
				+ "Discount amount: " + MONEY_FORMATTER.format(discountAmount) + "\n" + "Final charge: "
				+ MONEY_FORMATTER.format(finalCharge);
	}

	@Override
	public int hashCode() {
		return Objects.hash(checkoutDate, discountAmount, discountRateAsDecimal, dueDate, finalCharge,
				numOfChargeableDays, preDiscountCharge, rentalDurationInDays, tool);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RentalAgreement other = (RentalAgreement) obj;
		return checkoutDate.equals(other.checkoutDate) && Math.abs(discountAmount - other.discountAmount) < DELTA
				&& Math.abs(discountRateAsDecimal - other.discountRateAsDecimal) < DELTA
				&& dueDate.equals(other.dueDate) && Math.abs(finalCharge - other.finalCharge) < DELTA
				&& numOfChargeableDays == other.numOfChargeableDays
				&& Math.abs(preDiscountCharge - other.preDiscountCharge) < DELTA
				&& rentalDurationInDays == other.rentalDurationInDays && tool.equals(other.tool);
	}
}
