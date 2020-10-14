package org.benetech.servicenet.repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import org.benetech.servicenet.domain.Referral;

import org.benetech.servicenet.domain.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Referral entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReferralRepository extends JpaRepository<Referral, UUID> {

    @Query("SELECT referral FROM Referral referral WHERE referral.beneficiary.id = :beneficiaryId AND referral.to.id = :cboId")
    List<Referral> findAllByBeneficiaryIdAndReferredTo(@Param("beneficiaryId") UUID beneficiaryId, @Param("cboId") UUID cboId);

    @Query("SELECT referral FROM Referral referral "
        + "JOIN referral.from org "
        + "JOIN org.userProfiles userProfile "
        + "WHERE userProfile = :fromUser "
        + "AND (COALESCE(:since, NULL) IS NULL OR referral.sentAt > :since) "
        + "AND (NOT :isSent = true OR (referral.sentAt IS NOT NULL AND referral.fulfilledAt IS NULL))"
        + "AND (NOT :isFulfilled = true OR referral.fulfilledAt IS NOT NULL)")
    Page<Referral> findByUserProfileSince(@Param("fromUser") UserProfile fromUser, @Param("since")
        ZonedDateTime since, @Param("isSent") Boolean isSent, @Param("isFulfilled") Boolean isFulfilled, Pageable pageable);
}
