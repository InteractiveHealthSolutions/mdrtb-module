package org.openmrs.module.mdrtb.service.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.mdrtb.reporting.PDFHelper;

public class HibernateMdrtbDAO implements MdrtbDAO {

    protected static final Log log = LogFactory.getLog(HibernateMdrtbDAO.class);
    
    /**
     * Hibernate session factory
     */
    private SessionFactory sessionFactory;
    
    
    public void setSessionFactory(SessionFactory sessionFactory) { 
        this.sessionFactory = sessionFactory;
    }
         
    /**
	 * @see MdrtbDAO#getLocationsWithAnyProgramEnrollments()
	 */
    @SuppressWarnings("unchecked")
	public List<Location> getLocationsWithAnyProgramEnrollments() throws DAOException {
		String query = "select distinct location from PatientProgram where voided = false";
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}
    
    /**
	 * @see MdrtbDAO#getAllRayonsTJK()
	 */
    @SuppressWarnings("unchecked")
	public List<String> getAllRayonsTJK() throws DAOException {
		String query = "select distinct name from address_hierarchy_entry where level_id=3";
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}
    
    
    public PatientIdentifier getPatientIdentifierById(Integer patientIdentifierId) {
		return (PatientIdentifier) sessionFactory.getCurrentSession().createQuery(
		    "from PatientIdentifier p where patientIdentifierId = :pid").setInteger("pid", patientIdentifierId.intValue()).uniqueResult();
	}
    
   /* public void savePDF(Integer oblast, String location, Integer year, Integer quarter, Integer month, String reportDate, String tableData, boolean reportStatus, String reportName, String reportType) {
		Integer status = 0; 
		if(reportStatus == true) { 
			status = 1; 
		}
    	String sql = "INSERT INTO report_data (oblast_id, location_id, year, quarter, month, report_date, table_data, report_status, report_name, report_type) VALUES ("+oblast+", "+location+", "+year+", "+quarter+", "+month+", '"+reportDate+"', '"+tableData+"', "+status+", '"+reportName+"', '" + reportType + "')";
    	System.out.println(sql);
    	Session session = sessionFactory.getCurrentSession();
    	session.beginTransaction();
		session.createSQLQuery(sql).executeUpdate();
		session.getTransaction().commit();
	}*/
    
    public void doPDF(Integer oblast, Integer district, Integer facility, Integer year, String quarter, String month, String reportDate, String tableData, boolean reportStatus, String reportName, String reportType) {
    	try{
    	System.out.println("SavePDF-PARAMS:"+oblast +":" + district + ":" + facility +":" + year + ":" + reportDate + ":" + tableData + ":" +  reportName + ":" + reportType);
    	
    	Integer status = 0; 
		if(reportStatus == true) { 
			status = 1; 
		}
    	String sql = "INSERT INTO report_data (oblast_id, district_id, facility_id,year, quarter, month, report_date, table_data, report_status, report_name, report_type) VALUES ("+oblast+", "+district+", "+ facility+", " +year+", '"+ quarter + "', '" + month + "', '"    +reportDate+"', '"+tableData+"', "+status+", '"+reportName+"', '" + reportType + "')";
    	System.out.println(sql);
    	Session session = sessionFactory.getCurrentSession();
    	session.beginTransaction();
		session.createSQLQuery(sql).executeUpdate();
		session.getTransaction().commit();
    	}
    	catch (Exception e) {
    		System.out.println("caught in dao: " + e.getMessage());
    		e.printStackTrace();
    	}
	}
    
    /*public void savePDF(Integer oblast, String location, Integer year, Integer quarter, Integer month, String reportDate, String tableData, boolean reportStatus, String reportName) {
		Integer status = 0; 
		if(reportStatus == true) { 
			status = 1; 
		}
    	String sql = "INSERT INTO report_data (oblast_id, location_id, year, quarter, month, report_date, table_data, report_status, report_name) VALUES ("+oblast+", "+location+", "+year+", "+quarter+", "+month+", '"+reportDate+"', '"+tableData+"', "+status+", '"+reportName+"'";
    	System.out.println(sql);
    	Session session = sessionFactory.getCurrentSession();
    	session.beginTransaction();
		session.createSQLQuery(sql).executeUpdate();
		session.getTransaction().commit();
	}*/
   
