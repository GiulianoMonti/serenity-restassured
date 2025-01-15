package com.claro.automation.planmgmt.facade;

import com.claro.automation.dbloggerelastic.dataaccess.dao.IotLogDao;
import com.claro.automation.dbloggerelastic.dataaccess.dao.impl.IotLogDaoImpl;
import com.claro.automation.dbloggerelastic.dataaccess.model.IotLog;
import com.claro.automation.planmgmt.dataaccess.dao.CellularPlansDao;
import com.claro.automation.planmgmt.dataaccess.dao.ChangeStatusDao;
import com.claro.automation.planmgmt.dataaccess.dao.PlanListDao;
import com.claro.automation.planmgmt.dataaccess.dao.impl.CellularPlansDaoImpl;
import com.claro.automation.planmgmt.dataaccess.dao.impl.ChangeStatusDaoImpl;
import com.claro.automation.planmgmt.dataaccess.dao.impl.PlanListDaoImpl;
import com.claro.automation.planmgmt.dataaccess.model.CellularPlans;
import com.claro.automation.planmgmt.dataaccess.model.Plan;
import com.claro.automation.planmgmt.exception.assertion.NoDataFoundAssertionError;
import com.claro.automation.planmgmt.util.SerenityEnvironmentVariables;
import com.claro.automation.planmgmt.util.VariableByCountry;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DataBaseApi {

    private final IotLogDao iotLogDao;
    private final PlanListDao planListDao;
    private final CellularPlansDao cellularPlansDao;
    private final ChangeStatusDao changeStatusDao;

    public DataBaseApi() {
        this.iotLogDao = new IotLogDaoImpl(SerenityEnvironmentVariables.getDataBaseCountry());
        this.planListDao = new PlanListDaoImpl();
        this.cellularPlansDao = new CellularPlansDaoImpl();
        this.changeStatusDao = new ChangeStatusDaoImpl();
    }

    public List<Plan> selectPlanList(String providerId, String enterpriseId) {
        List<Plan> planslist = planListDao.selectPlanList(providerId, enterpriseId);
        if (planslist.isEmpty())
            throw new NoDataFoundAssertionError("no with record found with value: " + providerId);
        return planslist;
    }


    public IotLog getLogWithProviderIdAndCorrelator(String serviceName, String providerId, String correlatorId) throws InterruptedException {

       Thread.sleep(11000);
        List<IotLog> iotLogs = iotLogDao.selectByServiceNameAndMsisdnAndProviderAndCorrelatorGreaterThanDate(serviceName, null, providerId, correlatorId, VariableByCountry.getLocalTime().minusMinutes(5));

        if (iotLogs.isEmpty())
            throw new NoDataFoundAssertionError("no with record found with value: "
                    + "provider= " + providerId + ", correlatorId= " + correlatorId + ", serviceName= " + serviceName);

        return iotLogs.stream().max(Comparator.comparing(IotLog::systemDate)).orElseThrow();
    }


    public Optional<CellularPlans> selectCellularPlansByMsisdn(String msisdn) {
        return cellularPlansDao.selectCellularPlans(msisdn).stream()
                .max(Comparator.comparing(CellularPlans::endDate));
    }

    public void deleteFromChangeStatus(String cellularNumber) {
        changeStatusDao.deleteFromChangeStatus(cellularNumber);
    }


}