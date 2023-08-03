package travelplanner.project.demo.planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travelplanner.project.demo.planner.domain.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}