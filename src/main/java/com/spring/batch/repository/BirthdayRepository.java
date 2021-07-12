package com.spring.batch.repository;

import com.spring.batch.model.Birthday;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BirthdayRepository extends JpaRepository<Birthday, String>{
	
//	//Partner
//	@Query(nativeQuery = true,value = "select * from consent_revised where partner_id =" +
//			" ?1")
//	List<consent> findByPartnerId(String partner_id);
//
//	//Partner
//	@Query(nativeQuery = true,value = "select * from consent_revised where partner_id = ?1 and partner_service = ?2 and granter_id = ?3 and consent_type = ?4")
//	consent findByRequestDetails(String partner_id, String partner_service, String granter_id, String consent_type);
//
//
//	@Query(nativeQuery = true,value = "select * from consent_revised where consent_id = ?1")
//	consent findOne(String consent_id);
//
//	@Query(nativeQuery = true,value = "SELECT * FROM consent_revised where " +
//					"consent_status = ?1 and consent_type = ?2 and granter_id = ?3")
//	List<consent> getAllActiveConsents(String consentStatus,
//															String consentType,
//															String granterId);
	
}
