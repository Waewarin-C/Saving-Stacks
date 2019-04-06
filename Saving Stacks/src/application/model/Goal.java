package application.model;

public class Goal {
	
	private String title;
	private String date;
	private double amount;
	
	public Goal( String title, String date, double amount)
	{
		this.title = title;
		this.date = date;
		this.amount = amount;
	}

	//goal string
	public String toString() {
		String ret = this.getTitle() + "," + this.getDate() + "," + this.getAmount() + "\n";
		return ret;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

}
