package com.pluralsight.courseinfo.cli.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PluralsightCourseTest {

    @Test
    void durationInMinutes() {
        PluralsightCourse pluralsightCourse =
                new PluralsightCourse("id","Test course","00:05:37", "url", false);
        assertEquals(5,pluralsightCourse.durationInMinutes());
    }

    @Test
    void durationInMinutesOverHour() {
        PluralsightCourse pluralsightCourse =
                new PluralsightCourse("id","Test course","01:08:54.9613330", "url", false);
        assertEquals(68,pluralsightCourse.durationInMinutes());
    }

    @Test
    void durationInMinuteZero() {
        PluralsightCourse pluralsightCourse =
                new PluralsightCourse("id","Test course","00:00:00", "url", false);
        assertEquals(0,pluralsightCourse.durationInMinutes());
    }
}