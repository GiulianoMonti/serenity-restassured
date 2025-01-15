package com.claro.automation.planmgmt.dto.elasticsearch;

import com.claro.automation.dbloggerelastic.elasticsearch.dto.DocumentBase;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentQueryPlanList extends DocumentBase {

    @JsonProperty("ProviderId")
    private String providerId;

    @JsonProperty("RequestHub")
    private String requestHub;

    @JsonProperty("MSISDN")
    private String msisdn;

    @JsonProperty("type")
    private String type;

    @JsonProperty("AspCallbackUrl")
    private String aspCallbackUrl;

    @JsonProperty("Metodo")
    private String method;

    @JsonProperty("Fecha")
    private String date;

    @JsonProperty("@version")
    private String version;

    @JsonProperty("HubIoTError")
    private String hubIoTError;

    @JsonProperty("IMSI")
    private String imsi;

    @JsonProperty("Descripcion")
    private String description;

    @JsonProperty("planId")
    private String planId;

    @JsonProperty("es_index")
    private String esIndex;

    @JsonProperty("ResponseHub")
    private String responseHub;

    @JsonProperty("Pais")
    private String country;

    @JsonProperty("ICCID")
    private String iccid;

    @JsonProperty("EID")
    private String eid;

    @JsonProperty("es_pipeline")
    private String esPipeline;

    @JsonProperty("NivelLog")
    private String logLevel;

    @JsonProperty("Request")
    private String request;

    @JsonProperty("IMEI")
    private String imei;

    @JsonProperty("message")
    private String message;

    @JsonProperty("Transaccion")
    private String transaction;

    @JsonProperty("CorrelatorId")
    private String correlatorId;

    @JsonProperty("Resultado")
    private String result;

    @JsonProperty("Tiempo")
    private int elapsed;

    @JsonProperty("@timestamp")
    private String timestamp;

    @JsonProperty("Tipo")
    private String typeMessage;

    @JsonProperty("Enterprise")
    private String enterprise;

    @JsonProperty("Servidor")
    private String server;

    @JsonProperty("fields")
    private Fields fields;

    @JsonProperty("DescripcionError")
    private String errorDescription;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Fields {
        @JsonProperty("app")
        private String app;

        @JsonProperty("host")
        private String host;
    }
}
