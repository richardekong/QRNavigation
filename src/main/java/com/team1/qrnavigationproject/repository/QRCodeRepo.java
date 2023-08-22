package com.team1.qrnavigationproject.repository;

import com.team1.qrnavigationproject.model.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QRCodeRepo extends JpaRepository<QRCode, Integer> {
    QRCode findQRCodeById(int id);

    QRCode findQRCodeByDescription(String description);

    QRCode findQRCodeBySpaceId(int id);

    QRCode findQRCodeBySubSpaceId(int id);

    QRCode findQRCodeByCreatedAt(LocalDateTime dateTime);

    @Query("SELECT CASE WHEN COUNT(q) > 0 THEN true ELSE false END FROM QRCode q WHERE q.space.id = :spaceId AND q.subSpace.id = :subSpaceId")
    boolean existsQRCodeBySpaceIdAndSubSpaceId(@Param("spaceId") int spaceId,@Param("subSpaceId") int subSpaceId);

    @Query("SELECT q FROM QRCode q")
    List<QRCode> findAllQRCodes();

    @Query("SELECT q FROM QRCode q where  q.space.id = :spaceId and q.subSpace.id = :subspaceId")
    Optional<QRCode> findQRCodeBySpaceIdAndSubspaceId(@Param("spaceId") int spaceId, @Param("subspaceId") int subspaceId);

    @Query("SELECT q FROM QRCode q left join Space s on q.space.id=s.id WHERE s.organization.id = :organizationId")
    Optional<List<QRCode>> findQRCodesByOrganizationId(@Param("organizationId") int organizationId);
    @Query("SELECT s.name AS space,sb.name as subspace, " +
            "q.description as description FROM QRCode" +
            " q LEFT JOIN Space s ON q.space.id=s.id " +
            "LEFT JOIN SubSpace sb ON s.id=sb.space.id " +
            "WHERE s.organization.id = :organizationId" +
            " AND sb.name IS NOT NULL")
    Optional<List<Object[]>> findQRCodeRecordsHavingSpaceNameSubspaceNameAndDescriptionByOrganizationId(
            @Param("organizationId") int organizationId
    );


}
