package org.openmrs.module.mdrtb.service.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.db.DAOException;

public interface MdrtbDAO {

    /**
     * @return all Locations which have non-voided Patient Programs associated with them
     */
    public List<Location> getLocationsWithAnyProgramEnrollments() throws DAOException;
    public List<String> getAllRayonsTJK();
    public PatientIdentifier getPatientIdentifierById(Integer patientIdentifierId);
    
    public int countPDFRows();
    public int countPDFColumns();
    public List<List<Integer>> PDFRows();
    public ArrayList<String> PDFColumns();
    
    public void savePDF(Integer oblast, String location, Integer year, Integer quarter, Integer month, String reportDate, String tableData, boolean reportStatus, String reportName);
    public boolean readReportStatus(Integer oblast, Integer location, Integer year, Integer quarter, Integer month, String name);
    public List<String> readTableData(Integer oblast, Integer location, Integer year, Integer quarter, Integer month, String name, String date);
    public void unlockReport(Integer oblast, Integer location, Integer year, Integer quarter, Integer month, String name, String date);
	public List<Encounter> getEncountersByEncounterTypes(List<String> encounterTypeNames);
	public List<Encounter> getEncountersByEncounterTypes(List<String> encounterTypeNames, Date startDate, Date endDate, Date closeDate);
}
