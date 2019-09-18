package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.service.CourseService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/courses")
public class CourseController
{
    @Autowired
    private CourseService courseService;

    // http://localhost:2019/courses/courses/?sort=coursename,asc&page=0&size=3
    @ApiOperation(value = "return all courses using Paging and Sorting",
            response = Course.class,
            responseContainer = "ArrayList")
    @ApiImplicitParams({@ApiImplicitParam(name = "page",
            dataType = "integer",
            paramType = "query",
            value = "Results page you want to retrieve(0...N)"), @ApiImplicitParam(name = "size",
            dataType = "integer",
            paramType = "query",
            value = "Number of records " + "per page"), @ApiImplicitParam(name = "sort",
            allowMultiple = true,
            dataType = "string",
            paramType = "query",
            value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple " + "sort criteria are suported")})
    @GetMapping(value = "/courses",
            produces = {"application/json"})
    public ResponseEntity<?> listAllCourses(
            @PageableDefault(page = 0,
                    size = 3)
                    Pageable pageable)
    {
        ArrayList<Course> myCourses = courseService.findAll(pageable);
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }

    @ApiOperation(value = "Return a Count of Students in Courses",
            response = Course.class,
            responseContainer = "List")
    @GetMapping(value = "/studcount",
            produces = {"application/json"})
    public ResponseEntity<?> getCountStudentsInCourses()
    {
        return new ResponseEntity<>(courseService.getCountStudentsInCourse(), HttpStatus.OK);
    }

    @ApiOperation(value="Deletes a Course Based on Id",
            response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Course Successfully Deleted",
                    response = void.class)
    })
    @DeleteMapping("/courses/{courseid}")
    public ResponseEntity<?> deleteCourseById(
            @ApiParam(value = "Course Id",
                    required = true,
                    example = "1")
            @PathVariable
                    long courseid)
    {
        courseService.delete(courseid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
