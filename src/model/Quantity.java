package model;

/**
 * 
 * @author C.B.
 * @version 1.2
 * @since 1.2
 *
 */
public class Quantity {

	private String unit;

	private double quantity;
	
	public static final char SEPARATOR = '|';

	/**
	 * @param unit
	 * @param quantity
	 */
	public Quantity(String unit, double quantity) {
		super();
		this.unit = unit;
		this.quantity = quantity;
	}

	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * @return the quantity
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(quantity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Quantity other = (Quantity) obj;
		if (Double.doubleToLongBits(quantity) != Double.doubleToLongBits(other.quantity))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  unit + Quantity.SEPARATOR + quantity ;
	}

	/**
	 * 
	 * @param line
	 * @return {@link Quantity}
	 */
	public static Quantity parse(String line) {
		if(line==null) {
			return null;
		}
		String info[]  = line.split("\\"+String.valueOf(Quantity.SEPARATOR));
		if(info.length!=2) {
			return null;
		}
		String unit = info[0];
		double quantity = Double.parseDouble(info[1]);
		return new Quantity(unit, quantity);
	}



}
