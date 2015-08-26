package fastreflect;


public class Bean extends Parent{
	
	private String name;
	
	private int age;
	
	private byte byteType;
	
	private boolean bool;
	
	private float floatType;
	
	private double doub;
	
	private long longType;
	
	private short shortType;
	private char charType;
	
	private int[] intArray;
	private long[][] longAry;
	private Integer[][][] integerArray;
	public Bean() {
		super();
	}
	
	public static class InnerClass{
		
		public static class IIClass{
			private String N;

			public String getN() {
				return N;
			}

			public void setN(String n) {
				N = n;
			}
		}
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public byte getByteType() {
		return byteType;
	}

	public void setByteType(byte byteType) {
		this.byteType = byteType;
	}

	public boolean isBool() {
		return bool;
	}

	public void setBool(boolean bool) {
		this.bool = bool;
	}

	public float getFloatType() {
		return floatType;
	}

	public void setFloatType(float floatType) {
		this.floatType = floatType;
	}

	public double getDoub() {
		return doub;
	}

	public void setDoub(double doub) {
		this.doub = doub;
	}

	public long getLongType() {
		return longType;
	}

	public void setLongType(long longType) {
		this.longType = longType;
	}

	public short getShortType() {
		return shortType;
	}

	public void setShortType(short shortType) {
		this.shortType = shortType;
	}

	public char getCharType() {
		return charType;
	}

	public void setCharType(char charType) {
		this.charType = charType;
	}

	public int[] getIntArray() {
		return intArray;
	}

	public void setIntArray(int[] intArray) {
		this.intArray = intArray;
	}

	public long[][] getLongAry() {
		return longAry;
	}

	public void setLongAry(long[][] longAry) {
		this.longAry = longAry;
	}

	public Integer[][][] getIntegerArray() {
		return integerArray;
	}

	public void setIntegerArray(Integer[][][] integerArray, int a) {
		this.integerArray = integerArray;
	}
}
