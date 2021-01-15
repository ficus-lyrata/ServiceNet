package org.benetech.servicenet.service;

import org.benetech.servicenet.domain.DataImportReport;
import org.benetech.servicenet.domain.Organization;

public interface OrganizationImportService {

    Organization createOrUpdateOrganization(Organization filledOrganization, String externalDbId, String providerName,
                                            DataImportReport report, boolean overwriteLastUpdated);
}
