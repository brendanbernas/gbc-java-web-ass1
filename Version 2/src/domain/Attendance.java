package domain;

import java.util.Date;

public class Attendance {
	private int id;
	private Date date;
	
	public Attendance(int id, Date date) {
		this.id = id;
		this.date = date;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
