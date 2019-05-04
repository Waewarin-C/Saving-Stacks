package application.model;

/**
 * Goal.java class sets up the Goal object, it's toString() and getters and setters.
 * 
 * @author Chelsea Flores (rue750)
 *
 */
public class Goal {
	
	/*
	 * Class variables
	 */
	private String title;
	private String time;
	private String date;
	private double amount;
	
	/**
	 * Goal constructor.
	 * 
	 * @param title - String of the goal title.
	 * @param time - String of the timeframe of the goal.
	 * @param date - String of the date the goal was entered.
	 * @param amount - double of the amount of the goal.
	 */
	public Goal( String title, String time, String date, double amount)
	{
		this.title = title;
		this.time = time;
		this.date = date;
		this.amount = amount;
	}

	/**
	 * toString creates a String of the Goal object.
	 * 
	 * @return ret - String of the Goal object.
	 */
	public String toString() {
		String ret = this.getTitle() + "," + this.getTime() + "," + this.getDate() + "," + this.getAmount() + "\n";
		return ret;
	}
	
	/**
	 * Returns the title of the goal.
	 * 
	 * @return String of the goal title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the goal.
	 * 
	 * @param title - String of the goal title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the timeframe of the goal.
	 * 
	 * @return the time - String of the goal timeframe.
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
