package main.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tool {
	private String code;
	private ToolTypeInfo typeInfo;
	private ToolBrand brand;

	public Tool(String code, ToolTypeInfo typeInfo, ToolBrand brand) {
		this.code = code;
		this.typeInfo = typeInfo;
		this.brand = brand;
	}

	public String getCode() {
		return code;
	}

	public ToolTypeInfo getTypeInfo() {
		return typeInfo;
	}

	public ToolBrand getBrand() {
		return brand;
	}

	/*
	 * Helper method to initialize tool type info and list of available tools to
	 * rent
	 */
	public static List<Tool> getTools() {
		List<Tool> tools = new ArrayList<>();

		ToolTypeInfo ladderInfo = new ToolTypeInfo(ToolType.LADDER, 1.99, true, true, false);
		ToolTypeInfo chainsawInfo = new ToolTypeInfo(ToolType.CHAINSAW, 1.49, true, false, true);
		ToolTypeInfo jackhammerInfo = new ToolTypeInfo(ToolType.JACKHAMMER, 2.99, true, false, false);

		tools.add(new Tool("CHNS", chainsawInfo, ToolBrand.STIHL));
		tools.add(new Tool("LADW", ladderInfo, ToolBrand.WERNER));
		tools.add(new Tool("JAKD", jackhammerInfo, ToolBrand.DEWALT));
		tools.add(new Tool("JAKR", jackhammerInfo, ToolBrand.RIDGID));

		return tools;
	}

	@Override
	public int hashCode() {
		return Objects.hash(brand, code, typeInfo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tool other = (Tool) obj;
		return brand == other.brand && Objects.equals(code, other.code) && Objects.equals(typeInfo, other.typeInfo);
	}
}
