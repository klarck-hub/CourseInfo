package com.pluralsight.courseinfo.cli;


import com.pluralsight.courseinfo.cli.service.CourseRetrievalService;
import com.pluralsight.courseinfo.cli.service.PluralsightCourse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CourseRetriever {

    public static final Logger LOG = LoggerFactory.getLogger(CourseRetriever.class);

    public static void main(String... args) throws Exception {
        LOG.info("CourseRetriever started!");

        if(args.length == 0 ){
            LOG.warn("Please provide an author name as first argument");
            return;
        }
        try{
            retrieveCourses(args[0]);

        } catch (Exception e){
            LOG.error("Unexpected error",e);
        }
    }

    private static void retrieveCourses(String authorId) throws IOException, InterruptedException {
        LOG.info("Retrieving courses from author '{}' " , authorId);
        CourseRetrievalService courseRetrievalService = new CourseRetrievalService();
        List<PluralsightCourse> coursesToStore =
                courseRetrievalService.getCoursesFor(authorId)
                .stream()
                .filter(Predicate.not(PluralsightCourse::isRetired))
                .toList();

        LOG.info("Retrieved the following {} course {}",coursesToStore.size() , coursesToStore);



    }

}
