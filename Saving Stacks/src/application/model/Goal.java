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
	 * Sets the timeframe of the goal object.
	 * 
	 * @param time the time to set of string of the timeframe of the goal.
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * Sets the entry date of the transaction.
	 * 
	 * @param date the date to set of the String of the entry date.
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * Returns the entry date of the transaction.
	 * 
	 * @return the date String of the date the transaction was entered.
	 */
	public String getDate() {
		return this.date;
	}
	
	/**
	 * getAmount returns the double amount of the goal.
	 * 
	 * @return the amount double of the goal dollar amount.
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * setAmount sets the dollar amount of the goal.
	 * 
	 * @param amount the amount to set double as the amount value.
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

}
