/*package org.springframework.integration.samples.dsl.kafka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={InfrastructureConfiguration.class})
public class AppTest {
   
	
	@Autowired
    CourseService service;
     
    @Test
    public void testFlow() {
        String courseName = service.findCourse("BC-45");
        assertNotNull(courseName);
        assertEquals("Introduction to Java", courseName);
         
        courseName = service.findCourse("DF-21");
        assertNotNull(courseName);
        assertEquals("Functional Programming Principles in Scala", courseName);
    }
}

*/