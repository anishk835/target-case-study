package com.target.casestudy.model;

public class Status {

	private String str;
	private boolean bol;

	public Status(String str, boolean bol) {
		this.str = str;
		this.bol = bol;
	}

	public String getStr() {
		return str;
	}

	public boolean isBol() {
		return bol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (bol ? 1231 : 1237);
		result = prime * result + ((str == null) ? 0 : str.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Status other = (Status) obj;
		if (bol != other.bol)
			return false;
		if (str == null) {
			if (other.str != null)
				return false;
		} else if (!str.equals(other.str))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Status [str=" + str + ", bol=" + bol + "]";
	}

}
