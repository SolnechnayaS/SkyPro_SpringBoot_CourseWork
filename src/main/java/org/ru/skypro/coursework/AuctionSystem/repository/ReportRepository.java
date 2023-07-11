package org.ru.skypro.coursework.AuctionSystem.repository;

import org.ru.skypro.coursework.AuctionSystem.model.Report;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ReportRepository extends CrudRepository <Report, Integer>,
        PagingAndSortingRepository<Report, Integer> {

    @Query(value = "SELECT * FROM report",
            nativeQuery = true)
    List<Report> findAllReport();

    @Query(value = "SELECT MAX(report_id) FROM report",
            nativeQuery = true)
    Long findLastReportId ();

    @Query(value = "SELECT file_path FROM report " +
            "WHERE report_id= :reportId",
            nativeQuery = true)
    String findFilePathByReportId(Long reportId);

}
