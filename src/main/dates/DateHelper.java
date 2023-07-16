package main.dates;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class DateHelper {
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/uuuu");

	/*
	 * Returns Dates of Holidays for all years the rental agreement is in effect,
	 * i.e. a rental agreement from 08/30/2022 - 07/07/2023 will fetch the holiday
	 * dates for 2022 and 2023
	 */
	public static Set<LocalDate> getDatesOfHolidays(LocalDate checkoutDate, LocalDate dueDate) {
		Set<LocalDate> datesOfHolidays = new HashSet<>();
		// datesUntil is exclusive but we charge for the due date so it needs to be
		// included in case of the due date being an observed 4th of july
		Stream<LocalDate> datesOfRental = checkoutDate.datesUntil(dueDate.plusDays(1));

		List<Integer> yearsToCalculateHolidaysFor = datesOfRental.map(LocalDate::getYear).distinct()
				.collect(Collectors.toList());
//		System.out.println(String.format("DEBUG: yearsToCalculateHolidaysFor: %s", yearsToCalculateHolidaysFor));
		
		yearsToCalculateHolidaysFor.forEach(year -> datesOfHolidays.addAll(getDatesOfHolidaysForYear(year)));

		return datesOfHolidays;
	}

	/*
	 * Returns Set of 2 LocalDates, 1 for observed date of 4th of July, 1 for
	 * LaborDay, for a specified year
	 * 
	 * If the 4th of July lands on a Saturday, it will be observed on the 3rd If the
	 * 4th of July lands on a Sunday, it will be observed on the 5th Otherwise it
	 * will be observed on the 4th
	 * 
	 * Labor day will be on the first Monday of September
	 */
	public static Set<LocalDate> getDatesOfHolidaysForYear(int year) {
		Set<LocalDate> datesOfHolidaysForYear = new HashSet<>();

		LocalDate fourthOfJuly = LocalDate.of(year, 7, 4);
		if (fourthOfJuly.getDayOfWeek() == DayOfWeek.SATURDAY) {
			datesOfHolidaysForYear.add(LocalDate.of(year, 7, 3));
		} else if (fourthOfJuly.getDayOfWeek() == DayOfWeek.SUNDAY) {
			datesOfHolidaysForYear.add(LocalDate.of(year, 7, 5));
		} else {
			datesOfHolidaysForYear.add(fourthOfJuly);
		}

		LocalDate firstDateOfSeptember = LocalDate.of(year, 9, 1);
		LocalDate eighthDateOfSeptember = LocalDate.of(year, 9, 8);

		// datesUntil is exclusive so this will loop through first week only
		LocalDate laborDayDate = firstDateOfSeptember.datesUntil(eighthDateOfSeptember)
				.filter(date -> date.getDayOfWeek() == DayOfWeek.MONDAY).findFirst().get();

		datesOfHolidaysForYear.add(laborDayDate);
		
//		System.out.println(String.format("DEBUG: datesOfHolidaysForYear for %d : %s", year, datesOfHolidaysForYear));
		return datesOfHolidaysForYear;
	}
}
