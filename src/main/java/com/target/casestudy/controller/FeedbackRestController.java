package com.targert.casestudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.targert.casestudy.model.Status;
import com.targert.casestudy.service.UserComments;

@RestController
public class FeedbackRestController {

	@Autowired
	@Qualifier("userComments")
	UserComments userComments;

	@RequestMapping(value = "/casestudy/feeds/{str}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> getUser(@PathVariable("str") String comment) {

		Boolean flag = userComments.checkComments(comment);
		if (!flag) {
			return new ResponseEntity<Status>(HttpStatus.NOT_FOUND);
		}
		Status st = new Status(comment, flag);
		return new ResponseEntity<Status>(st, HttpStatus.OK);
	}

}