	/*@SuppressWarnings("unchecked")
	public int countPDFRows() {
    	Session session = sessionFactory.getCurrentSession(); 
    	session.beginTransaction();
    	List<String> list = (List<String>) session.createSQLQuery("select count(*) from report_data").list();
    	return list.size();
	}*/
    @SuppressWarnings("unchecked")
	public int countPDFRows() {
    	Session session = sessionFactory.getCurrentSession(); 
    	session.beginTransaction();
    	List<String> list = (List<String>) session.createSQLQuery("select count(*) from report_data where report_type = 'MDRTB'").list();
    	return list.size();
	}
    

	public int countPDFColumns() {
		return PDFColumns().size();
	}

	public ArrayList<String> PDFColumns() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("report_id");
		list.add("oblast_id");
		list.add("district_id");
		list.add("facility_id");
		list.add("year");
		list.add("quarter");
		list.add("month");
		list.add("report_date");
		//list.add("table_data");
		list.add("report_status");
		list.add("report_name");
		
    	return list;
	}
	
	/*@SuppressWarnings({"unchecked"})
	public List<List<Integer>> PDFRows() {
		Session session = sessionFactory.getCurrentSession(); 
    	session.beginTransaction();
    	List<List<Integer>> list = new ArrayList<List<Integer>>();
		list.add((List<Integer>) session.createSQLQuery("select report_id from report_data").list());
		list.add((List<Integer>) session.createSQLQuery("select oblast_id from report_data").list());
		list.add((List<Integer>) session.createSQLQuery("select location_id from report_data").list());
		list.add((List<Integer>) session.createSQLQuery("select year from report_data").list());
		list.add((List<Integer>) session.createSQLQuery("select quarter from report_data").list());
		list.add((List<Integer>) session.createSQLQuery("select month from report_data").list());
		list.add((List<Integer>) session.createSQLQuery("select report_date from report_data").list());
		list.add((List<Integer>) session.createSQLQuery("select report_status from report_data").list());
		list.add((List<Integer>) session.createSQLQuery("select report_name from report_data").list());
    	return list;
	}*/
	
	@SuppressWarnings({"unchecked"})
	public List<List<Integer>> PDFRows(String reportType) {
		Session session = sessionFactory.getCurrentSession(); 
    	session.beginTransaction();
    	List<List<Integer>> list = new ArrayList<List<Integer>>();
    	list.add((List<Integer>) session.createSQLQuery("select report_id from report_data where report_type = '" + reportType + "'").list());
		list.add((List<Integer>) session.createSQLQuery("select oblast_id from report_data where report_type = '" + reportType + "'").list());
		list.add((List<Integer>) session.createSQLQuery("select district_id from report_data where report_type = '" + reportType + "'").list());
		list.add((List<Integer>) session.createSQLQuery("select facility_id from report_data where report_type = '" + reportType + "'").list());
		list.add((List<Integer>) session.createSQLQuery("select year from report_data where report_type = '" + reportType + "'").list());
		list.add((List<Integer>) session.createSQLQuery("select quarter from report_data where report_type = '" + reportType + "'").list());
		list.add((List<Integer>) session.createSQLQuery("select month from report_data where report_type = '" + reportType + "'").list());
		list.add((List<Integer>) session.createSQLQuery("select report_date from report_data where report_type = '" + reportType + "'").list());
		list.add((List<Integer>) session.createSQLQuery("select report_status from report_data where report_type = '" + reportType + "'").list());
		list.add((List<Integer>) session.createSQLQuery("select report_name from report_data where report_type = '" + reportType + "'").list());
		list.add((List<Integer>) session.createSQLQuery("select report_type from report_data where report_type = '" + reportType + "'").list());
    	return list;
	}
	
	/*@SuppressWarnings({"unchecked"})
	public List<String> readTableData(Integer oblast, Integer location, Integer year, Integer quarter, Integer month, String name, String date, String reportType) {
		String sql = "select table_data from report_data ";
    	if(name != null && !name.equals("")) { 
    		sql += " where report_name='" + name + "'"; 
		} 
    	if(date != null && !date.equals("")) { 
    		sql += " and report_date='" + date + "'"; 
		} 
    	if(oblast != null) { 
    		sql += " and oblast_id=" + oblast; 
		} 
    	if(location != null) { 
    		sql += " and location_id=" + location; 
		} 
    	if(year != null) { 
    		sql += " and year=" + year; 
		} 
    	if(quarter != null) { 
    		sql += " and quarter=" + quarter; 
		} 
    	if(month != null) { 
    		sql += " and month=" + month; 
		}
    	
    	sql += " and report_type= '" + reportType + "'"; 
    	
    	System.out.println(sql);
    	Session session = sessionFactory.getCurrentSession(); 
    	session.beginTransaction();
    	List<String> list = (List<String>) session.createSQLQuery(sql).list();
    	return list;
	}*/
	
	@SuppressWarnings({"unchecked"})
	public List<String> readTableData(Integer oblast, Integer district, Integer facility, Integer year, String quarter, String month, String name, String date, String reportType) {
		String sql = "select table_data from report_data ";
    	if(name != null && !name.equals("")) { 
    		sql += " where report_name='" + name + "'"; 
		} 
    	if(date != null && !date.equals("")) { 
    		sql += " and report_date='" + date + "'"; 
		} 
    	if(oblast != null) { 
    		sql += " and oblast_id=" + oblast; 
		} 
    	
    	else {
    		sql += " and oblast_id IS NULL";
    	}
    	if(district != null) { 
    		sql += " and district_id=" + district; 
		} 
    	
    	else {
    		sql += " and district_id IS NULL";
    	}
    	
    	if(facility != null) { 
    		sql += " and facility_id=" + facility; 
		} 
    	
    	else {
    		sql += " and facility_id IS NULL";
    	}
    	
    	if(year != null) { 
    		sql += " and year=" + year; 
		} 
    	
    	else {
    		sql += "and year IS NULL";
    	}
    	if(quarter != null && !quarter.equals(Context.getMessageSourceService().getMessage("mdrtb.annual"))) { 
    		sql += " and quarter='" + quarter + "'"; 
		} 
    	
    	else {
    		sql += " and quarter IS NULL";
    	}
    	if(month != null) { 
    		sql += " and month='" + month + "'"; 
		}
    	
    	else {
    		sql += " and month IS NULL";
    	}
    	
    	sql += " and report_type= '" + reportType + "'"; 
    	
    	System.out.println(sql);
    	Session session = sessionFactory.getCurrentSession(); 
    	session.beginTransaction();
    	List<String> list = (List<String>) session.createSQLQuery(sql).list();
    	return list;
	}
	
		
	/*public void unlockReport(Integer oblast, Integer location, Integer year, Integer quarter, Integer month, String name, String date) {
    	String sql = "delete from report_data where report_type = 'MDRTB'"; 
    	if(name != null && !name.equals("")) { 
    		sql += " where report_name='" + name + "'"; 
		} 
    	if(date != null && !date.equals("")) { 
    		sql += " and report_date='" + date + "'"; 
		} 
    	if(oblast != null) { 
    		sql += " and oblast_id=" + oblast; 
		} 
    	if(location != null) { 
    		sql += " and location_id=" + location; 
		} 
    	if(year != null) { 
    		sql += " and year=" + year; 
		} 
    	if(quarter != null) { 
    		sql += " and quarter=" + quarter; 
		} 
    	if(month != null) { 
    		sql += " and month=" + month; 
		}
    	System.out.println(sql);
    	Session session = sessionFactory.getCurrentSession(); 
    	session.beginTransaction();
    	session.createSQLQuery(sql).executeUpdate();
		session.getTransaction().commit();
	}*/
	
	public void unlockReport(Integer oblast, Integer district, Integer facility, Integer year, String quarter, String month, String name, String date, String reportType) {
    	String sql = "delete from report_data "; 
    	if(name != null && !name.equals("")) { 
    		sql += " where report_name='" + name + "'"; 
		} 
    	if(date != null && !date.equals("")) { 
    		sql += " and report_date='" + date + "'"; 
		} 
    	if(oblast != null) { 
    		sql += " and oblast_id=" + oblast; 
		} 
    	
    	else {
    		sql += " and oblast_id IS NULL";
    	}
    	if(district != null) { 
    		sql += " and district_id=" + district; 
		} 
    	
    	else {
    		sql += " and district_id IS NULL";
    	}
    	
    	if(facility != null) { 
    		sql += " and facility_id=" + facility; 
		} 
    	
    	else {
    		sql += " and facility_id IS NULL";
    	}
    	if(year != null) { 
    		sql += " and year=" + year; 
		} 
    	
    	else {
    		sql += " and year IS NULL";
    	}
    	if(quarter != null && !quarter.equals(Context.getMessageSourceService().getMessage("mdrtb.annual"))) { 
    		sql += " and quarter='" + quarter + "'"; 
		} 
    	else {
    		sql += " and quarter IS NULL";
    	}
    	if(month != null) { 
    		sql += " and month='" + month + "'"; 
		}
    	
    	else {
    		sql += " and month IS NULL";
    	}
    	
    	sql += " and report_type='" + reportType + "'";
    	
    	System.out.println(sql);
    	Session session = sessionFactory.getCurrentSession(); 
    	session.beginTransaction();
    	session.createSQLQuery(sql).executeUpdate();
		session.getTransaction().commit();
	}
	

	@SuppressWarnings("unchecked")
	public boolean readReportStatus(Integer oblast, Integer district, Integer facility, Integer year, String quarter, String month, String name, String reportType) {
    	String sql = "select report_status from report_data ";
    	if(name != null && !name.equals("")) { 
    		sql += " where report_name='" + name + "'"; 
		} 
    	if(oblast == null) { 
    		sql += " and oblast_id IS NULL"; 
		} 
    	
    	else {
    		sql += " and oblast_id=" + oblast; 
    	}
    	
    	if(district == null) { 
    		sql += " and district_id IS NULL"; 
		} 
    	
    	else { 
    		sql += " and district_id=" + district; 
		} 
    	
    	if(facility == null) { 
    		sql += " and facility_id IS NULL"; 
		} 
    	
    	else {
    		sql += " and facility_id=" + facility; 
		} 
    	
    	if(year == null) { 
    		sql += " and year IS NULL"; 
		} 
    	
    	else { 
    		sql += " and year=" + year; 
		} 
    	
    	if(quarter == null) { 
    		sql += " and quarter IS NULL"; 
		} 
    	else { 
    		sql += " and quarter='" + quarter + "'"; 
		} 
    	
    	if(month == null) { 
    		sql += " and month IS NULL"; 
		} 
    	
    	else { 
    		sql += " and month='" + month + "'"; 
		}
    	
    	sql += " and report_type='" + reportType + "'";
    	
    	System.out.println(sql);
    	Session session = sessionFactory.getCurrentSession(); 
    	session.beginTransaction();
    	List<String> statusList = (List<String>) session.createSQLQuery(sql).list(); 
    	List<String> list = new PDFHelper().byteToStrArray(statusList.toString());
    	boolean reportStatus = false; 
    	if(list.size() > 0) { 
    		if(new PDFHelper().isInt(list.get(0))) { 
    			Integer status = Integer.parseInt(list.get(0)); 
    			if(status == 0) { 
    				reportStatus = false; 
    			} else if(status == 1) { 
    				reportStatus = true; 
    			}
    		}
    	} else { 
    		reportStatus = false; 
    	}
		session.getTransaction().commit(); 
		return reportStatus;
    }
	
	/*@SuppressWarnings("unchecked")
	public boolean readReportStatus(Integer oblast, Integer location, Integer year, Integer quarter, Integer month, String name, String reportType) {
    	String sql = "select report_status from report_data ";
    	if(name != null && !name.equals("")) { 
    		sql += " where report_name='" + name + "'"; 
		} 
    	if(oblast != null) { 
    		sql += " and oblast_id=" + oblast; 
		} 
    	if(location != null) { 
    		sql += " and location_id=" + location; 
		} 
    	if(year != null) { 
    		sql += " and year=" + year; 
		} 
    	if(quarter != null) { 
    		sql += " and quarter=" + quarter; 
		} 
    	if(month != null) { 
    		sql += " and month=" + month; 
		}
    	
    	sql += " and report_type='" + reportType + "'";
    	
    	System.out.println(sql);
    	Session session = sessionFactory.getCurrentSession(); 
    	session.beginTransaction();
    	List<String> statusList = (List<String>) session.createSQLQuery(sql).list(); 
    	List<String> list = new PDFHelper().byteToStrArray(statusList.toString());
    	boolean reportStatus = false; 
    	if(list.size() > 0) { 
    		if(new PDFHelper().isInt(list.get(0))) { 
    			Integer status = Integer.parseInt(list.get(0)); 
    			if(status == 0) { 
    				reportStatus = false; 
    			} else if(status == 1) { 
    				reportStatus = true; 
    			}
    		}
    	} else { 
    		reportStatus = false; 
    	}
		session.getTransaction().commit(); 
		return reportStatus;
    }*/

	public List<Encounter> getEncountersByEncounterTypes(List<String> encounterTypeNames) {
		return getEncountersByEncounterTypes(encounterTypeNames, null, null, null); 
	}
	
	@SuppressWarnings("unchecked")
	public List<Encounter> getEncountersByEncounterTypes(List<String> encounterTypeNames, Date startDate, Date endDate, Date closeDate) {
		SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Integer> encounterIds = new ArrayList<Integer>();
		List<Integer> tempList = new ArrayList<Integer>();
		String sql = "";
		Session session = sessionFactory.getCurrentSession();
    	session.beginTransaction();
		for (String encounterTypeName : encounterTypeNames) {
	    	sql = "select e.encounter_id from encounter e inner join encounter_type et where e.encounter_type=et.encounter_type_id and et.name='" + encounterTypeName + "' and e.voided=0";
			
	    	if(startDate != null && endDate != null) {
				sql += " and e.encounter_datetime between '" + dbDateFormat.format(startDate) + "' and '" + dbDateFormat.format(endDate) + "'";
			}
	    	/*if(closeDate != null) {
				sql += " and (e.date_changed >= '" + dbDateFormat.format(closeDate) + "' or e.date_created >= '" + dbDateFormat.format(closeDate) + "')";
	    	}*/
	    	sql += ";";
	    	System.out.println(sql);
	    	
	    	tempList = (List<Integer>) session.createSQLQuery(sql).list();
			System.out.println("tempList: " + tempList.size());
	    	
			for (Integer encounterId : tempList) {
		    	if (!(encounterIds.contains(encounterId))) {
			    	encounterIds.add(encounterId); 
				}
			}
		}
		System.out.println("encounterIds: " + encounterIds.size());
    	session.getTransaction().commit(); 
    	
    	List<Encounter> encounters = new ArrayList<Encounter>();
		Encounter encounter = new Encounter();
    	for (Integer encounterId : encounterIds) {
			encounter = Context.getEncounterService().getEncounter(encounterId);
			encounters.add(encounter);
		}
		System.out.println("encounters: " + encounters.size());
		return encounters; 
	}
	
	public void evict(Object obj) {
		sessionFactory.getCurrentSession().evict(obj);
	}
    
}
