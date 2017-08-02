package org.openmrs.module.mdrtb.service.db;

import java.util.Date;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.db.DAOException;

public interface MdrtbDAO {

    /**
     * @return all Locations which have non-voided Patient Programs associated with them
     */
    public List<Location> getLocationsWithAnyProgramEnrollments() throws DAOException;
    public List<String> getAllRayonsTJK();
    public PatientIdentifier getPatientIdentifierById(Integer patientIdentifierId);
    public void savePDF(Integer oblast, Integer location, Integer year, Integer quarter, Integer month, String reportDate, byte[] tableData);
}
