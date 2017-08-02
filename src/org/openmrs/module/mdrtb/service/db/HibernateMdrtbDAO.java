package org.openmrs.module.mdrtb.service.db;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openmrs.Location;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.db.DAOException;

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
    
    public void savePDF(Integer oblast, Integer location, Integer year, Integer quarter, Integer month, String reportDate, byte[] tableData) {
    	Session session = sessionFactory.openSession();
		session.beginTransaction();
		String sql = "INSERT INTO report_data (oblast_id, location_id, year, quarter, month, report_date, table_data) VALUES ('"+oblast+"', '"+location+"', "+year+", "+quarter+", "+month+", '"+reportDate+"', '"+tableData+"');";
		session.createSQLQuery(sql).executeUpdate();//has no effect. Query doesn't execute.
		session.getTransaction().commit();
		session.close();
	}
    
    public void readPDF(Integer oblast, Integer location, Integer year, Integer quarter, Integer month, String reportDate, byte[] tableData) {
    	Session session = sessionFactory.openSession();
		session.beginTransaction();
		String sql = "INSERT INTO report (oblast_id, location_id, year, quarter, month, report_date, table_data) VALUES ('"+oblast+"', '"+location+"', "+year+", "+quarter+", "+month+", '"+reportDate+"', '"+tableData+"');";
		session.createSQLQuery(sql).executeUpdate();//has no effect. Query doesn't execute.
		session.getTransaction().commit();
		session.close();
	}
    
}
