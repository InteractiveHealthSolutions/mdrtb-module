package org.openmrs.module.mdrtb.web.controller.reporting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.openmrs.Cohort;
import org.openmrs.Concept;
import org.openmrs.Drug;
import org.openmrs.DrugOrder;
import org.openmrs.Encounter;
import org.openmrs.Form;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.Person;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.form.RegimenForm;
import org.openmrs.module.mdrtb.form.TB03Form;
import org.openmrs.module.mdrtb.form.TB03uForm;
import org.openmrs.module.mdrtb.reporting.data.Cohorts;
import org.openmrs.module.mdrtb.District;
import org.openmrs.module.mdrtb.Facility;
import org.openmrs.module.mdrtb.MdrtbConceptMap;
import org.openmrs.module.mdrtb.MdrtbConstants;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.reporting.PDFHelper;
import org.openmrs.module.mdrtb.reporting.ReportUtil;
import org.openmrs.module.mdrtb.reporting.TB03uData;
import org.openmrs.module.mdrtb.reporting.TB03uUtil;
import org.openmrs.module.mdrtb.reporting.TB07uData;
import org.openmrs.module.mdrtb.reporting.TB08uData;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.specimen.Culture;
import org.openmrs.module.mdrtb.specimen.Dst;
import org.openmrs.module.mdrtb.specimen.DstResult;
import org.openmrs.module.mdrtb.specimen.HAIN;
import org.openmrs.module.mdrtb.specimen.Smear;
import org.openmrs.module.mdrtb.specimen.Xpert;
/*import org.openmrs.module.mdrtbdrugforecast.DrugCount;
 import org.openmrs.module.mdrtbdrugforecast.MdrtbDrugStock;
 import org.openmrs.module.mdrtbdrugforecast.MdrtbUtil;
 import org.openmrs.module.mdrtbdrugforecast.MdrtbConcepts;
 import org.openmrs.module.mdrtbdrugforecast.drugneeds.DrugForecastUtil;
 import org.openmrs.module.mdrtbdrugforecast.program.MdrtbPatientProgram;
 import org.openmrs.module.mdrtbdrugforecast.regimen.Regimen;
 import org.openmrs.module.mdrtbdrugforecast.regimen.RegimenUtils;
 import org.openmrs.module.mdrtbdrugforecast.reporting.definition.MdrtbDrugForecastTreatmentStartedCohortDefinition;
 import org.openmrs.module.mdrtbdrugforecast.reporting.definition.MdrtbDrugForecastTreatmentStartedOnDrugCohortDefinition;
 import org.openmrs.module.mdrtbdrugforecast.service.MdrtbDrugForecastService;
 import org.openmrs.module.mdrtbdrugforecast.status.TreatmentStatusCalculator;
 import org.openmrs.module.mdrtbdrugforecast.web.controller.status.DashboardTreatmentStatusRenderer;*/
