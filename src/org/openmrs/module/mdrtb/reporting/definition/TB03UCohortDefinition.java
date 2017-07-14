package org.openmrs.module.mdrtb.reporting.definition;

import java.util.Date;

import org.openmrs.Location;
import org.openmrs.module.reporting.cohort.definition.BaseCohortDefinition;
import org.openmrs.module.reporting.common.Localized;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;

@Localized("dotsreports.reporting.TB03CohortDefinition")
public class TB03UCohortDefinition extends BaseCohortDefinition {
	
	@ConfigurationProperty
	private Date onOrBefore;
	
	@ConfigurationProperty
	private Date onOrAfter;
	
	@ConfigurationProperty
	private Location location;

	public TB03UCohortDefinition() {
		super();
		
	}

	public Date getOnOrBefore() {
		return onOrBefore;
	}

	public void setOnOrBefore(Date onOrBefore) {
		this.onOrBefore = onOrBefore;
	}

	public Date getOnOrAfter() {
		return onOrAfter;
	}

	public void setOnOrAfter(Date onOrAfter) {
		this.onOrAfter = onOrAfter;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	

	
	
	
}
