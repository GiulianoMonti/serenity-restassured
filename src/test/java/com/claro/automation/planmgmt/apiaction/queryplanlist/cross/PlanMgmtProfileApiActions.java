package com.claro.automation.planmgmt.apiaction.queryplanlist.cross;

import com.claro.automation.planmgmt.dataaccess.model.CellularProfile;
import com.claro.automation.planmgmt.dataaccess.model.QueryPlanListProfile;
import com.claro.automation.planmgmt.facade.GetCellularProfile;
import com.claro.automation.planmgmt.facade.GetQueryPlanListProfile;
import io.cucumber.java.en.Given;
import net.serenitybdd.core.steps.UIInteractions;

public class PlanMgmtProfileApiActions extends UIInteractions {

    private final GetQueryPlanListProfile getQueryPlanListProfile;
    private final GetCellularProfile getChangePlanProfile;

    public PlanMgmtProfileApiActions() {
        this.getQueryPlanListProfile = new GetQueryPlanListProfile();
        this.getChangePlanProfile = new GetCellularProfile();

    }

    @Given("un perfil con planes activos para General Motors")
    public QueryPlanListProfile givenGetMsisdnActiveLine() {
        return getQueryPlanListProfile.activeProfile("GM");
    }

    @Given("un perfil con planes activos para Samsung")
    public QueryPlanListProfile givenGetMsisdnActiveLineSamsung() {
        return getQueryPlanListProfile.activeProfile("SAM");
    }
    @Given("un perfil con planes activos para Apple")
    public QueryPlanListProfile givenGetMsisdnActiveLineApple() {
        return getQueryPlanListProfile.activeProfile("APP");
    }

    @Given("un perfil con planes activos GM")
    public CellularProfile givenGetMsisdnActiveLineChangePlan() {
        return getChangePlanProfile.activeProfile("GM");
    }

    @Given("un perfil con planes activos GM para agregar restriccion")
    public CellularProfile givenGetMsisdnActiveLineRestriction() {
        return getChangePlanProfile.activeProfile("RES");
    }

    @Given("un perfil con planes activos GM para agregar restriccion")
    public CellularProfile givenGetMsisdnActiveLineRemoveRestriction() {
        return getChangePlanProfile.activeProfile("REMRES");
    }
}