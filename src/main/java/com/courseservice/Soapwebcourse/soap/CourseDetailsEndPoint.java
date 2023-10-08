package com.courseservice.Soapwebcourse.soap;

import com.courseservice.Soapwebcourse.soap.exception.CourseNotFoundException;
import com.courseservice.Soapwebcourse.soap.sevice.CourseDrtailsService;
import com.in28minutes.courses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;


@Endpoint
public class CourseDetailsEndPoint {

	@Autowired
	CourseDrtailsService service;
	//method 
	// Request - GetCourseDetailsRequest
	//Response - GetCourseDetailsResponse
	//http://in28minutes.com/courses
	//define url
	@PayloadRoot(namespace = "http://in28minutes.com/courses" , localPart = "GetCourseDetailsRequest")
	@ResponsePayload //handel Response 
	public GetCourseDetailsResponse processGetCourseDetailsRequest (
			@RequestPayload 	GetCourseDetailsRequest request) {  //@RequestPayload handel incoming request
		Course course = service.findById(request.getId());
		if (course == null)
			throw new CourseNotFoundException("Invalid Course Id" + request.getId());
		return mapCourseDetails(course);
	}
	
	@PayloadRoot(namespace = "http://in28minutes.com/courses" , localPart = "GetAllCourseDetailsRequest")
	@ResponsePayload //handel Response
	public GetAllCourseDetailsResponse processGetAllCourseDetailsRequest (
			@RequestPayload 	GetAllCourseDetailsRequest request) {  //@RequestPayload handel incoming request
		List<Course> courses = service.findAllCourses();
		return mapAllCourseDetails(courses);
	}

	@PayloadRoot(namespace = "http://in28minutes.com/courses" , localPart = "DeleteCourseDetailsRequest")
	@ResponsePayload //handel Response
	public DeleteCourseDetailsResponse processDeleteCourseDetailsResponse (
			@RequestPayload 	DeleteCourseDetailsRequest request) {  //@RequestPayload handel incoming request
		CourseDrtailsService.Status status = service.deleteById(request.getId());
		DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
		response.setStatus(mapStatus(status));
		return response;
	}




	private com.in28minutes.courses.Status mapStatus(com.courseservice.Soapwebcourse.soap.sevice.CourseDrtailsService.Status status) {
		
			if (status == com.courseservice.Soapwebcourse.soap.sevice.CourseDrtailsService.Status .FAILURE)
				return com.in28minutes.courses.Status.FAILURE;
			return com.in28minutes.courses.Status.SUCCESS;
		

	}

	private GetCourseDetailsResponse mapCourseDetails(Course course) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse ();
		response.setCourseDetails(mapCourse(course));
		
		return response;
	}
	private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
		GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse ();
		for(Course course:courses){
			CourseDetails mapCourse = mapCourse(course);
			response.getCourseDetails().add(mapCourse);
		}
		return response;
	}

	private CourseDetails mapCourse(Course course) {
		CourseDetails courseDetails = new CourseDetails ();
		courseDetails.setId(course.getId());
		courseDetails.setName(course.getName());
		courseDetails.setDescription(course.getDescription());
		return courseDetails;
	}



}
