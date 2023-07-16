package test.dates;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import main.dates.DateHelper;

class TestDateHelper {	
	@Test
	void testGetDatesOfHolidaysForYear_weekday4thOfJuly_return4thOfJulyAndLaborDay() {
		Set<LocalDate> expected = new HashSet<>();
		expected.add(LocalDate.of(2023, 7, 4));
		expected.add(LocalDate.of(2023, 9, 4));
		
		Set<LocalDate> actual = DateHelper.getDatesOfHolidaysForYear(2023);
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetDatesOfHolidaysForYear_Saturday4thOfJuly_return3rdOfJulyAndLaborDay() {
		Set<LocalDate> expected = new HashSet<>();
		expected.add(LocalDate.of(2020, 7, 3));
		expected.add(LocalDate.of(2020, 9, 7));
		
		Set<LocalDate> actual = DateHelper.getDatesOfHolidaysForYear(2020);
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetDatesOfHolidaysForYear_Sunday4thOfJuly_return5thOfJulyAndLaborDay() {
		Set<LocalDate> expected = new HashSet<>();
		expected.add(LocalDate.of(2021, 7, 5));
		expected.add(LocalDate.of(2021, 9, 6));
		
		Set<LocalDate> actual = DateHelper.getDatesOfHolidaysForYear(2021);
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetDatesOfHolidays_singleYear_returnHolidaysForSingleYear() {
		Set<LocalDate> expected = new HashSet<>();
		expected.add(LocalDate.of(2023, 7, 4));
		expected.add(LocalDate.of(2023, 9, 4));
		
		Set<LocalDate> actual = DateHelper.getDatesOfHolidays(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 2));
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetDatesOfHolidays_multipleYears_returnHolidaysForAllYears() {
		Set<LocalDate> expected = new HashSet<>();
		expected.add(LocalDate.of(2020, 7, 3));
		expected.add(LocalDate.of(2020, 9, 7));
		expected.add(LocalDate.of(2021, 7, 5));
		expected.add(LocalDate.of(2021, 9, 6));
		
		Set<LocalDate> actual = DateHelper.getDatesOfHolidays(LocalDate.of(2020, 12, 31), LocalDate.of(2021, 1, 1));
		assertEquals(expected, actual);
	}
}
