package main.domain;

import java.util.Objects;

public class ToolTypeInfo {
	private ToolType toolType;
	private double dailyCharge;
	private boolean isChargeableOnWeekday; //currently all tool types are chargeable on weekdays but could change in future
	private boolean isChargeableOnWeekend;
	private boolean isChargeableOnHoliday;

	public ToolTypeInfo(ToolType toolType, Double dailyCharge, boolean isChargeableOnWeekday,
			boolean isChargeableOnWeekend, boolean isChargeableOnHoliday) {
		this.toolType = toolType;
		this.dailyCharge = dailyCharge;
		this.isChargeableOnWeekday = isChargeableOnWeekday;
		this.isChargeableOnWeekend = isChargeableOnWeekend;
		this.isChargeableOnHoliday = isChargeableOnHoliday;
	}

	public ToolType getToolType() {
		return toolType;
	}

	public double getDailyCharge() {
		return dailyCharge;
	}

	public boolean isChargeableOnWeekday() {
		return isChargeableOnWeekday;
	}

	public boolean isChargeableOnWeekend() {
		return isChargeableOnWeekend;
	}

	public boolean isChargeableOnHoliday() {
		return isChargeableOnHoliday;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dailyCharge, isChargeableOnHoliday, isChargeableOnWeekday, isChargeableOnWeekend, toolType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ToolTypeInfo other = (ToolTypeInfo) obj;
		return Double.doubleToLongBits(dailyCharge) == Double.doubleToLongBits(other.dailyCharge)
				&& isChargeableOnHoliday == other.isChargeableOnHoliday
				&& isChargeableOnWeekday == other.isChargeableOnWeekday
				&& isChargeableOnWeekend == other.isChargeableOnWeekend && toolType == other.toolType;
	}
}
