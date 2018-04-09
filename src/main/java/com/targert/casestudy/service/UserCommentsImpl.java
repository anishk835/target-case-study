package com.targert.casestudy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service("userComments")
public class UserCommentsImpl implements UserComments {

	private static List<String> comments;

	static {
		comments = new ArrayList<String>();
		addComments();
	}

	public UserCommentsImpl() {

	}

	public boolean checkComments(String str) {
		return comments.contains(str) ? true : false;
	}

	private static void addComments() {
		comments.add("Product is good");
		comments.add("Nice product");
		comments.add("Value for money");
	}

}
