package application.model;

public class Goal {
	
	private String title;
	private String time;
	private String date;
	private double amount;
	
	public Goal( String title, String time, String date, double amount)
	{
		this.title = title;
		this.time = time;
		this.date = date;
		this.amount = amount;
	}

	//goal string
	public String toString() {
		String ret = this.getTitle() + "," + this.getTime() + "," + this.getDate() + "," + this.getAmount() + "\n";
		return ret;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * @return the date
	 */
	public String getDate() {
		return this.date;
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