import org.openmrs.module.reporting.cohort.EvaluatedCohort;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.service.CohortDefinitionService;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.propertyeditor.ConceptEditor;
import org.openmrs.propertyeditor.LocationEditor;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TB07uController {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class,
				new CustomDateEditor(Context.getDateFormat(), true, 10));
		binder.registerCustomEditor(Concept.class, new ConceptEditor());
		binder.registerCustomEditor(Location.class, new LocationEditor());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/module/mdrtb/reporting/tb07u")
	public ModelAndView showRegimenOptions(
			@RequestParam(value = "loc", required = false) String district,
			@RequestParam(value = "ob", required = false) String oblast,
			@RequestParam(value = "yearSelected", required = false) Integer year,
			@RequestParam(value = "quarterSelected", required = false) String quarter,
			@RequestParam(value = "monthSelected", required = false) String month,
			ModelMap model) {

		List<Oblast> oblasts;
		List<Facility> facilities;
		List<District> districts;

		if (oblast == null) {
			oblasts = Context.getService(MdrtbService.class).getOblasts();
			model.addAttribute("oblasts", oblasts);
		}

		else if (district == null) {
			// DUSHANBE
			if (Integer.parseInt(oblast) == 186) {
				oblasts = Context.getService(MdrtbService.class).getOblasts();
				districts = Context.getService(MdrtbService.class)
						.getDistricts(Integer.parseInt(oblast));
				District d = districts.get(0);
				facilities = Context.getService(MdrtbService.class)
						.getFacilities(d.getId());
				model.addAttribute("oblastSelected", oblast);
				model.addAttribute("oblasts", oblasts);
				model.addAttribute("districts", districts);
				model.addAttribute("facilities", facilities);
				model.addAttribute("dushanbe", 186);
			}

			else {

				oblasts = Context.getService(MdrtbService.class).getOblasts();
				districts = Context.getService(MdrtbService.class)
						.getDistricts(Integer.parseInt(oblast));
				model.addAttribute("oblastSelected", oblast);
				model.addAttribute("oblasts", oblasts);
				model.addAttribute("districts", districts);
			}
		}
		
		else {
			
			/*
			 * if oblast is dushanbe, return both districts and facilities
			 */
			if (Integer.parseInt(oblast) == 186) {
				oblasts = Context.getService(MdrtbService.class).getOblasts();
				districts = Context.getService(MdrtbService.class)
						.getDistricts(Integer.parseInt(oblast));
				District d = districts.get(0);
				facilities = Context.getService(MdrtbService.class)
						.getFacilities(d.getId());
				model.addAttribute("oblastSelected", oblast);
				model.addAttribute("oblasts", oblasts);
				model.addAttribute("districts", districts);
				model.addAttribute("facilities", facilities);
				model.addAttribute("dushanbe", 186);
			}

			else {
				oblasts = Context.getService(MdrtbService.class).getOblasts();
				districts = Context.getService(MdrtbService.class)
						.getDistricts(Integer.parseInt(oblast));
				facilities = Context.getService(MdrtbService.class)
						.getFacilities(Integer.parseInt(district));
				model.addAttribute("oblastSelected", oblast);
				model.addAttribute("oblasts", oblasts);
				model.addAttribute("districts", districts);
				model.addAttribute("districtSelected", district);
				model.addAttribute("facilities", facilities);
			}
		}

		model.addAttribute("yearSelected", year);
		model.addAttribute("monthSelected", month);
		model.addAttribute("quarterSelected", quarter);

		/*
		 * List<Location> locations =
		 * Context.getLocationService().getAllLocations(false);//
		 * Context.getLocationService().getAllLocations();//ms =
		 * (MdrtbDrugForecastService)
		 * Context.getService(MdrtbDrugForecastService.class); List<Oblast>
		 * oblasts = Context.getService(MdrtbService.class).getOblasts();
		 * //drugSets = ms.getMdrtbDrugs();
		 * 
		 * 
		 * 
		 * model.addAttribute("locations", locations);
		 * model.addAttribute("oblasts", oblasts);
		 */
		return new ModelAndView("/module/mdrtb/reporting/tb07u", model);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/module/mdrtb/reporting/tb07u")
	public static String doTB08(@RequestParam("district") Integer districtId,
			@RequestParam("facility") Integer facilityId,
			@RequestParam("oblast") Integer oblastId,
			@RequestParam(value = "year", required = true) Integer year,
			@RequestParam(value = "quarter", required = false) String quarter,
			@RequestParam(value = "month", required = false) String month,
			ModelMap model) throws EvaluationException {
		System.out.println("---POST-----");
		// System.out.println("PARAMS:" + location + " " + oblast + " " + year +
		// " " + quarter + " " + month);

		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("dd.MM.yyyy");
		SimpleDateFormat rdateSDF = new SimpleDateFormat();
		rdateSDF.applyPattern("dd.MM.yyyy HH:mm:ss");
		
		ArrayList<Location> locList = null;
		if (oblastId.intValue() == 186) {
			locList = Context.getService(MdrtbService.class)
					.getLocationListForDushanbe(oblastId, districtId,
							facilityId);
		} else {
			locList = Context.getService(MdrtbService.class).getLocationList(
					oblastId, districtId, facilityId);
		}

		ArrayList<TB03uForm> tb03uList = Context.getService(MdrtbService.class)
				.getTB03uFormsFilled(locList, year, quarter, month);
		
		ArrayList<RegimenForm> regList = Context.getService(MdrtbService.class).getRegimenFormsFilled(locList, year, quarter, month);

		TB07uData table1 = new TB07uData();
		Concept q = null;

		Boolean newCase = null;
		Boolean relapse1 = null;
		Boolean relapse2 = null;
		Boolean default1 = null;
		Boolean default2 = null;
		Boolean failure1 = null;
		Boolean failure2 = null;
		Boolean other = null;
		Boolean txStarted = null;

		// Concept regimenType = null;
		/*
		 * Concept shortCnc = null; Concept indivCnc = null; Concept stdCnc =
		 * null;
		 */

		Concept regimen = null;

		int shrt = Context.getService(MdrtbService.class)
				.getConcept(MdrtbConcepts.SHORT_MDR_REGIMEN).getConceptId()
				.intValue();
		
		int standard = Context.getService(MdrtbService.class)
				.getConcept(MdrtbConcepts.STANDARD_MDR_REGIMEN).getConceptId()
				.intValue();

		int indLzd = Context.getService(MdrtbService.class)
				.getConcept(MdrtbConcepts.INDIVIDUAL_WITH_CFZ_LZD)
				.getConceptId().intValue();
		int indBdq = Context.getService(MdrtbService.class)
				.getConcept(MdrtbConcepts.INDIVIDUAL_WITH_BDQ).getConceptId()
				.intValue();
		
		Boolean isShort = null;
		Boolean isStandard = null;
		Boolean isIndLzd = null;
		Boolean isIndBdq = null;

		Boolean hiv = null;

		Concept hivStatus = null;

		Integer ageAtRegistration = 0;

		Concept positiveConcept = Context.getService(MdrtbService.class)
				.getConcept(TbConcepts.POSITIVE);
		int positive = positiveConcept.getConceptId().intValue();
		Concept resistanceType = null;

		int rr = Context.getService(MdrtbService.class)
				.getConcept(MdrtbConcepts.RR_TB).getConceptId().intValue();
		int mdr = Context.getService(MdrtbService.class)
				.getConcept(MdrtbConcepts.MDR_TB).getConceptId().intValue();
		int pdr = Context.getService(MdrtbService.class)
				.getConcept(MdrtbConcepts.PDR_TB).getConceptId().intValue();
		int preXdr = Context.getService(MdrtbService.class)
				.getConcept(MdrtbConcepts.PRE_XDR_TB).getConceptId().intValue();
		int xdr = Context.getService(MdrtbService.class)
				.getConcept(MdrtbConcepts.XDR_TB).getConceptId().intValue();
		int age = 0;
		
		RegimenForm rf = null;
		

		for (TB03uForm tf : tb03uList) {

			newCase = null;
			relapse1 = null;
			relapse2 = null;
			default1 = null;
			default2 = null;
			failure1 = null;
			failure2 = null;
			other = null;
			regimen = null;
			txStarted = null;
			age = 0;
			
			isShort = null;
			isStandard = null;
			isIndLzd = null;
			isIndBdq = null;
			
			
			rf = null;

			/*
			 * patientList.clear(); conceptQuestionList.clear();
			 * System.out.println("PATIENT ID " + i);
			 */

			Patient patient = tf.getPatient();
			if (patient == null || patient.isVoided()) {
				continue;
			}

			// patientList.add(patient);

			// DATE OF MDR TREATMENT START
			Date txStartDate = tf.getMdrTreatmentStartDate();// Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_TREATMENT_START_DATE);
			/*
			 * conceptQuestionList.clear(); conceptQuestionList.add(q);
			 * 
			 * obsList = Context.getObsService().getObservations(patientList,
			 * null, conceptQuestionList, null, null, null, null, null, null,
			 * startDate, endDate, false); if(obsList.size()>0 &&
			 * obsList.get(0)!=null)
			 */
			if (txStartDate != null) {
				txStarted = Boolean.TRUE;
			}

			else {
				txStarted = Boolean.FALSE;
			}

			ageAtRegistration = tf.getAgeAtMDRRegistration();
			if (ageAtRegistration != null)
				age = ageAtRegistration.intValue();

			else
				age = 999;

			hivStatus = tf.getHivStatus();
			if (hivStatus != null
					&& hivStatus.getConceptId().intValue() == positive) {
				hiv = Boolean.TRUE;
			}

			else {
				hiv = Boolean.FALSE;
			}

			q = tf.getRegistrationGroup();

			if (q == null)
				continue;

			int groupId = q.getConceptId().intValue();

			if (groupId == Integer.parseInt(Context.getAdministrationService()
					.getGlobalProperty("mdrtb.new.conceptId"))) {
				newCase = Boolean.TRUE;
				System.out.println("NEW");
			}

			else if (groupId == Integer.parseInt(Context
					.getAdministrationService().getGlobalProperty(
							"mdrtb.afterRelapse1.conceptId"))) {
				relapse1 = Boolean.TRUE;
				System.out.println("R1");
			}

			else if (groupId == Integer.parseInt(Context
					.getAdministrationService().getGlobalProperty(
							"mdrtb.afterRelapse2.conceptId"))) {
				relapse2 = Boolean.TRUE;
				System.out.println("R2");
			}

			else if (groupId == Integer.parseInt(Context
					.getAdministrationService().getGlobalProperty(
							"mdrtb.afterDefault1.conceptId"))) {
				default1 = Boolean.TRUE;
				System.out.println("D1");
			}

			else if (groupId == Integer.parseInt(Context
					.getAdministrationService().getGlobalProperty(
							"mdrtb.afterDefault2.conceptId"))) {
				default2 = Boolean.TRUE;
				System.out.println("D2");
			}

			else if (groupId == Integer.parseInt(Context
					.getAdministrationService().getGlobalProperty(
							"mdrtb.afterFailure1.conceptId"))) {
				failure1 = Boolean.TRUE;
				System.out.println("F1");
			}

			else if (groupId == Integer.parseInt(Context
					.getAdministrationService().getGlobalProperty(
							"mdrtb.afterFailure2.conceptId"))) {
				failure2 = Boolean.TRUE;
				System.out.println("F2");
			}

			else {
				other = Boolean.TRUE;
				System.out.println("O");
			}

			q = tf.getResistanceType();

			if (q == null)
				continue;

			if (q.getConceptId().intValue() == pdr) {
				table1.setPdrDetections(table1.getPdrDetections() + 1);
				table1.setTotalPdr(table1.getTotalPdr() + 1);
				table1.setTotalDetections(table1.getTotalDetections() + 1);
				
				if (newCase != null && newCase) {

					table1.setNewPdr(table1.getNewPdr() + 1);
					table1.setNewTotal(table1.getNewTotal() + 1);
					

					if (age > 0 && age <= 4) {
						table1.setNewPdr04(table1.getNewPdr04() + 1);
						table1.setTotalPdr04(table1.getTotalPdr04() + 1);
						table1.setNewTotal04(table1.getNewTotal04() + 1);

					}

					else if (age >= 5 && age <= 14) {
						table1.setNewPdr0514(table1.getNewPdr0514() + 1);
						table1.setTotalPdr0514(table1.getTotalPdr0514() + 1);
						table1.setNewTotal0514(table1.getNewTotal0514() + 1);
					}

					else if (age >= 15 && age <= 17) {
						table1.setNewPdr1517(table1.getNewPdr1517() + 1);
						table1.setTotalPdr1517(table1.getTotalPdr1517() + 1);
						table1.setNewTotal1517(table1.getNewTotal1517() + 1);
					}

					if (hiv != null && hiv) {
						table1.setNewPdrHiv(table1.getNewPdrHiv() + 1);
						table1.setTotalPdrHiv(table1.getTotalPdrHiv() + 1);
						table1.setNewTotalHiv(table1.getNewTotalHiv() + 1);
					}
				}

				else if (relapse1 != null && relapse1) {

					table1.setRelapse1Pdr(table1.getRelapse1Pdr() + 1);
					table1.setRelapse1Total(table1.getRelapse1Total() + 1);
					

					if (age > 0 && age <= 4) {
						table1.setRelapse1Pdr04(table1.getRelapse1Pdr04() + 1);
						table1.setTotalPdr04(table1.getTotalPdr04() + 1);
						table1.setRelapse1Total04(table1.getRelapse1Total04() + 1);

					}

					else if (age >= 5 && age <= 14) {
						table1.setRelapse1Pdr0514(table1.getRelapse1Pdr0514() + 1);
						table1.setTotalPdr0514(table1.getTotalPdr0514() + 1);
						table1.setRelapse1Total0514(table1.getRelapse1Total0514() + 1);
					}

					else if (age >= 15 && age <= 17) {
						table1.setRelapse1Pdr1517(table1.getRelapse1Pdr1517() + 1);
						table1.setTotalPdr1517(table1.getTotalPdr1517() + 1);
						table1.setRelapse1Total1517(table1.getRelapse1Total1517() + 1);
					}

					if (hiv != null && hiv) {
						table1.setRelapse1PdrHiv(table1.getRelapse1PdrHiv() + 1);
						table1.setTotalPdrHiv(table1.getTotalPdrHiv() + 1);
						table1.setRelapse1TotalHiv(table1.getRelapse1TotalHiv() + 1);
					}
				}

				else if (relapse2 != null && relapse2) {

					table1.setRelapse2Pdr(table1.getRelapse2Pdr() + 1);
					table1.setRelapse2Total(table1.getRelapse2Total() + 1);

					if (age > 0 && age <= 4) {
						table1.setRelapse2Pdr04(table1.getRelapse2Pdr04() + 1);
						table1.setTotalPdr04(table1.getTotalPdr04() + 1);
						table1.setRelapse2Total04(table1.getRelapse2Total04() + 1);

					}

					else if (age >= 5 && age <= 14) {
						table1.setRelapse2Pdr0514(table1.getRelapse2Pdr0514() + 1);
						table1.setTotalPdr0514(table1.getTotalPdr0514() + 1);
						table1.setRelapse2Total0514(table1.getRelapse2Total0514() + 1);
					}

					else if (age >= 15 && age <= 17) {
						table1.setRelapse2Pdr1517(table1.getRelapse2Pdr1517() + 1);
						table1.setTotalPdr1517(table1.getTotalPdr1517() + 1);
						table1.setRelapse2Total1517(table1.getRelapse2Total1517() + 1);
					}

					if (hiv != null && hiv) {
						table1.setRelapse2PdrHiv(table1.getRelapse2PdrHiv() + 1);
						table1.setTotalPdrHiv(table1.getTotalPdrHiv() + 1);
						table1.setRelapse2TotalHiv(table1.getRelapse2TotalHiv() + 1);
					}
				}

				else if (default1 != null && default1) {

					table1.setDefault1Pdr(table1.getDefault1Pdr() + 1);
					table1.setDefault1Total(table1.getDefault1Total() + 1);

					if (age > 0 && age <= 4) {
						table1.setDefault1Pdr04(table1.getDefault1Pdr04() + 1);
						table1.setTotalPdr04(table1.getTotalPdr04() + 1);
						table1.setDefault1Total04(table1.getDefault1Total04() + 1);


					}

					else if (age >= 5 && age <= 14) {
						table1.setDefault1Pdr0514(table1.getDefault1Pdr0514() + 1);
						table1.setTotalPdr0514(table1.getTotalPdr0514() + 1);
						table1.setDefault1Total0514(table1.getDefault1Total0514() + 1);

					}

					else if (age >= 15 && age <= 17) {
						table1.setDefault1Pdr1517(table1.getDefault1Pdr1517() + 1);
						table1.setTotalPdr1517(table1.getTotalPdr1517() + 1);
						table1.setDefault1Total1517(table1.getDefault1Total1517() + 1);

					}

					if (hiv != null && hiv) {
						table1.setDefault1PdrHiv(table1.getDefault1PdrHiv() + 1);
						table1.setTotalPdrHiv(table1.getTotalPdrHiv() + 1);
						table1.setDefault1TotalHiv(table1.getDefault1TotalHiv() + 1);

					}
				}

				else if (default2 != null && default2) {

					table1.setDefault2Pdr(table1.getDefault2Pdr() + 1);
					table1.setDefault2Total(table1.getDefault2Total() + 1);


					if (age > 0 && age <= 4) {
						table1.setDefault2Pdr04(table1.getDefault2Pdr04() + 1);
						table1.setTotalPdr04(table1.getTotalPdr04() + 1);
						table1.setDefault2Total04(table1.getDefault2Total04() + 1);

						

					}

					else if (age >= 5 && age <= 14) {
						table1.setDefault2Pdr0514(table1.getDefault2Pdr0514() + 1);
						table1.setTotalPdr0514(table1.getTotalPdr0514() + 1);
						table1.setDefault2Total0514(table1.getDefault2Total0514() + 1);

					}

					else if (age >= 15 && age <= 17) {
						table1.setDefault2Pdr1517(table1.getDefault2Pdr1517() + 1);
						table1.setTotalPdr1517(table1.getTotalPdr1517() + 1);
						table1.setDefault2Total1517(table1.getDefault2Total1517() + 1);

					}

					if (hiv != null && hiv) {
						table1.setDefault2PdrHiv(table1.getDefault2PdrHiv() + 1);
						table1.setTotalPdrHiv(table1.getTotalPdrHiv() + 1);
						table1.setDefault2TotalHiv(table1.getDefault2TotalHiv() + 1);

					}
				}
				
				else if (failure1 != null && failure1) {

					table1.setFailure1Pdr(table1.getFailure1Pdr() + 1);
					table1.setFailure1Total(table1.getFailure1Total() + 1);


					if (age > 0 && age <= 4) {
						table1.setFailure1Pdr04(table1.getFailure1Pdr04() + 1);
						table1.setTotalPdr04(table1.getTotalPdr04() + 1);
						table1.setFailure1Total04(table1.getFailure1Total04() + 1);

					}

					else if (age >= 5 && age <= 14) {
						table1.setFailure1Pdr0514(table1.getFailure1Pdr0514() + 1);
						table1.setTotalPdr0514(table1.getTotalPdr0514() + 1);
						table1.setFailure1Total0514(table1.getFailure1Total0514() + 1);
					}

					else if (age >= 15 && age <= 17) {
						table1.setFailure1Pdr1517(table1.getFailure1Pdr1517() + 1);
						table1.setTotalPdr1517(table1.getTotalPdr1517() + 1);
						table1.setFailure1Total1517(table1.getFailure1Total1517() + 1);
					}

					if (hiv != null && hiv) {
						table1.setFailure1PdrHiv(table1.getFailure1PdrHiv() + 1);
						table1.setTotalPdrHiv(table1.getTotalPdrHiv() + 1);
						table1.setFailure1TotalHiv(table1.getFailure1TotalHiv() + 1);
					}
				}

				else if (failure2 != null && failure2) {

					table1.setFailure2Pdr(table1.getFailure2Pdr() + 1);
					table1.setFailure2Total(table1.getFailure2Total() + 1);

					if (age > 0 && age <= 4) {
						table1.setFailure2Pdr04(table1.getFailure2Pdr04() + 1);
						table1.setTotalPdr04(table1.getTotalPdr04() + 1);
						table1.setFailure2Total04(table1.getFailure2Total04() + 1);

					}

					else if (age >= 5 && age <= 14) {
						table1.setFailure2Pdr0514(table1.getFailure2Pdr0514() + 1);
						table1.setTotalPdr0514(table1.getTotalPdr0514() + 1);
						table1.setFailure2Total0514(table1.getFailure2Total0514() + 1);
					}

					else if (age >= 15 && age <= 17) {
						table1.setFailure2Pdr1517(table1.getFailure2Pdr1517() + 1);
						table1.setTotalPdr1517(table1.getTotalPdr1517() + 1);
						table1.setFailure2Total1517(table1.getFailure2Total1517() + 1);
					}

					if (hiv != null && hiv) {
						table1.setFailure2PdrHiv(table1.getFailure2PdrHiv() + 1);
						table1.setTotalPdrHiv(table1.getTotalPdrHiv() + 1);
						table1.setFailure2TotalHiv(table1.getFailure2TotalHiv() + 1);
					}
				}
				
				else if (other != null && other) {

					table1.setOtherPdr(table1.getOtherPdr() + 1);
					table1.setOtherTotal(table1.getOtherTotal() + 1);

					if (age > 0 && age <= 4) {
						table1.setOtherPdr04(table1.getOtherPdr04() + 1);
						table1.setTotalPdr04(table1.getTotalPdr04() + 1);
						table1.setOtherTotal04(table1.getOtherTotal04() + 1);

					}

					else if (age >= 5 && age <= 14) {
						table1.setOtherPdr0514(table1.getOtherPdr0514() + 1);
						table1.setTotalPdr0514(table1.getTotalPdr0514() + 1);
						table1.setOtherTotal0514(table1.getOtherTotal0514() + 1);
					}

					else if (age >= 15 && age <= 17) {
						table1.setOtherPdr1517(table1.getOtherPdr1517() + 1);
						table1.setTotalPdr1517(table1.getTotalPdr1517() + 1);
						table1.setOtherTotal1517(table1.getOtherTotal1517() + 1);
					}

					if (hiv != null && hiv) {
						table1.setOtherPdrHiv(table1.getOtherPdrHiv() + 1);
						table1.setTotalPdrHiv(table1.getTotalPdrHiv() + 1);
						table1.setOtherTotalHiv(table1.getOtherTotalHiv() + 1);
					}
				}
			}

			else if (q.getConceptId().intValue() == rr
					|| q.getConceptId().intValue() == mdr) {
				table1.setMdrDetections(table1.getMdrDetections() + 1);
				table1.setTotalMdr(table1.getTotalMdr() + 1);
				table1.setTotalDetections(table1.getTotalDetections() + 1);
				
				if(regList!=null) {
					rf = getLatestRegimenForPatient(tf.getPatient().getPatientId().intValue(),regList, locList, year, quarter, month);
				}
				
				if(rf!=null) {
					regimen = rf.getSldRegimenType();
					if(regimen!=null) {
						if(regimen.getConceptId().intValue()==shrt) {
							isShort = Boolean.TRUE;
						}
						
						else if(regimen.getConceptId().intValue()==standard) {
							isStandard = Boolean.TRUE;
						}
					}
				}
				
				if (newCase != null && newCase) {

					table1.setNewMdr(table1.getNewMdr() + 1);
					table1.setNewTotal(table1.getNewTotal() + 1);
					

					if (age > 0 && age <= 4) {
						table1.setNewMdr04(table1.getNewMdr04() + 1);
						table1.setTotalMdr04(table1.getTotalMdr04() + 1);
						table1.setNewTotal04(table1.getNewTotal04() + 1);
						
						if(isShort!=null && isShort) {
							table1.setNewShortMdr(table1.getNewShortMdr() + 1);
							table1.setNewShortMdr04(table1.getNewShortMdr04() + 1);
							table1.setTotalShortMdr04(table1.getTotalShortMdr04() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setNewStandardMdr(table1.getNewStandardMdr() + 1);
							table1.setNewStandardMdr04(table1.getNewStandardMdr04() + 1);
							table1.setTotalStandardMdr04(table1.getTotalStandardMdr04() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}

					}

					else if (age >= 5 && age <= 14) {
						table1.setNewMdr0514(table1.getNewMdr0514() + 1);
						table1.setTotalMdr0514(table1.getTotalMdr0514() + 1);
						table1.setNewTotal0514(table1.getNewTotal0514() + 1);
						
						if(isShort!=null && isShort) {
							table1.setNewShortMdr(table1.getNewShortMdr() + 1);
							table1.setNewShortMdr0514(table1.getNewShortMdr0514() + 1);
							table1.setTotalShortMdr0514(table1.getTotalShortMdr0514() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setNewStandardMdr(table1.getNewStandardMdr() + 1);
							table1.setNewStandardMdr0514(table1.getNewStandardMdr0514() + 1);
							table1.setTotalStandardMdr0514(table1.getTotalStandardMdr0514() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}
					}

					else if (age >= 15 && age <= 17) {
						table1.setNewMdr1517(table1.getNewMdr1517() + 1);
						table1.setTotalMdr1517(table1.getTotalMdr1517() + 1);
						table1.setNewTotal1517(table1.getNewTotal1517() + 1);
						
						if(isShort!=null && isShort) {
							table1.setNewShortMdr(table1.getNewShortMdr() + 1);
							table1.setNewShortMdr1517(table1.getNewShortMdr1517() + 1);
							table1.setTotalShortMdr1517(table1.getTotalShortMdr1517() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setNewStandardMdr(table1.getNewStandardMdr() + 1);
							table1.setNewStandardMdr1517(table1.getNewStandardMdr1517() + 1);
							table1.setTotalStandardMdr1517(table1.getTotalStandardMdr1517() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}
					}

					if (hiv != null && hiv) {
						table1.setNewMdrHiv(table1.getNewMdrHiv() + 1);
						table1.setTotalMdrHiv(table1.getTotalMdrHiv() + 1);
						table1.setNewTotalHiv(table1.getNewTotalHiv() + 1);
						
						if(isShort!=null && isShort) {
							table1.setNewShortMdr(table1.getNewShortMdr() + 1);
							table1.setNewShortMdrHiv(table1.getNewShortMdrHiv() + 1);
							table1.setTotalShortMdrHiv(table1.getTotalShortMdrHiv() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setNewStandardMdr(table1.getNewStandardMdr() + 1);
							table1.setNewStandardMdrHiv(table1.getNewStandardMdrHiv() + 1);
							table1.setTotalStandardMdrHiv(table1.getTotalStandardMdrHiv() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}
					}
				}

				else if (relapse1 != null && relapse1) {

					table1.setRelapse1Mdr(table1.getRelapse1Mdr() + 1);
					table1.setRelapse1Total(table1.getRelapse1Total() + 1);
					

					if (age > 0 && age <= 4) {
						table1.setRelapse1Mdr04(table1.getRelapse1Mdr04() + 1);
						table1.setTotalMdr04(table1.getTotalMdr04() + 1);
						table1.setRelapse1Total04(table1.getRelapse1Total04() + 1);
						
						if(isShort!=null && isShort) {
							table1.setRelapse1ShortMdr(table1.getRelapse1ShortMdr() + 1);
							table1.setRelapse1ShortMdr04(table1.getRelapse1ShortMdr04() + 1);
							table1.setTotalShortMdr04(table1.getTotalShortMdr04() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setRelapse1StandardMdr(table1.getRelapse1StandardMdr() + 1);
							table1.setRelapse1StandardMdr04(table1.getRelapse1StandardMdr04() + 1);
							table1.setTotalStandardMdr04(table1.getTotalStandardMdr04() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}

					}

					else if (age >= 5 && age <= 14) {
						table1.setRelapse1Mdr0514(table1.getRelapse1Mdr0514() + 1);
						table1.setTotalMdr0514(table1.getTotalMdr0514() + 1);
						table1.setRelapse1Total0514(table1.getRelapse1Total0514() + 1);
						
						if(isShort!=null && isShort) {
							table1.setRelapse1ShortMdr(table1.getRelapse1ShortMdr() + 1);
							table1.setRelapse1ShortMdr0514(table1.getRelapse1ShortMdr0514() + 1);
							table1.setTotalShortMdr0514(table1.getTotalShortMdr0514() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setRelapse1StandardMdr(table1.getRelapse1StandardMdr() + 1);
							table1.setRelapse1StandardMdr0514(table1.getRelapse1StandardMdr0514() + 1);
							table1.setTotalStandardMdr0514(table1.getTotalStandardMdr0514() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}
					}

					else if (age >= 15 && age <= 17) {
						table1.setRelapse1Mdr1517(table1.getRelapse1Mdr1517() + 1);
						table1.setTotalMdr1517(table1.getTotalMdr1517() + 1);
						table1.setRelapse1Total1517(table1.getRelapse1Total1517() + 1);
						
						if(isShort!=null && isShort) {
							table1.setRelapse1ShortMdr(table1.getRelapse1ShortMdr() + 1);
							table1.setRelapse1ShortMdr1517(table1.getRelapse1ShortMdr1517() + 1);
							table1.setTotalShortMdr1517(table1.getTotalShortMdr1517() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setRelapse1StandardMdr(table1.getRelapse1StandardMdr() + 1);
							table1.setRelapse1StandardMdr1517(table1.getRelapse1StandardMdr1517() + 1);
							table1.setTotalStandardMdr1517(table1.getTotalStandardMdr1517() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}
					}

					if (hiv != null && hiv) {
						table1.setRelapse1MdrHiv(table1.getRelapse1MdrHiv() + 1);
						table1.setTotalMdrHiv(table1.getTotalMdrHiv() + 1);
						table1.setRelapse1TotalHiv(table1.getRelapse1TotalHiv() + 1);
						
						if(isShort!=null && isShort) {
							table1.setRelapse1ShortMdr(table1.getRelapse1ShortMdr() + 1);
							table1.setRelapse1ShortMdrHiv(table1.getRelapse1ShortMdrHiv() + 1);
							table1.setTotalShortMdrHiv(table1.getTotalShortMdrHiv() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setRelapse1StandardMdr(table1.getRelapse1StandardMdr() + 1);
							table1.setRelapse1StandardMdrHiv(table1.getRelapse1StandardMdrHiv() + 1);
							table1.setTotalStandardMdrHiv(table1.getTotalStandardMdrHiv() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}
					}
				}

				else if (relapse2 != null && relapse2) {

					table1.setRelapse2Mdr(table1.getRelapse2Mdr() + 1);
					table1.setRelapse2Total(table1.getRelapse2Total() + 1);

					if (age > 0 && age <= 4) {
						table1.setRelapse2Mdr04(table1.getRelapse2Mdr04() + 1);
						table1.setTotalMdr04(table1.getTotalMdr04() + 1);
						table1.setRelapse2Total04(table1.getRelapse2Total04() + 1);
						
						if(isShort!=null && isShort) {
							table1.setRelapse2ShortMdr(table1.getRelapse2ShortMdr() + 1);
							table1.setRelapse2ShortMdr04(table1.getRelapse2ShortMdr04() + 1);
							table1.setTotalShortMdr04(table1.getTotalShortMdr04() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setRelapse2StandardMdr(table1.getRelapse2StandardMdr() + 1);
							table1.setRelapse2StandardMdr04(table1.getRelapse2StandardMdr04() + 1);
							table1.setTotalStandardMdr04(table1.getTotalStandardMdr04() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}

					}

					else if (age >= 5 && age <= 14) {
						table1.setRelapse2Mdr0514(table1.getRelapse2Mdr0514() + 1);
						table1.setTotalMdr0514(table1.getTotalMdr0514() + 1);
						table1.setRelapse2Total0514(table1.getRelapse2Total0514() + 1);
						
						if(isShort!=null && isShort) {
							table1.setRelapse2ShortMdr(table1.getRelapse2ShortMdr() + 1);
							table1.setRelapse2ShortMdr0514(table1.getRelapse2ShortMdr0514() + 1);
							table1.setTotalShortMdr0514(table1.getTotalShortMdr0514() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setRelapse2StandardMdr(table1.getRelapse2StandardMdr() + 1);
							table1.setRelapse2StandardMdr0514(table1.getRelapse2StandardMdr0514() + 1);
							table1.setTotalStandardMdr0514(table1.getTotalStandardMdr0514() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}
					}

					else if (age >= 15 && age <= 17) {
						table1.setRelapse2Mdr1517(table1.getRelapse2Mdr1517() + 1);
						table1.setTotalMdr1517(table1.getTotalMdr1517() + 1);
						table1.setRelapse2Total1517(table1.getRelapse2Total1517() + 1);
						
						if(isShort!=null && isShort) {
							table1.setRelapse2ShortMdr(table1.getRelapse2ShortMdr() + 1);
							table1.setRelapse2ShortMdr1517(table1.getRelapse2ShortMdr1517() + 1);
							table1.setTotalShortMdr1517(table1.getTotalShortMdr1517() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setRelapse2StandardMdr(table1.getRelapse2StandardMdr() + 1);
							table1.setRelapse2StandardMdr1517(table1.getRelapse2StandardMdr1517() + 1);
							table1.setTotalStandardMdr1517(table1.getTotalStandardMdr1517() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}
					}

					if (hiv != null && hiv) {
						table1.setRelapse2MdrHiv(table1.getRelapse2MdrHiv() + 1);
						table1.setTotalMdrHiv(table1.getTotalMdrHiv() + 1);
						table1.setRelapse2TotalHiv(table1.getRelapse2TotalHiv() + 1);
						
						if(isShort!=null && isShort) {
							table1.setRelapse2ShortMdr(table1.getRelapse2ShortMdr() + 1);
							table1.setRelapse2ShortMdrHiv(table1.getRelapse2ShortMdrHiv() + 1);
							table1.setTotalShortMdrHiv(table1.getTotalShortMdrHiv() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setRelapse2StandardMdr(table1.getRelapse2StandardMdr() + 1);
							table1.setRelapse2StandardMdrHiv(table1.getRelapse2StandardMdrHiv() + 1);
							table1.setTotalStandardMdrHiv(table1.getTotalStandardMdrHiv() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}
					}
				}

				else if (default1 != null && default1) {

					table1.setDefault1Mdr(table1.getDefault1Mdr() + 1);
					table1.setDefault1Total(table1.getDefault1Total() + 1);

					if (age > 0 && age <= 4) {
						table1.setDefault1Mdr04(table1.getDefault1Mdr04() + 1);
						table1.setTotalMdr04(table1.getTotalMdr04() + 1);
						table1.setDefault1Total04(table1.getDefault1Total04() + 1);
						
						if(isShort!=null && isShort) {
							table1.setDefault1ShortMdr(table1.getDefault1ShortMdr() + 1);
							table1.setDefault1ShortMdr04(table1.getDefault1ShortMdr04() + 1);
							table1.setTotalShortMdr04(table1.getTotalShortMdr04() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setDefault1StandardMdr(table1.getDefault1StandardMdr() + 1);
							table1.setDefault1StandardMdr04(table1.getDefault1StandardMdr04() + 1);
							table1.setTotalStandardMdr04(table1.getTotalStandardMdr04() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}

					}

					else if (age >= 5 && age <= 14) {
						table1.setDefault1Mdr0514(table1.getDefault1Mdr0514() + 1);
						table1.setTotalMdr0514(table1.getTotalMdr0514() + 1);
						table1.setDefault1Total0514(table1.getDefault1Total0514() + 1);
						
						if(isShort!=null && isShort) {
							table1.setDefault1ShortMdr(table1.getDefault1ShortMdr() + 1);
							table1.setDefault1ShortMdr0514(table1.getDefault1ShortMdr0514() + 1);
							table1.setTotalShortMdr0514(table1.getTotalShortMdr0514() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setDefault1StandardMdr(table1.getDefault1StandardMdr() + 1);
							table1.setDefault1StandardMdr0514(table1.getDefault1StandardMdr0514() + 1);
							table1.setTotalStandardMdr0514(table1.getTotalStandardMdr0514() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}

					}

					else if (age >= 15 && age <= 17) {
						table1.setDefault1Mdr1517(table1.getDefault1Mdr1517() + 1);
						table1.setTotalMdr1517(table1.getTotalMdr1517() + 1);
						table1.setDefault1Total1517(table1.getDefault1Total1517() + 1);
						
						if(isShort!=null && isShort) {
							table1.setDefault1ShortMdr(table1.getDefault1ShortMdr() + 1);
							table1.setDefault1ShortMdr1517(table1.getDefault1ShortMdr1517() + 1);
							table1.setTotalShortMdr1517(table1.getTotalShortMdr1517() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setDefault1StandardMdr(table1.getDefault1StandardMdr() + 1);
							table1.setDefault1StandardMdr1517(table1.getDefault1StandardMdr1517() + 1);
							table1.setTotalStandardMdr1517(table1.getTotalStandardMdr1517() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}

					}

					if (hiv != null && hiv) {
						table1.setDefault1MdrHiv(table1.getDefault1MdrHiv() + 1);
						table1.setTotalMdrHiv(table1.getTotalMdrHiv() + 1);
						table1.setDefault1TotalHiv(table1.getDefault1TotalHiv() + 1);
						
						if(isShort!=null && isShort) {
							table1.setDefault1ShortMdr(table1.getDefault1ShortMdr() + 1);
							table1.setDefault1ShortMdrHiv(table1.getDefault1ShortMdrHiv() + 1);
							table1.setTotalShortMdrHiv(table1.getTotalShortMdrHiv() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setDefault1StandardMdr(table1.getDefault1StandardMdr() + 1);
							table1.setDefault1StandardMdrHiv(table1.getDefault1StandardMdrHiv() + 1);
							table1.setTotalStandardMdrHiv(table1.getTotalStandardMdrHiv() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}

					}
				}

				else if (default2 != null && default2) {

					table1.setDefault2Mdr(table1.getDefault2Mdr() + 1);
					table1.setDefault2Total(table1.getDefault2Total() + 1);


					if (age > 0 && age <= 4) {
						table1.setDefault2Mdr04(table1.getDefault2Mdr04() + 1);
						table1.setTotalMdr04(table1.getTotalMdr04() + 1);
						table1.setDefault2Total04(table1.getDefault2Total04() + 1);
						
						if(isShort!=null && isShort) {
							table1.setDefault2ShortMdr(table1.getDefault2ShortMdr() + 1);
							table1.setDefault2ShortMdr04(table1.getDefault2ShortMdr04() + 1);
							table1.setTotalShortMdr04(table1.getTotalShortMdr04() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setDefault2StandardMdr(table1.getDefault2StandardMdr() + 1);
							table1.setDefault2StandardMdr04(table1.getDefault2StandardMdr04() + 1);
							table1.setTotalStandardMdr04(table1.getTotalStandardMdr04() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}
						

					}

					else if (age >= 5 && age <= 14) {
						table1.setDefault2Mdr0514(table1.getDefault2Mdr0514() + 1);
						table1.setTotalMdr0514(table1.getTotalMdr0514() + 1);
						table1.setDefault2Total0514(table1.getDefault2Total0514() + 1);
						
						if(isShort!=null && isShort) {
							table1.setDefault2ShortMdr(table1.getDefault2ShortMdr() + 1);
							table1.setDefault2ShortMdr0514(table1.getDefault2ShortMdr0514() + 1);
							table1.setTotalShortMdr0514(table1.getTotalShortMdr0514() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setDefault2StandardMdr(table1.getDefault2StandardMdr() + 1);
							table1.setDefault2StandardMdr0514(table1.getDefault2StandardMdr0514() + 1);
							table1.setTotalStandardMdr0514(table1.getTotalStandardMdr0514() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}

					}

					else if (age >= 15 && age <= 17) {
						table1.setDefault2Mdr1517(table1.getDefault2Mdr1517() + 1);
						table1.setTotalMdr1517(table1.getTotalMdr1517() + 1);
						table1.setDefault2Total1517(table1.getDefault2Total1517() + 1);
						
						if(isShort!=null && isShort) {
							table1.setDefault2ShortMdr(table1.getDefault2ShortMdr() + 1);
							table1.setDefault2ShortMdr1517(table1.getDefault2ShortMdr1517() + 1);
							table1.setTotalShortMdr1517(table1.getTotalShortMdr1517() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setDefault2StandardMdr(table1.getDefault2StandardMdr() + 1);
							table1.setDefault2StandardMdr1517(table1.getDefault2StandardMdr1517() + 1);
							table1.setTotalStandardMdr1517(table1.getTotalStandardMdr1517() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}

					}

					if (hiv != null && hiv) {
						table1.setDefault2MdrHiv(table1.getDefault2MdrHiv() + 1);
						table1.setTotalMdrHiv(table1.getTotalMdrHiv() + 1);
						table1.setDefault2TotalHiv(table1.getDefault2TotalHiv() + 1);
						
						if(isShort!=null && isShort) {
							table1.setDefault2ShortMdr(table1.getDefault2ShortMdr() + 1);
							table1.setDefault2ShortMdrHiv(table1.getDefault2ShortMdrHiv() + 1);
							table1.setTotalShortMdrHiv(table1.getTotalShortMdrHiv() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setDefault2StandardMdr(table1.getDefault2StandardMdr() + 1);
							table1.setDefault2StandardMdrHiv(table1.getDefault2StandardMdrHiv() + 1);
							table1.setTotalStandardMdrHiv(table1.getTotalStandardMdrHiv() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}

					}
				}
				
				else if (failure1 != null && failure1) {

					table1.setFailure1Mdr(table1.getFailure1Mdr() + 1);
					table1.setFailure1Total(table1.getFailure1Total() + 1);


					if (age > 0 && age <= 4) {
						table1.setFailure1Mdr04(table1.getFailure1Mdr04() + 1);
						table1.setTotalMdr04(table1.getTotalMdr04() + 1);
						table1.setFailure1Total04(table1.getFailure1Total04() + 1);
						
						if(isShort!=null && isShort) {
							table1.setFailure1ShortMdr(table1.getFailure1ShortMdr() + 1);
							table1.setFailure1ShortMdr04(table1.getFailure1ShortMdr04() + 1);
							table1.setTotalShortMdr04(table1.getTotalShortMdr04() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setFailure1StandardMdr(table1.getFailure1StandardMdr() + 1);
							table1.setFailure1StandardMdr04(table1.getFailure1StandardMdr04() + 1);
							table1.setTotalStandardMdr04(table1.getTotalStandardMdr04() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}

					}

					else if (age >= 5 && age <= 14) {
						table1.setFailure1Mdr0514(table1.getFailure1Mdr0514() + 1);
						table1.setTotalMdr0514(table1.getTotalMdr0514() + 1);
						table1.setFailure1Total0514(table1.getFailure1Total0514() + 1);
						
						if(isShort!=null && isShort) {
							table1.setFailure1ShortMdr(table1.getFailure1ShortMdr() + 1);
							table1.setFailure1ShortMdr0514(table1.getFailure1ShortMdr0514() + 1);
							table1.setTotalShortMdr0514(table1.getTotalShortMdr0514() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setFailure1StandardMdr(table1.getFailure1StandardMdr() + 1);
							table1.setFailure1StandardMdr0514(table1.getFailure1StandardMdr0514() + 1);
							table1.setTotalStandardMdr0514(table1.getTotalStandardMdr0514() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}
					}

					else if (age >= 15 && age <= 17) {
						table1.setFailure1Mdr1517(table1.getFailure1Mdr1517() + 1);
						table1.setTotalMdr1517(table1.getTotalMdr1517() + 1);
						table1.setFailure1Total1517(table1.getFailure1Total1517() + 1);
						
						if(isShort!=null && isShort) {
							table1.setFailure1ShortMdr(table1.getFailure1ShortMdr() + 1);
							table1.setFailure1ShortMdr1517(table1.getFailure1ShortMdr1517() + 1);
							table1.setTotalShortMdr1517(table1.getTotalShortMdr1517() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setFailure1StandardMdr(table1.getFailure1StandardMdr() + 1);
							table1.setFailure1StandardMdr1517(table1.getFailure1StandardMdr1517() + 1);
							table1.setTotalStandardMdr1517(table1.getTotalStandardMdr1517() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}
					}

					if (hiv != null && hiv) {
						table1.setFailure1MdrHiv(table1.getFailure1MdrHiv() + 1);
						table1.setTotalMdrHiv(table1.getTotalMdrHiv() + 1);
						table1.setFailure1TotalHiv(table1.getFailure1TotalHiv() + 1);
						
						if(isShort!=null && isShort) {
							table1.setFailure1ShortMdr(table1.getFailure1ShortMdr() + 1);
							table1.setFailure1ShortMdrHiv(table1.getFailure1ShortMdrHiv() + 1);
							table1.setTotalShortMdrHiv(table1.getTotalShortMdrHiv() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setFailure1StandardMdr(table1.getFailure1StandardMdr() + 1);
							table1.setFailure1StandardMdrHiv(table1.getFailure1StandardMdrHiv() + 1);
							table1.setTotalStandardMdrHiv(table1.getTotalStandardMdrHiv() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}
					}
				}

				else if (failure2 != null && failure2) {

					table1.setFailure2Mdr(table1.getFailure2Mdr() + 1);
					table1.setFailure2Total(table1.getFailure2Total() + 1);

					if (age > 0 && age <= 4) {
						table1.setFailure2Mdr04(table1.getFailure2Mdr04() + 1);
						table1.setTotalMdr04(table1.getTotalMdr04() + 1);
						table1.setFailure2Total04(table1.getFailure2Total04() + 1);
						
						if(isShort!=null && isShort) {
							table1.setFailure2ShortMdr(table1.getFailure2ShortMdr() + 1);
							table1.setFailure2ShortMdr04(table1.getFailure2ShortMdr04() + 1);
							table1.setTotalShortMdr04(table1.getTotalShortMdr04() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setFailure2StandardMdr(table1.getFailure2StandardMdr() + 1);
							table1.setFailure2StandardMdr04(table1.getFailure2StandardMdr04() + 1);
							table1.setTotalStandardMdr04(table1.getTotalStandardMdr04() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}

					}

					else if (age >= 5 && age <= 14) {
						table1.setFailure2Mdr0514(table1.getFailure2Mdr0514() + 1);
						table1.setTotalMdr0514(table1.getTotalMdr0514() + 1);
						table1.setFailure2Total0514(table1.getFailure2Total0514() + 1);
						
						if(isShort!=null && isShort) {
							table1.setFailure2ShortMdr(table1.getFailure2ShortMdr() + 1);
							table1.setFailure2ShortMdr0514(table1.getFailure2ShortMdr0514() + 1);
							table1.setTotalShortMdr0514(table1.getTotalShortMdr0514() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setFailure2StandardMdr(table1.getFailure2StandardMdr() + 1);
							table1.setFailure2StandardMdr0514(table1.getFailure2StandardMdr0514() + 1);
							table1.setTotalStandardMdr0514(table1.getTotalStandardMdr0514() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}
					}

					else if (age >= 15 && age <= 17) {
						table1.setFailure2Mdr1517(table1.getFailure2Mdr1517() + 1);
						table1.setTotalMdr1517(table1.getTotalMdr1517() + 1);
						table1.setFailure2Total1517(table1.getFailure2Total1517() + 1);
						
						if(isShort!=null && isShort) {
							table1.setFailure2ShortMdr(table1.getFailure2ShortMdr() + 1);
							table1.setFailure2ShortMdr1517(table1.getFailure2ShortMdr1517() + 1);
							table1.setTotalShortMdr1517(table1.getTotalShortMdr1517() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setFailure2StandardMdr(table1.getFailure2StandardMdr() + 1);
							table1.setFailure2StandardMdr1517(table1.getFailure2StandardMdr1517() + 1);
							table1.setTotalStandardMdr1517(table1.getTotalStandardMdr1517() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}
					}

					if (hiv != null && hiv) {
						table1.setFailure2MdrHiv(table1.getFailure2MdrHiv() + 1);
						table1.setTotalMdrHiv(table1.getTotalMdrHiv() + 1);
						table1.setFailure2TotalHiv(table1.getFailure2TotalHiv() + 1);
						
						if(isShort!=null && isShort) {
							table1.setFailure2ShortMdr(table1.getFailure2ShortMdr() + 1);
							table1.setFailure2ShortMdrHiv(table1.getFailure2ShortMdrHiv() + 1);
							table1.setTotalShortMdrHiv(table1.getTotalShortMdrHiv() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setFailure2StandardMdr(table1.getFailure2StandardMdr() + 1);
							table1.setFailure2StandardMdrHiv(table1.getFailure2StandardMdrHiv() + 1);
							table1.setTotalStandardMdrHiv(table1.getTotalStandardMdrHiv() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}
					}
				}
				
				else if (other != null && other) {

					table1.setOtherMdr(table1.getOtherMdr() + 1);
					table1.setOtherTotal(table1.getOtherTotal() + 1);

					if (age > 0 && age <= 4) {
						table1.setOtherMdr04(table1.getOtherMdr04() + 1);
						table1.setTotalMdr04(table1.getTotalMdr04() + 1);
						table1.setOtherTotal04(table1.getOtherTotal04() + 1);
						
						if(isShort!=null && isShort) {
							table1.setOtherShortMdr(table1.getOtherShortMdr() + 1);
							table1.setOtherShortMdr04(table1.getOtherShortMdr04() + 1);
							table1.setTotalShortMdr04(table1.getTotalShortMdr04() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setOtherStandardMdr(table1.getOtherStandardMdr() + 1);
							table1.setOtherStandardMdr04(table1.getOtherStandardMdr04() + 1);
							table1.setTotalStandardMdr04(table1.getTotalStandardMdr04() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}

					}

					else if (age >= 5 && age <= 14) {
						table1.setOtherMdr0514(table1.getOtherMdr0514() + 1);
						table1.setTotalMdr0514(table1.getTotalMdr0514() + 1);
						table1.setOtherTotal0514(table1.getOtherTotal0514() + 1);
						
						if(isShort!=null && isShort) {
							table1.setOtherShortMdr(table1.getOtherShortMdr() + 1);
							table1.setOtherShortMdr0514(table1.getOtherShortMdr0514() + 1);
							table1.setTotalShortMdr0514(table1.getTotalShortMdr0514() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setOtherStandardMdr(table1.getOtherStandardMdr() + 1);
							table1.setOtherStandardMdr0514(table1.getOtherStandardMdr0514() + 1);
							table1.setTotalStandardMdr0514(table1.getTotalStandardMdr0514() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}
					}

					else if (age >= 15 && age <= 17) {
						table1.setOtherMdr1517(table1.getOtherMdr1517() + 1);
						table1.setTotalMdr1517(table1.getTotalMdr1517() + 1);
						table1.setOtherTotal1517(table1.getOtherTotal1517() + 1);
						
						if(isShort!=null && isShort) {
							table1.setOtherShortMdr(table1.getOtherShortMdr() + 1);
							table1.setOtherShortMdr1517(table1.getOtherShortMdr1517() + 1);
							table1.setTotalShortMdr1517(table1.getTotalShortMdr1517() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setOtherStandardMdr(table1.getOtherStandardMdr() + 1);
							table1.setOtherStandardMdr1517(table1.getOtherStandardMdr1517() + 1);
							table1.setTotalStandardMdr1517(table1.getTotalStandardMdr1517() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}
					}

					if (hiv != null && hiv) {
						table1.setOtherMdrHiv(table1.getOtherMdrHiv() + 1);
						table1.setTotalMdrHiv(table1.getTotalMdrHiv() + 1);
						table1.setOtherTotalHiv(table1.getOtherTotalHiv() + 1);
						
						if(isShort!=null && isShort) {
							table1.setOtherShortMdr(table1.getOtherShortMdr() + 1);
							table1.setOtherShortMdrHiv(table1.getOtherShortMdrHiv() + 1);
							table1.setTotalShortMdrHiv(table1.getTotalShortMdrHiv() + 1);
							table1.setTotalShortMdr(table1.getTotalShortMdr() + 1);
							
						}
						
						else if(isStandard!=null && isStandard) {
							table1.setOtherStandardMdr(table1.getOtherStandardMdr() + 1);
							table1.setOtherStandardMdrHiv(table1.getOtherStandardMdrHiv() + 1);
							table1.setTotalStandardMdrHiv(table1.getTotalStandardMdrHiv() + 1);
							table1.setTotalStandardMdr(table1.getTotalStandardMdr() + 1);
						}
					}
				}
			}

			else if (q.getConceptId().intValue() == preXdr) {
				table1.setPreXdrDetections(table1.getPreXdrDetections() + 1);
				table1.setTotalPreXdr(table1.getTotalPreXdr() + 1);
				table1.setTotalDetections(table1.getTotalDetections() + 1);
				
				if(regList!=null) {
					rf = getLatestRegimenForPatient(tf.getPatient().getPatientId().intValue(),regList, locList, year, quarter, month);
				}
				
				if(rf!=null) {
					regimen = rf.getSldRegimenType();
					if(regimen!=null) {
						if(regimen.getConceptId().intValue()==indLzd) {
							isIndLzd = Boolean.TRUE;
						}
						
						else if(regimen.getConceptId().intValue()==indBdq) {
							isIndBdq = Boolean.TRUE;
						}
					}
				}
				
				if (newCase != null && newCase) {

					table1.setNewPreXdr(table1.getNewPreXdr() + 1);
					table1.setNewTotal(table1.getNewTotal() + 1);
					

					if (age > 0 && age <= 4) {
						table1.setNewPreXdr04(table1.getNewPreXdr04() + 1);
						table1.setTotalPreXdr04(table1.getTotalPreXdr04() + 1);
						table1.setNewTotal04(table1.getNewTotal04() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setNewIndLzdXdrPreXdr(table1.getNewIndLzdXdrPreXdr() + 1);
							table1.setNewIndLzdXdrPreXdr04(table1.getNewIndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr04(table1.getTotalIndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setNewIndBdqXdrPreXdr(table1.getNewIndBdqXdrPreXdr() + 1);
							table1.setNewIndBdqXdrPreXdr04(table1.getNewIndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr04(table1.getTotalIndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}

					else if (age >= 5 && age <= 14) {
						table1.setNewPreXdr0514(table1.getNewPreXdr0514() + 1);
						table1.setTotalPreXdr0514(table1.getTotalPreXdr0514() + 1);
						table1.setNewTotal0514(table1.getNewTotal0514() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setNewIndLzdXdrPreXdr(table1.getNewIndLzdXdrPreXdr() + 1);
							table1.setNewIndLzdXdrPreXdr0514(table1.getNewIndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr0514(table1.getTotalIndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setNewIndBdqXdrPreXdr(table1.getNewIndBdqXdrPreXdr() + 1);
							table1.setNewIndBdqXdrPreXdr0514(table1.getNewIndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr0514(table1.getTotalIndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}

					else if (age >= 15 && age <= 17) {
						table1.setNewPreXdr1517(table1.getNewPreXdr1517() + 1);
						table1.setTotalPreXdr1517(table1.getTotalPreXdr1517() + 1);
						table1.setNewTotal1517(table1.getNewTotal1517() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setNewIndLzdXdrPreXdr(table1.getNewIndLzdXdrPreXdr() + 1);
							table1.setNewIndLzdXdrPreXdr1517(table1.getNewIndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr1517(table1.getTotalIndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setNewIndBdqXdrPreXdr(table1.getNewIndBdqXdrPreXdr() + 1);
							table1.setNewIndBdqXdrPreXdr1517(table1.getNewIndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr1517(table1.getTotalIndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}

					if (hiv != null && hiv) {
						table1.setNewPreXdrHiv(table1.getNewPreXdrHiv() + 1);
						table1.setTotalPreXdrHiv(table1.getTotalPreXdrHiv() + 1);
						table1.setNewTotalHiv(table1.getNewTotalHiv() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setNewIndLzdXdrPreXdr(table1.getNewIndLzdXdrPreXdr() + 1);
							table1.setNewIndLzdXdrPreXdrHiv(table1.getNewIndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdrHiv(table1.getTotalIndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setNewIndBdqXdrPreXdr(table1.getNewIndBdqXdrPreXdr() + 1);
							table1.setNewIndBdqXdrPreXdrHiv(table1.getNewIndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdrHiv(table1.getTotalIndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}
				}

				else if (relapse1 != null && relapse1) {

					table1.setRelapse1PreXdr(table1.getRelapse1PreXdr() + 1);
					table1.setRelapse1Total(table1.getRelapse1Total() + 1);
					

					if (age > 0 && age <= 4) {
						table1.setRelapse1PreXdr04(table1.getRelapse1PreXdr04() + 1);
						table1.setTotalPreXdr04(table1.getTotalPreXdr04() + 1);
						table1.setRelapse1Total04(table1.getRelapse1Total04() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setRelapse1IndLzdXdrPreXdr(table1.getRelapse1IndLzdXdrPreXdr() + 1);
							table1.setRelapse1IndLzdXdrPreXdr04(table1.getRelapse1IndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr04(table1.getTotalIndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setRelapse1IndBdqXdrPreXdr(table1.getRelapse1IndBdqXdrPreXdr() + 1);
							table1.setRelapse1IndBdqXdrPreXdr04(table1.getRelapse1IndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr04(table1.getTotalIndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}

					else if (age >= 5 && age <= 14) {
						table1.setRelapse1PreXdr0514(table1.getRelapse1PreXdr0514() + 1);
						table1.setTotalPreXdr0514(table1.getTotalPreXdr0514() + 1);
						table1.setRelapse1Total0514(table1.getRelapse1Total0514() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setRelapse1IndLzdXdrPreXdr(table1.getRelapse1IndLzdXdrPreXdr() + 1);
							table1.setRelapse1IndLzdXdrPreXdr0514(table1.getRelapse1IndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr0514(table1.getTotalIndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setRelapse1IndBdqXdrPreXdr(table1.getRelapse1IndBdqXdrPreXdr() + 1);
							table1.setRelapse1IndBdqXdrPreXdr0514(table1.getRelapse1IndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr0514(table1.getTotalIndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}

					else if (age >= 15 && age <= 17) {
						table1.setRelapse1PreXdr1517(table1.getRelapse1PreXdr1517() + 1);
						table1.setTotalPreXdr1517(table1.getTotalPreXdr1517() + 1);
						table1.setRelapse1Total1517(table1.getRelapse1Total1517() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setRelapse1IndLzdXdrPreXdr(table1.getRelapse1IndLzdXdrPreXdr() + 1);
							table1.setRelapse1IndLzdXdrPreXdr1517(table1.getRelapse1IndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr1517(table1.getTotalIndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setRelapse1IndBdqXdrPreXdr(table1.getRelapse1IndBdqXdrPreXdr() + 1);
							table1.setRelapse1IndBdqXdrPreXdr1517(table1.getRelapse1IndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr1517(table1.getTotalIndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}

					if (hiv != null && hiv) {
						table1.setRelapse1PreXdrHiv(table1.getRelapse1PreXdrHiv() + 1);
						table1.setTotalPreXdrHiv(table1.getTotalPreXdrHiv() + 1);
						table1.setRelapse1TotalHiv(table1.getRelapse1TotalHiv() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setRelapse1IndLzdXdrPreXdr(table1.getRelapse1IndLzdXdrPreXdr() + 1);
							table1.setRelapse1IndLzdXdrPreXdrHiv(table1.getRelapse1IndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdrHiv(table1.getTotalIndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setRelapse1IndBdqXdrPreXdr(table1.getRelapse1IndBdqXdrPreXdr() + 1);
							table1.setRelapse1IndBdqXdrPreXdrHiv(table1.getRelapse1IndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdrHiv(table1.getTotalIndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}
				}

				else if (relapse2 != null && relapse2) {

					table1.setRelapse2PreXdr(table1.getRelapse2PreXdr() + 1);
					table1.setRelapse2Total(table1.getRelapse2Total() + 1);

					if (age > 0 && age <= 4) {
						table1.setRelapse2PreXdr04(table1.getRelapse2PreXdr04() + 1);
						table1.setTotalPreXdr04(table1.getTotalPreXdr04() + 1);
						table1.setRelapse2Total04(table1.getRelapse2Total04() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setRelapse2IndLzdXdrPreXdr(table1.getRelapse2IndLzdXdrPreXdr() + 1);
							table1.setRelapse2IndLzdXdrPreXdr04(table1.getRelapse2IndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr04(table1.getTotalIndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setRelapse2IndBdqXdrPreXdr(table1.getRelapse2IndBdqXdrPreXdr() + 1);
							table1.setRelapse2IndBdqXdrPreXdr04(table1.getRelapse2IndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr04(table1.getTotalIndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}

					else if (age >= 5 && age <= 14) {
						table1.setRelapse2PreXdr0514(table1.getRelapse2PreXdr0514() + 1);
						table1.setTotalPreXdr0514(table1.getTotalPreXdr0514() + 1);
						table1.setRelapse2Total0514(table1.getRelapse2Total0514() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setRelapse2IndLzdXdrPreXdr(table1.getRelapse2IndLzdXdrPreXdr() + 1);
							table1.setRelapse2IndLzdXdrPreXdr0514(table1.getRelapse2IndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr0514(table1.getTotalIndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setRelapse2IndBdqXdrPreXdr(table1.getRelapse2IndBdqXdrPreXdr() + 1);
							table1.setRelapse2IndBdqXdrPreXdr0514(table1.getRelapse2IndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr0514(table1.getTotalIndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}

					else if (age >= 15 && age <= 17) {
						table1.setRelapse2PreXdr1517(table1.getRelapse2PreXdr1517() + 1);
						table1.setTotalPreXdr1517(table1.getTotalPreXdr1517() + 1);
						table1.setRelapse2Total1517(table1.getRelapse2Total1517() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setRelapse2IndLzdXdrPreXdr(table1.getRelapse2IndLzdXdrPreXdr() + 1);
							table1.setRelapse2IndLzdXdrPreXdr1517(table1.getRelapse2IndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr1517(table1.getTotalIndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setRelapse2IndBdqXdrPreXdr(table1.getRelapse2IndBdqXdrPreXdr() + 1);
							table1.setRelapse2IndBdqXdrPreXdr1517(table1.getRelapse2IndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr1517(table1.getTotalIndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}

					if (hiv != null && hiv) {
						table1.setRelapse2PreXdrHiv(table1.getRelapse2PreXdrHiv() + 1);
						table1.setTotalPreXdrHiv(table1.getTotalPreXdrHiv() + 1);
						table1.setRelapse2TotalHiv(table1.getRelapse2TotalHiv() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setRelapse2IndLzdXdrPreXdr(table1.getRelapse2IndLzdXdrPreXdr() + 1);
							table1.setRelapse2IndLzdXdrPreXdrHiv(table1.getRelapse2IndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdrHiv(table1.getTotalIndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setRelapse2IndBdqXdrPreXdr(table1.getRelapse2IndBdqXdrPreXdr() + 1);
							table1.setRelapse2IndBdqXdrPreXdrHiv(table1.getRelapse2IndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdrHiv(table1.getTotalIndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}
				}

				else if (default1 != null && default1) {

					table1.setDefault1PreXdr(table1.getDefault1PreXdr() + 1);
					table1.setDefault1Total(table1.getDefault1Total() + 1);

					if (age > 0 && age <= 4) {
						table1.setDefault1PreXdr04(table1.getDefault1PreXdr04() + 1);
						table1.setTotalPreXdr04(table1.getTotalPreXdr04() + 1);
						table1.setDefault1Total04(table1.getDefault1Total04() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setDefault1IndLzdXdrPreXdr(table1.getDefault1IndLzdXdrPreXdr() + 1);
							table1.setDefault1IndLzdXdrPreXdr04(table1.getDefault1IndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr04(table1.getTotalIndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setDefault1IndBdqXdrPreXdr(table1.getDefault1IndBdqXdrPreXdr() + 1);
							table1.setDefault1IndBdqXdrPreXdr04(table1.getDefault1IndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr04(table1.getTotalIndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}


					}

					else if (age >= 5 && age <= 14) {
						table1.setDefault1PreXdr0514(table1.getDefault1PreXdr0514() + 1);
						table1.setTotalPreXdr0514(table1.getTotalPreXdr0514() + 1);
						table1.setDefault1Total0514(table1.getDefault1Total0514() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setDefault1IndLzdXdrPreXdr(table1.getDefault1IndLzdXdrPreXdr() + 1);
							table1.setDefault1IndLzdXdrPreXdr0514(table1.getDefault1IndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr0514(table1.getTotalIndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setDefault1IndBdqXdrPreXdr(table1.getDefault1IndBdqXdrPreXdr() + 1);
							table1.setDefault1IndBdqXdrPreXdr0514(table1.getDefault1IndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr0514(table1.getTotalIndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}

					else if (age >= 15 && age <= 17) {
						table1.setDefault1PreXdr1517(table1.getDefault1PreXdr1517() + 1);
						table1.setTotalPreXdr1517(table1.getTotalPreXdr1517() + 1);
						table1.setDefault1Total1517(table1.getDefault1Total1517() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setDefault1IndLzdXdrPreXdr(table1.getDefault1IndLzdXdrPreXdr() + 1);
							table1.setDefault1IndLzdXdrPreXdr1517(table1.getDefault1IndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr1517(table1.getTotalIndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setDefault1IndBdqXdrPreXdr(table1.getDefault1IndBdqXdrPreXdr() + 1);
							table1.setDefault1IndBdqXdrPreXdr1517(table1.getDefault1IndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr1517(table1.getTotalIndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}

					if (hiv != null && hiv) {
						table1.setDefault1PreXdrHiv(table1.getDefault1PreXdrHiv() + 1);
						table1.setTotalPreXdrHiv(table1.getTotalPreXdrHiv() + 1);
						table1.setDefault1TotalHiv(table1.getDefault1TotalHiv() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setDefault1IndLzdXdrPreXdr(table1.getDefault1IndLzdXdrPreXdr() + 1);
							table1.setDefault1IndLzdXdrPreXdrHiv(table1.getDefault1IndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdrHiv(table1.getTotalIndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setDefault1IndBdqXdrPreXdr(table1.getDefault1IndBdqXdrPreXdr() + 1);
							table1.setDefault1IndBdqXdrPreXdrHiv(table1.getDefault1IndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdrHiv(table1.getTotalIndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}
				}

				else if (default2 != null && default2) {

					table1.setDefault2PreXdr(table1.getDefault2PreXdr() + 1);
					table1.setDefault2Total(table1.getDefault2Total() + 1);


					if (age > 0 && age <= 4) {
						table1.setDefault2PreXdr04(table1.getDefault2PreXdr04() + 1);
						table1.setTotalPreXdr04(table1.getTotalPreXdr04() + 1);
						table1.setDefault2Total04(table1.getDefault2Total04() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setDefault2IndLzdXdrPreXdr(table1.getDefault2IndLzdXdrPreXdr() + 1);
							table1.setDefault2IndLzdXdrPreXdr04(table1.getDefault2IndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr04(table1.getTotalIndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setDefault2IndBdqXdrPreXdr(table1.getDefault2IndBdqXdrPreXdr() + 1);
							table1.setDefault2IndBdqXdrPreXdr04(table1.getDefault2IndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr04(table1.getTotalIndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

						

					}

					else if (age >= 5 && age <= 14) {
						table1.setDefault2PreXdr0514(table1.getDefault2PreXdr0514() + 1);
						table1.setTotalPreXdr0514(table1.getTotalPreXdr0514() + 1);
						table1.setDefault2Total0514(table1.getDefault2Total0514() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setDefault2IndLzdXdrPreXdr(table1.getDefault2IndLzdXdrPreXdr() + 1);
							table1.setDefault2IndLzdXdrPreXdr0514(table1.getDefault2IndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr0514(table1.getTotalIndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setDefault2IndBdqXdrPreXdr(table1.getDefault2IndBdqXdrPreXdr() + 1);
							table1.setDefault2IndBdqXdrPreXdr0514(table1.getDefault2IndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr0514(table1.getTotalIndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}

					else if (age >= 15 && age <= 17) {
						table1.setDefault2PreXdr1517(table1.getDefault2PreXdr1517() + 1);
						table1.setTotalPreXdr1517(table1.getTotalPreXdr1517() + 1);
						table1.setDefault2Total1517(table1.getDefault2Total1517() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setDefault2IndLzdXdrPreXdr(table1.getDefault2IndLzdXdrPreXdr() + 1);
							table1.setDefault2IndLzdXdrPreXdr1517(table1.getDefault2IndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr1517(table1.getTotalIndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setDefault2IndBdqXdrPreXdr(table1.getDefault2IndBdqXdrPreXdr() + 1);
							table1.setDefault2IndBdqXdrPreXdr1517(table1.getDefault2IndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr1517(table1.getTotalIndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}

					if (hiv != null && hiv) {
						table1.setDefault2PreXdrHiv(table1.getDefault2PreXdrHiv() + 1);
						table1.setTotalPreXdrHiv(table1.getTotalPreXdrHiv() + 1);
						table1.setDefault2TotalHiv(table1.getDefault2TotalHiv() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setDefault2IndLzdXdrPreXdr(table1.getDefault2IndLzdXdrPreXdr() + 1);
							table1.setDefault2IndLzdXdrPreXdrHiv(table1.getDefault2IndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdrHiv(table1.getTotalIndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setDefault2IndBdqXdrPreXdr(table1.getDefault2IndBdqXdrPreXdr() + 1);
							table1.setDefault2IndBdqXdrPreXdrHiv(table1.getDefault2IndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdrHiv(table1.getTotalIndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}
				}
				
				else if (failure1 != null && failure1) {

					table1.setFailure1PreXdr(table1.getFailure1PreXdr() + 1);
					table1.setFailure1Total(table1.getFailure1Total() + 1);


					if (age > 0 && age <= 4) {
						table1.setFailure1PreXdr04(table1.getFailure1PreXdr04() + 1);
						table1.setTotalPreXdr04(table1.getTotalPreXdr04() + 1);
						table1.setFailure1Total04(table1.getFailure1Total04() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setFailure1IndLzdXdrPreXdr(table1.getFailure1IndLzdXdrPreXdr() + 1);
							table1.setFailure1IndLzdXdrPreXdr04(table1.getFailure1IndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr04(table1.getTotalIndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setFailure1IndBdqXdrPreXdr(table1.getFailure1IndBdqXdrPreXdr() + 1);
							table1.setFailure1IndBdqXdrPreXdr04(table1.getFailure1IndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr04(table1.getTotalIndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}

					else if (age >= 5 && age <= 14) {
						table1.setFailure1PreXdr0514(table1.getFailure1PreXdr0514() + 1);
						table1.setTotalPreXdr0514(table1.getTotalPreXdr0514() + 1);
						table1.setFailure1Total0514(table1.getFailure1Total0514() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setFailure1IndLzdXdrPreXdr(table1.getFailure1IndLzdXdrPreXdr() + 1);
							table1.setFailure1IndLzdXdrPreXdr0514(table1.getFailure1IndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr0514(table1.getTotalIndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setFailure1IndBdqXdrPreXdr(table1.getFailure1IndBdqXdrPreXdr() + 1);
							table1.setFailure1IndBdqXdrPreXdr0514(table1.getFailure1IndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr0514(table1.getTotalIndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}

					else if (age >= 15 && age <= 17) {
						table1.setFailure1PreXdr1517(table1.getFailure1PreXdr1517() + 1);
						table1.setTotalPreXdr1517(table1.getTotalPreXdr1517() + 1);
						table1.setFailure1Total1517(table1.getFailure1Total1517() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setFailure1IndLzdXdrPreXdr(table1.getFailure1IndLzdXdrPreXdr() + 1);
							table1.setFailure1IndLzdXdrPreXdr1517(table1.getFailure1IndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr1517(table1.getTotalIndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setFailure1IndBdqXdrPreXdr(table1.getFailure1IndBdqXdrPreXdr() + 1);
							table1.setFailure1IndBdqXdrPreXdr1517(table1.getFailure1IndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr1517(table1.getTotalIndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}

					if (hiv != null && hiv) {
						table1.setFailure1PreXdrHiv(table1.getFailure1PreXdrHiv() + 1);
						table1.setTotalPreXdrHiv(table1.getTotalPreXdrHiv() + 1);
						table1.setFailure1TotalHiv(table1.getFailure1TotalHiv() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setFailure1IndLzdXdrPreXdr(table1.getFailure1IndLzdXdrPreXdr() + 1);
							table1.setFailure1IndLzdXdrPreXdrHiv(table1.getFailure1IndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdrHiv(table1.getTotalIndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setFailure1IndBdqXdrPreXdr(table1.getFailure1IndBdqXdrPreXdr() + 1);
							table1.setFailure1IndBdqXdrPreXdrHiv(table1.getFailure1IndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdrHiv(table1.getTotalIndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}
				}

				else if (failure2 != null && failure2) {

					table1.setFailure2PreXdr(table1.getFailure2PreXdr() + 1);
					table1.setFailure2Total(table1.getFailure2Total() + 1);

					if (age > 0 && age <= 4) {
						table1.setFailure2PreXdr04(table1.getFailure2PreXdr04() + 1);
						table1.setTotalPreXdr04(table1.getTotalPreXdr04() + 1);
						table1.setFailure2Total04(table1.getFailure2Total04() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setFailure2IndLzdXdrPreXdr(table1.getFailure2IndLzdXdrPreXdr() + 1);
							table1.setFailure2IndLzdXdrPreXdr04(table1.getFailure2IndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr04(table1.getTotalIndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setFailure2IndBdqXdrPreXdr(table1.getFailure2IndBdqXdrPreXdr() + 1);
							table1.setFailure2IndBdqXdrPreXdr04(table1.getFailure2IndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr04(table1.getTotalIndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}

					else if (age >= 5 && age <= 14) {
						table1.setFailure2PreXdr0514(table1.getFailure2PreXdr0514() + 1);
						table1.setTotalPreXdr0514(table1.getTotalPreXdr0514() + 1);
						table1.setFailure2Total0514(table1.getFailure2Total0514() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setFailure2IndLzdXdrPreXdr(table1.getFailure2IndLzdXdrPreXdr() + 1);
							table1.setFailure2IndLzdXdrPreXdr0514(table1.getFailure2IndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr0514(table1.getTotalIndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setFailure2IndBdqXdrPreXdr(table1.getFailure2IndBdqXdrPreXdr() + 1);
							table1.setFailure2IndBdqXdrPreXdr0514(table1.getFailure2IndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr0514(table1.getTotalIndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}

					else if (age >= 15 && age <= 17) {
						table1.setFailure2PreXdr1517(table1.getFailure2PreXdr1517() + 1);
						table1.setTotalPreXdr1517(table1.getTotalPreXdr1517() + 1);
						table1.setFailure2Total1517(table1.getFailure2Total1517() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setFailure2IndLzdXdrPreXdr(table1.getFailure2IndLzdXdrPreXdr() + 1);
							table1.setFailure2IndLzdXdrPreXdr1517(table1.getFailure2IndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr1517(table1.getTotalIndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setFailure2IndBdqXdrPreXdr(table1.getFailure2IndBdqXdrPreXdr() + 1);
							table1.setFailure2IndBdqXdrPreXdr1517(table1.getFailure2IndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr1517(table1.getTotalIndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}

					if (hiv != null && hiv) {
						table1.setFailure2PreXdrHiv(table1.getFailure2PreXdrHiv() + 1);
						table1.setTotalPreXdrHiv(table1.getTotalPreXdrHiv() + 1);
						table1.setFailure2TotalHiv(table1.getFailure2TotalHiv() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setFailure2IndLzdXdrPreXdr(table1.getFailure2IndLzdXdrPreXdr() + 1);
							table1.setFailure2IndLzdXdrPreXdrHiv(table1.getFailure2IndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdrHiv(table1.getTotalIndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setFailure2IndBdqXdrPreXdr(table1.getFailure2IndBdqXdrPreXdr() + 1);
							table1.setFailure2IndBdqXdrPreXdrHiv(table1.getFailure2IndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdrHiv(table1.getTotalIndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}
				}
				
				else if (other != null && other) {

					table1.setOtherPreXdr(table1.getOtherPreXdr() + 1);
					table1.setOtherTotal(table1.getOtherTotal() + 1);

					if (age > 0 && age <= 4) {
						table1.setOtherPreXdr04(table1.getOtherPreXdr04() + 1);
						table1.setTotalPreXdr04(table1.getTotalPreXdr04() + 1);
						table1.setOtherTotal04(table1.getOtherTotal04() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setOtherIndLzdXdrPreXdr(table1.getOtherIndLzdXdrPreXdr() + 1);
							table1.setOtherIndLzdXdrPreXdr04(table1.getOtherIndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr04(table1.getTotalIndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setOtherIndBdqXdrPreXdr(table1.getOtherIndBdqXdrPreXdr() + 1);
							table1.setOtherIndBdqXdrPreXdr04(table1.getOtherIndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr04(table1.getTotalIndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}


					}

					else if (age >= 5 && age <= 14) {
						table1.setOtherPreXdr0514(table1.getOtherPreXdr0514() + 1);
						table1.setTotalPreXdr0514(table1.getTotalPreXdr0514() + 1);
						table1.setOtherTotal0514(table1.getOtherTotal0514() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setOtherIndLzdXdrPreXdr(table1.getOtherIndLzdXdrPreXdr() + 1);
							table1.setOtherIndLzdXdrPreXdr0514(table1.getOtherIndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr0514(table1.getTotalIndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setOtherIndBdqXdrPreXdr(table1.getOtherIndBdqXdrPreXdr() + 1);
							table1.setOtherIndBdqXdrPreXdr0514(table1.getOtherIndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr0514(table1.getTotalIndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}

					else if (age >= 15 && age <= 17) {
						table1.setOtherPreXdr1517(table1.getOtherPreXdr1517() + 1);
						table1.setTotalPreXdr1517(table1.getTotalPreXdr1517() + 1);
						table1.setOtherTotal1517(table1.getOtherTotal1517() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setOtherIndLzdXdrPreXdr(table1.getOtherIndLzdXdrPreXdr() + 1);
							table1.setOtherIndLzdXdrPreXdr1517(table1.getOtherIndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr1517(table1.getTotalIndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setOtherIndBdqXdrPreXdr(table1.getOtherIndBdqXdrPreXdr() + 1);
							table1.setOtherIndBdqXdrPreXdr1517(table1.getOtherIndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr1517(table1.getTotalIndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}

					if (hiv != null && hiv) {
						table1.setOtherPreXdrHiv(table1.getOtherPreXdrHiv() + 1);
						table1.setTotalPreXdrHiv(table1.getTotalPreXdrHiv() + 1);
						table1.setOtherTotalHiv(table1.getOtherTotalHiv() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setOtherIndLzdXdrPreXdr(table1.getOtherIndLzdXdrPreXdr() + 1);
							table1.setOtherIndLzdXdrPreXdrHiv(table1.getOtherIndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdrHiv(table1.getTotalIndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setOtherIndBdqXdrPreXdr(table1.getOtherIndBdqXdrPreXdr() + 1);
							table1.setOtherIndBdqXdrPreXdrHiv(table1.getOtherIndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdrHiv(table1.getTotalIndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}
				}
			}

			else if (q.getConceptId().intValue() == xdr) {
				table1.setXdrDetections(table1.getXdrDetections() + 1);
				table1.setTotalXdr(table1.getTotalXdr() + 1);
				table1.setTotalDetections(table1.getTotalDetections() + 1);
				
				if(regList!=null) {
					rf = getLatestRegimenForPatient(tf.getPatient().getPatientId().intValue(),regList, locList, year, quarter, month);
				}
				
				if(rf!=null) {
					regimen = rf.getSldRegimenType();
					if(regimen!=null) {
						if(regimen.getConceptId().intValue()==indLzd) {
							isIndLzd = Boolean.TRUE;
						}
						
						else if(regimen.getConceptId().intValue()==indBdq) {
							isIndBdq = Boolean.TRUE;
						}
					}
				}
				
				if (newCase != null && newCase) {

					table1.setNewXdr(table1.getNewXdr() + 1);
					table1.setNewTotal(table1.getNewTotal() + 1);
					

					if (age > 0 && age <= 4) {
						table1.setNewXdr04(table1.getNewXdr04() + 1);
						table1.setTotalXdr04(table1.getTotalXdr04() + 1);
						table1.setNewTotal04(table1.getNewTotal04() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setNewIndLzdXdrPreXdr(table1.getNewIndLzdXdrPreXdr() + 1);
							table1.setNewIndLzdXdrPreXdr04(table1.getNewIndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr04(table1.getTotalIndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setNewIndBdqXdrPreXdr(table1.getNewIndBdqXdrPreXdr() + 1);
							table1.setNewIndBdqXdrPreXdr04(table1.getNewIndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr04(table1.getTotalIndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}

					else if (age >= 5 && age <= 14) {
						table1.setNewXdr0514(table1.getNewXdr0514() + 1);
						table1.setTotalXdr0514(table1.getTotalXdr0514() + 1);
						table1.setNewTotal0514(table1.getNewTotal0514() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setNewIndLzdXdrPreXdr(table1.getNewIndLzdXdrPreXdr() + 1);
							table1.setNewIndLzdXdrPreXdr0514(table1.getNewIndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr0514(table1.getTotalIndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setNewIndBdqXdrPreXdr(table1.getNewIndBdqXdrPreXdr() + 1);
							table1.setNewIndBdqXdrPreXdr0514(table1.getNewIndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr0514(table1.getTotalIndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
						
					}
					
					

					else if (age >= 15 && age <= 17) {
						table1.setNewXdr1517(table1.getNewXdr1517() + 1);
						table1.setTotalXdr1517(table1.getTotalXdr1517() + 1);
						table1.setNewTotal1517(table1.getNewTotal1517() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setNewIndLzdXdrPreXdr(table1.getNewIndLzdXdrPreXdr() + 1);
							table1.setNewIndLzdXdrPreXdr1517(table1.getNewIndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr1517(table1.getTotalIndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setNewIndBdqXdrPreXdr(table1.getNewIndBdqXdrPreXdr() + 1);
							table1.setNewIndBdqXdrPreXdr1517(table1.getNewIndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr1517(table1.getTotalIndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}

					if (hiv != null && hiv) {
						table1.setNewXdrHiv(table1.getNewXdrHiv() + 1);
						table1.setTotalXdrHiv(table1.getTotalXdrHiv() + 1);
						table1.setNewTotalHiv(table1.getNewTotalHiv() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setNewIndLzdXdrPreXdr(table1.getNewIndLzdXdrPreXdr() + 1);
							table1.setNewIndLzdXdrPreXdrHiv(table1.getNewIndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdrHiv(table1.getTotalIndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setNewIndBdqXdrPreXdr(table1.getNewIndBdqXdrPreXdr() + 1);
							table1.setNewIndBdqXdrPreXdrHiv(table1.getNewIndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdrHiv(table1.getTotalIndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}
				}

				else if (relapse1 != null && relapse1) {

					table1.setRelapse1Xdr(table1.getRelapse1Xdr() + 1);
					table1.setRelapse1Total(table1.getRelapse1Total() + 1);
					

					if (age > 0 && age <= 4) {
						table1.setRelapse1Xdr04(table1.getRelapse1Xdr04() + 1);
						table1.setTotalXdr04(table1.getTotalXdr04() + 1);
						table1.setRelapse1Total04(table1.getRelapse1Total04() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setRelapse1IndLzdXdrPreXdr(table1.getRelapse1IndLzdXdrPreXdr() + 1);
							table1.setRelapse1IndLzdXdrPreXdr04(table1.getRelapse1IndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr04(table1.getTotalIndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setRelapse1IndBdqXdrPreXdr(table1.getRelapse1IndBdqXdrPreXdr() + 1);
							table1.setRelapse1IndBdqXdrPreXdr04(table1.getRelapse1IndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr04(table1.getTotalIndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}

					else if (age >= 5 && age <= 14) {
						table1.setRelapse1Xdr0514(table1.getRelapse1Xdr0514() + 1);
						table1.setTotalXdr0514(table1.getTotalXdr0514() + 1);
						table1.setRelapse1Total0514(table1.getRelapse1Total0514() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setRelapse1IndLzdXdrPreXdr(table1.getRelapse1IndLzdXdrPreXdr() + 1);
							table1.setRelapse1IndLzdXdrPreXdr0514(table1.getRelapse1IndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr0514(table1.getTotalIndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setRelapse1IndBdqXdrPreXdr(table1.getRelapse1IndBdqXdrPreXdr() + 1);
							table1.setRelapse1IndBdqXdrPreXdr0514(table1.getRelapse1IndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr0514(table1.getTotalIndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}

					else if (age >= 15 && age <= 17) {
						table1.setRelapse1Xdr1517(table1.getRelapse1Xdr1517() + 1);
						table1.setTotalXdr1517(table1.getTotalXdr1517() + 1);
						table1.setRelapse1Total1517(table1.getRelapse1Total1517() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setRelapse1IndLzdXdrPreXdr(table1.getRelapse1IndLzdXdrPreXdr() + 1);
							table1.setRelapse1IndLzdXdrPreXdr1517(table1.getRelapse1IndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr1517(table1.getTotalIndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setRelapse1IndBdqXdrPreXdr(table1.getRelapse1IndBdqXdrPreXdr() + 1);
							table1.setRelapse1IndBdqXdrPreXdr1517(table1.getRelapse1IndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr1517(table1.getTotalIndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}

					if (hiv != null && hiv) {
						table1.setRelapse1XdrHiv(table1.getRelapse1XdrHiv() + 1);
						table1.setTotalXdrHiv(table1.getTotalXdrHiv() + 1);
						table1.setRelapse1TotalHiv(table1.getRelapse1TotalHiv() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setRelapse1IndLzdXdrPreXdr(table1.getRelapse1IndLzdXdrPreXdr() + 1);
							table1.setRelapse1IndLzdXdrPreXdrHiv(table1.getRelapse1IndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdrHiv(table1.getTotalIndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setRelapse1IndBdqXdrPreXdr(table1.getRelapse1IndBdqXdrPreXdr() + 1);
							table1.setRelapse1IndBdqXdrPreXdrHiv(table1.getRelapse1IndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdrHiv(table1.getTotalIndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}
				}

				else if (relapse2 != null && relapse2) {

					table1.setRelapse2Xdr(table1.getRelapse2Xdr() + 1);
					table1.setRelapse2Total(table1.getRelapse2Total() + 1);

					if (age > 0 && age <= 4) {
						table1.setRelapse2Xdr04(table1.getRelapse2Xdr04() + 1);
						table1.setTotalXdr04(table1.getTotalXdr04() + 1);
						table1.setRelapse2Total04(table1.getRelapse2Total04() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setRelapse2IndLzdXdrPreXdr(table1.getRelapse2IndLzdXdrPreXdr() + 1);
							table1.setRelapse2IndLzdXdrPreXdr04(table1.getRelapse2IndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr04(table1.getTotalIndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setRelapse2IndBdqXdrPreXdr(table1.getRelapse2IndBdqXdrPreXdr() + 1);
							table1.setRelapse2IndBdqXdrPreXdr04(table1.getRelapse2IndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr04(table1.getTotalIndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}

					else if (age >= 5 && age <= 14) {
						table1.setRelapse2Xdr0514(table1.getRelapse2Xdr0514() + 1);
						table1.setTotalXdr0514(table1.getTotalXdr0514() + 1);
						table1.setRelapse2Total0514(table1.getRelapse2Total0514() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setRelapse2IndLzdXdrPreXdr(table1.getRelapse2IndLzdXdrPreXdr() + 1);
							table1.setRelapse2IndLzdXdrPreXdr0514(table1.getRelapse2IndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr0514(table1.getTotalIndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setRelapse2IndBdqXdrPreXdr(table1.getRelapse2IndBdqXdrPreXdr() + 1);
							table1.setRelapse2IndBdqXdrPreXdr0514(table1.getRelapse2IndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr0514(table1.getTotalIndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}

					else if (age >= 15 && age <= 17) {
						table1.setRelapse2Xdr1517(table1.getRelapse2Xdr1517() + 1);
						table1.setTotalXdr1517(table1.getTotalXdr1517() + 1);
						table1.setRelapse2Total1517(table1.getRelapse2Total1517() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setRelapse2IndLzdXdrPreXdr(table1.getRelapse2IndLzdXdrPreXdr() + 1);
							table1.setRelapse2IndLzdXdrPreXdr1517(table1.getRelapse2IndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr1517(table1.getTotalIndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setRelapse2IndBdqXdrPreXdr(table1.getRelapse2IndBdqXdrPreXdr() + 1);
							table1.setRelapse2IndBdqXdrPreXdr1517(table1.getRelapse2IndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr1517(table1.getTotalIndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}

					if (hiv != null && hiv) {
						table1.setRelapse2XdrHiv(table1.getRelapse2XdrHiv() + 1);
						table1.setTotalXdrHiv(table1.getTotalXdrHiv() + 1);
						table1.setRelapse2TotalHiv(table1.getRelapse2TotalHiv() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setRelapse2IndLzdXdrPreXdr(table1.getRelapse2IndLzdXdrPreXdr() + 1);
							table1.setRelapse2IndLzdXdrPreXdrHiv(table1.getRelapse2IndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdrHiv(table1.getTotalIndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setRelapse2IndBdqXdrPreXdr(table1.getRelapse2IndBdqXdrPreXdr() + 1);
							table1.setRelapse2IndBdqXdrPreXdrHiv(table1.getRelapse2IndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdrHiv(table1.getTotalIndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}
				}

				else if (default1 != null && default1) {

					table1.setDefault1Xdr(table1.getDefault1Xdr() + 1);
					table1.setDefault1Total(table1.getDefault1Total() + 1);

					if (age > 0 && age <= 4) {
						table1.setDefault1Xdr04(table1.getDefault1Xdr04() + 1);
						table1.setTotalXdr04(table1.getTotalXdr04() + 1);
						table1.setDefault1Total04(table1.getDefault1Total04() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setDefault1IndLzdXdrPreXdr(table1.getDefault1IndLzdXdrPreXdr() + 1);
							table1.setDefault1IndLzdXdrPreXdr04(table1.getDefault1IndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr04(table1.getTotalIndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setDefault1IndBdqXdrPreXdr(table1.getDefault1IndBdqXdrPreXdr() + 1);
							table1.setDefault1IndBdqXdrPreXdr04(table1.getDefault1IndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr04(table1.getTotalIndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}

					else if (age >= 5 && age <= 14) {
						table1.setDefault1Xdr0514(table1.getDefault1Xdr0514() + 1);
						table1.setTotalXdr0514(table1.getTotalXdr0514() + 1);
						table1.setDefault1Total0514(table1.getDefault1Total0514() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setDefault1IndLzdXdrPreXdr(table1.getDefault1IndLzdXdrPreXdr() + 1);
							table1.setDefault1IndLzdXdrPreXdr0514(table1.getDefault1IndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr0514(table1.getTotalIndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setDefault1IndBdqXdrPreXdr(table1.getDefault1IndBdqXdrPreXdr() + 1);
							table1.setDefault1IndBdqXdrPreXdr0514(table1.getDefault1IndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr0514(table1.getTotalIndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}

					else if (age >= 15 && age <= 17) {
						table1.setDefault1Xdr1517(table1.getDefault1Xdr1517() + 1);
						table1.setTotalXdr1517(table1.getTotalXdr1517() + 1);
						table1.setDefault1Total1517(table1.getDefault1Total1517() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setDefault1IndLzdXdrPreXdr(table1.getDefault1IndLzdXdrPreXdr() + 1);
							table1.setDefault1IndLzdXdrPreXdr1517(table1.getDefault1IndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr1517(table1.getTotalIndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setDefault1IndBdqXdrPreXdr(table1.getDefault1IndBdqXdrPreXdr() + 1);
							table1.setDefault1IndBdqXdrPreXdr1517(table1.getDefault1IndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr1517(table1.getTotalIndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}

					if (hiv != null && hiv) {
						table1.setDefault1XdrHiv(table1.getDefault1XdrHiv() + 1);
						table1.setTotalXdrHiv(table1.getTotalXdrHiv() + 1);
						table1.setDefault1TotalHiv(table1.getDefault1TotalHiv() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setDefault1IndLzdXdrPreXdr(table1.getDefault1IndLzdXdrPreXdr() + 1);
							table1.setDefault1IndLzdXdrPreXdrHiv(table1.getDefault1IndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdrHiv(table1.getTotalIndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setDefault1IndBdqXdrPreXdr(table1.getDefault1IndBdqXdrPreXdr() + 1);
							table1.setDefault1IndBdqXdrPreXdrHiv(table1.getDefault1IndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdrHiv(table1.getTotalIndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}
				}

				else if (default2 != null && default2) {

					table1.setDefault2Xdr(table1.getDefault2Xdr() + 1);
					table1.setDefault2Total(table1.getDefault2Total() + 1);


					if (age > 0 && age <= 4) {
						table1.setDefault2Xdr04(table1.getDefault2Xdr04() + 1);
						table1.setTotalXdr04(table1.getTotalXdr04() + 1);
						table1.setDefault2Total04(table1.getDefault2Total04() + 1);


						if(isIndLzd!=null && isIndLzd) {
							table1.setDefault2IndLzdXdrPreXdr(table1.getDefault2IndLzdXdrPreXdr() + 1);
							table1.setDefault2IndLzdXdrPreXdr04(table1.getDefault2IndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr04(table1.getTotalIndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setDefault2IndBdqXdrPreXdr(table1.getDefault2IndBdqXdrPreXdr() + 1);
							table1.setDefault2IndBdqXdrPreXdr04(table1.getDefault2IndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr04(table1.getTotalIndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}

					else if (age >= 5 && age <= 14) {
						table1.setDefault2Xdr0514(table1.getDefault2Xdr0514() + 1);
						table1.setTotalXdr0514(table1.getTotalXdr0514() + 1);
						table1.setDefault2Total0514(table1.getDefault2Total0514() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setDefault2IndLzdXdrPreXdr(table1.getDefault2IndLzdXdrPreXdr() + 1);
							table1.setDefault2IndLzdXdrPreXdr0514(table1.getDefault2IndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr0514(table1.getTotalIndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setDefault2IndBdqXdrPreXdr(table1.getDefault2IndBdqXdrPreXdr() + 1);
							table1.setDefault2IndBdqXdrPreXdr0514(table1.getDefault2IndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr0514(table1.getTotalIndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}

					else if (age >= 15 && age <= 17) {
						table1.setDefault2Xdr1517(table1.getDefault2Xdr1517() + 1);
						table1.setTotalXdr1517(table1.getTotalXdr1517() + 1);
						table1.setDefault2Total1517(table1.getDefault2Total1517() + 1);
						

						if(isIndLzd!=null && isIndLzd) {
							table1.setDefault2IndLzdXdrPreXdr(table1.getDefault2IndLzdXdrPreXdr() + 1);
							table1.setDefault2IndLzdXdrPreXdr1517(table1.getDefault2IndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr1517(table1.getTotalIndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setDefault2IndBdqXdrPreXdr(table1.getDefault2IndBdqXdrPreXdr() + 1);
							table1.setDefault2IndBdqXdrPreXdr1517(table1.getDefault2IndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr1517(table1.getTotalIndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}

					if (hiv != null && hiv) {
						table1.setDefault2XdrHiv(table1.getDefault2XdrHiv() + 1);
						table1.setTotalXdrHiv(table1.getTotalXdrHiv() + 1);
						table1.setDefault2TotalHiv(table1.getDefault2TotalHiv() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setDefault2IndLzdXdrPreXdr(table1.getDefault2IndLzdXdrPreXdr() + 1);
							table1.setDefault2IndLzdXdrPreXdrHiv(table1.getDefault2IndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdrHiv(table1.getTotalIndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setDefault2IndBdqXdrPreXdr(table1.getDefault2IndBdqXdrPreXdr() + 1);
							table1.setDefault2IndBdqXdrPreXdrHiv(table1.getDefault2IndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdrHiv(table1.getTotalIndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}
				}
				
				else if (failure1 != null && failure1) {

					table1.setFailure1Xdr(table1.getFailure1Xdr() + 1);
					table1.setFailure1Total(table1.getFailure1Total() + 1);


					if (age > 0 && age <= 4) {
						table1.setFailure1Xdr04(table1.getFailure1Xdr04() + 1);
						table1.setTotalXdr04(table1.getTotalXdr04() + 1);
						table1.setFailure1Total04(table1.getFailure1Total04() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setFailure1IndLzdXdrPreXdr(table1.getFailure1IndLzdXdrPreXdr() + 1);
							table1.setFailure1IndLzdXdrPreXdr04(table1.getFailure1IndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr04(table1.getTotalIndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setFailure1IndBdqXdrPreXdr(table1.getFailure1IndBdqXdrPreXdr() + 1);
							table1.setFailure1IndBdqXdrPreXdr04(table1.getFailure1IndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr04(table1.getTotalIndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}

					else if (age >= 5 && age <= 14) {
						table1.setFailure1Xdr0514(table1.getFailure1Xdr0514() + 1);
						table1.setTotalXdr0514(table1.getTotalXdr0514() + 1);
						table1.setFailure1Total0514(table1.getFailure1Total0514() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setFailure1IndLzdXdrPreXdr(table1.getFailure1IndLzdXdrPreXdr() + 1);
							table1.setFailure1IndLzdXdrPreXdr0514(table1.getFailure1IndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr0514(table1.getTotalIndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setFailure1IndBdqXdrPreXdr(table1.getFailure1IndBdqXdrPreXdr() + 1);
							table1.setFailure1IndBdqXdrPreXdr0514(table1.getFailure1IndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr0514(table1.getTotalIndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}

					else if (age >= 15 && age <= 17) {
						table1.setFailure1Xdr1517(table1.getFailure1Xdr1517() + 1);
						table1.setTotalXdr1517(table1.getTotalXdr1517() + 1);
						table1.setFailure1Total1517(table1.getFailure1Total1517() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setFailure1IndLzdXdrPreXdr(table1.getFailure1IndLzdXdrPreXdr() + 1);
							table1.setFailure1IndLzdXdrPreXdr1517(table1.getFailure1IndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr1517(table1.getTotalIndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setFailure1IndBdqXdrPreXdr(table1.getFailure1IndBdqXdrPreXdr() + 1);
							table1.setFailure1IndBdqXdrPreXdr1517(table1.getFailure1IndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr1517(table1.getTotalIndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}

					if (hiv != null && hiv) {
						table1.setFailure1XdrHiv(table1.getFailure1XdrHiv() + 1);
						table1.setTotalXdrHiv(table1.getTotalXdrHiv() + 1);
						table1.setFailure1TotalHiv(table1.getFailure1TotalHiv() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setFailure1IndLzdXdrPreXdr(table1.getFailure1IndLzdXdrPreXdr() + 1);
							table1.setFailure1IndLzdXdrPreXdrHiv(table1.getFailure1IndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdrHiv(table1.getTotalIndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setFailure1IndBdqXdrPreXdr(table1.getFailure1IndBdqXdrPreXdr() + 1);
							table1.setFailure1IndBdqXdrPreXdrHiv(table1.getFailure1IndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdrHiv(table1.getTotalIndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}
				}

				else if (failure2 != null && failure2) {

					table1.setFailure2Xdr(table1.getFailure2Xdr() + 1);
					table1.setFailure2Total(table1.getFailure2Total() + 1);

					if (age > 0 && age <= 4) {
						table1.setFailure2Xdr04(table1.getFailure2Xdr04() + 1);
						table1.setTotalXdr04(table1.getTotalXdr04() + 1);
						table1.setFailure2Total04(table1.getFailure2Total04() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setFailure2IndLzdXdrPreXdr(table1.getFailure2IndLzdXdrPreXdr() + 1);
							table1.setFailure2IndLzdXdrPreXdr04(table1.getFailure2IndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr04(table1.getTotalIndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setFailure2IndBdqXdrPreXdr(table1.getFailure2IndBdqXdrPreXdr() + 1);
							table1.setFailure2IndBdqXdrPreXdr04(table1.getFailure2IndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr04(table1.getTotalIndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}

					else if (age >= 5 && age <= 14) {
						table1.setFailure2Xdr0514(table1.getFailure2Xdr0514() + 1);
						table1.setTotalXdr0514(table1.getTotalXdr0514() + 1);
						table1.setFailure2Total0514(table1.getFailure2Total0514() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setFailure2IndLzdXdrPreXdr(table1.getFailure2IndLzdXdrPreXdr() + 1);
							table1.setFailure2IndLzdXdrPreXdr0514(table1.getFailure2IndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr0514(table1.getTotalIndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setFailure2IndBdqXdrPreXdr(table1.getFailure2IndBdqXdrPreXdr() + 1);
							table1.setFailure2IndBdqXdrPreXdr0514(table1.getFailure2IndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr0514(table1.getTotalIndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}

					else if (age >= 15 && age <= 17) {
						table1.setFailure2Xdr1517(table1.getFailure2Xdr1517() + 1);
						table1.setTotalXdr1517(table1.getTotalXdr1517() + 1);
						table1.setFailure2Total1517(table1.getFailure2Total1517() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setFailure2IndLzdXdrPreXdr(table1.getFailure2IndLzdXdrPreXdr() + 1);
							table1.setFailure2IndLzdXdrPreXdr1517(table1.getFailure2IndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr1517(table1.getTotalIndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setFailure2IndBdqXdrPreXdr(table1.getFailure2IndBdqXdrPreXdr() + 1);
							table1.setFailure2IndBdqXdrPreXdr1517(table1.getFailure2IndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr1517(table1.getTotalIndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}

					if (hiv != null && hiv) {
						table1.setFailure2XdrHiv(table1.getFailure2XdrHiv() + 1);
						table1.setTotalXdrHiv(table1.getTotalXdrHiv() + 1);
						table1.setFailure2TotalHiv(table1.getFailure2TotalHiv() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setFailure2IndLzdXdrPreXdr(table1.getFailure2IndLzdXdrPreXdr() + 1);
							table1.setFailure2IndLzdXdrPreXdrHiv(table1.getFailure2IndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdrHiv(table1.getTotalIndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setFailure2IndBdqXdrPreXdr(table1.getFailure2IndBdqXdrPreXdr() + 1);
							table1.setFailure2IndBdqXdrPreXdrHiv(table1.getFailure2IndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdrHiv(table1.getTotalIndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}
				}
				
				else if (other != null && other) {

					table1.setOtherXdr(table1.getOtherXdr() + 1);
					table1.setOtherTotal(table1.getOtherTotal() + 1);

					if (age > 0 && age <= 4) {
						table1.setOtherXdr04(table1.getOtherXdr04() + 1);
						table1.setTotalXdr04(table1.getTotalXdr04() + 1);
						table1.setOtherTotal04(table1.getOtherTotal04() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setOtherIndLzdXdrPreXdr(table1.getOtherIndLzdXdrPreXdr() + 1);
							table1.setOtherIndLzdXdrPreXdr04(table1.getOtherIndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr04(table1.getTotalIndLzdXdrPreXdr04() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setOtherIndBdqXdrPreXdr(table1.getOtherIndBdqXdrPreXdr() + 1);
							table1.setOtherIndBdqXdrPreXdr04(table1.getOtherIndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr04(table1.getTotalIndBdqXdrPreXdr04() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}

					}

					else if (age >= 5 && age <= 14) {
						table1.setOtherXdr0514(table1.getOtherXdr0514() + 1);
						table1.setTotalXdr0514(table1.getTotalXdr0514() + 1);
						table1.setOtherTotal0514(table1.getOtherTotal0514() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setOtherIndLzdXdrPreXdr(table1.getOtherIndLzdXdrPreXdr() + 1);
							table1.setOtherIndLzdXdrPreXdr0514(table1.getOtherIndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr0514(table1.getTotalIndLzdXdrPreXdr0514() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setOtherIndBdqXdrPreXdr(table1.getOtherIndBdqXdrPreXdr() + 1);
							table1.setOtherIndBdqXdrPreXdr0514(table1.getOtherIndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr0514(table1.getTotalIndBdqXdrPreXdr0514() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}

					else if (age >= 15 && age <= 17) {
						table1.setOtherXdr1517(table1.getOtherXdr1517() + 1);
						table1.setTotalXdr1517(table1.getTotalXdr1517() + 1);
						table1.setOtherTotal1517(table1.getOtherTotal1517() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setOtherIndLzdXdrPreXdr(table1.getOtherIndLzdXdrPreXdr() + 1);
							table1.setOtherIndLzdXdrPreXdr1517(table1.getOtherIndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr1517(table1.getTotalIndLzdXdrPreXdr1517() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setOtherIndBdqXdrPreXdr(table1.getOtherIndBdqXdrPreXdr() + 1);
							table1.setOtherIndBdqXdrPreXdr1517(table1.getOtherIndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr1517(table1.getTotalIndBdqXdrPreXdr1517() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}

					if (hiv != null && hiv) {
						table1.setOtherXdrHiv(table1.getOtherXdrHiv() + 1);
						table1.setTotalXdrHiv(table1.getTotalXdrHiv() + 1);
						table1.setOtherTotalHiv(table1.getOtherTotalHiv() + 1);
						
						if(isIndLzd!=null && isIndLzd) {
							table1.setOtherIndLzdXdrPreXdr(table1.getOtherIndLzdXdrPreXdr() + 1);
							table1.setOtherIndLzdXdrPreXdrHiv(table1.getOtherIndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdrHiv(table1.getTotalIndLzdXdrPreXdrHiv() + 1);
							table1.setTotalIndLzdXdrPreXdr(table1.getTotalIndLzdXdrPreXdr() + 1);
							
						}
						
						else if(isIndBdq!=null && isIndBdq) {
							table1.setOtherIndBdqXdrPreXdr(table1.getOtherIndBdqXdrPreXdr() + 1);
							table1.setOtherIndBdqXdrPreXdrHiv(table1.getOtherIndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdrHiv(table1.getTotalIndBdqXdrPreXdrHiv() + 1);
							table1.setTotalIndBdqXdrPreXdr(table1.getTotalIndBdqXdrPreXdr() + 1);
						}
					}
				}
			}

			else
				continue;

			/*regimen = tf.getPatientCategory();

			if (regimen == null)
				continue;

			int regId = regimen.getConceptId().intValue();*/

		}

		// TO CHECK WHETHER REPORT IS CLOSED OR NOT
		Integer report_oblast = null;
		Integer report_quarter = null;
		Integer report_month = null;
		/*
		 * if(new PDFHelper().isInt(oblast)) { report_oblast =
		 * Integer.parseInt(oblast); } if(new PDFHelper().isInt(quarter)) {
		 * report_quarter = Integer.parseInt(quarter); } if(new
		 * PDFHelper().isInt(month)) { report_month = Integer.parseInt(month); }
		 */
		model.addAttribute("table1", table1);
		boolean reportStatus = Context.getService(MdrtbService.class)
				.readReportStatus(oblastId, districtId, facilityId, year,
						quarter, month, "TB-08u", "MDRTB");
		System.out.println(reportStatus);

		String oName = null;
		String dName = null;
		String fName = null;

		if (oblastId != null) {
			Oblast o = Context.getService(MdrtbService.class).getOblast(
					oblastId);
			if (o != null) {
				oName = o.getName();
			}
		}

		if (districtId != null) {
			District d = Context.getService(MdrtbService.class).getDistrict(
					districtId);
			if (d != null) {
				dName = d.getName();
			}
		}

		if (facilityId != null) {
			Facility f = Context.getService(MdrtbService.class).getFacility(
					facilityId);
			if (f != null) {
				fName = f.getName();
			}
		}

		model.addAttribute("oblast", oblastId);
		model.addAttribute("facility", facilityId);
		model.addAttribute("district", districtId);
		model.addAttribute("year", year);
		if (month != null && month.length() != 0)
			model.addAttribute("month", month.replace("\"", ""));
		else
			model.addAttribute("month", "");

		if (quarter != null && quarter.length() != 0)
			model.addAttribute("quarter", quarter.replace("\"", ""));
		else
			model.addAttribute("quarter", "");

		model.addAttribute("oName", oName);
		model.addAttribute("dName", dName);
		model.addAttribute("fName", fName);
		model.addAttribute("reportDate", rdateSDF.format(new Date()));
		model.addAttribute("reportStatus", reportStatus);
		return "/module/mdrtb/reporting/tb07uResults";
		// + "_" + Context.getLocale().toString().substring(0, 2);

	}
	
	private static RegimenForm getLatestRegimenForPatient(int patientId, ArrayList<RegimenForm> forms, ArrayList<Location>locList, Integer year, String quarter, String month) {
		RegimenForm latest = null;
		Date latDate = null;
		
		for(RegimenForm rf : forms) {
			if(rf.getPatient().getPatientId().intValue()==patientId) {
				if(latDate==null) {
					latDate = rf.getEncounterDatetime();
					latest = rf;
				}
				
				else if(rf.getEncounterDatetime().after(latDate)) {
					latDate = rf.getEncounterDatetime();
					latest = rf;
				}
			}
		}
		
		Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
		
		//Date startDate = (Date)(dateMap.get("startDate"));
		Date endDate = (Date)(dateMap.get("endDate"));
		
		if(latest == null) {
			Patient p = Context.getPatientService().getPatient(new Integer(patientId));
			RegimenForm previous = Context.getService(MdrtbService.class).getPreviousRegimenFormForPatient(p, locList, endDate);
			if(previous!=null) {
				latest = previous;
			}
		}
		
		return latest;
	}

}
