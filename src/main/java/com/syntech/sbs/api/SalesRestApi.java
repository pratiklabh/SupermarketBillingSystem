package com.syntech.sbs.api;

import com.syntech.sbs.model.ClientLayoutPreferences;
import com.syntech.sbs.model.Sales;
import com.syntech.sbs.model.SalesDetails;
import com.syntech.sbs.model.Template;
import com.syntech.sbs.repository.ClientLayoutPreferencesRepository;
import com.syntech.sbs.repository.SalesDetailsRepository;
import com.syntech.sbs.repository.SalesRepository;
import com.syntech.sbs.repository.TemplateRepository;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.util.List;
import javax.json.JsonArray;

@Path("/sales")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SalesRestApi {

    @Inject
    private SalesRepository salesRepository;

    @Inject
    private SalesDetailsRepository salesDetailsRepository;

    @Inject
    private ClientLayoutPreferencesRepository clientLayoutPreferencesRepository;

    @Inject
    private TemplateRepository templateRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSales() {
        try {
            List<Sales> salesList = salesRepository.findAll(); // Fetch all sales records
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

            // Check if the salesList is empty
            if (salesList.isEmpty()) {
                return RestResponse.responseBuilder("false", "404", "No sales found", null);
            }

            // Iterate over the list of sales and build JSON objects
            for (Sales sale : salesList) {
                JsonObject jsonSale = Json.createObjectBuilder()
                        .add("id", sale.getId())
                        .add("customerName", sale.getCustomer().getName()) // Add customer name
                        .add("date", sale.getDate().toString()) // Adjust date format as needed
                        .add("total", sale.getTotal()) // Add total
                        .add("paymentMode", sale.getPaymentMode())
                        .build();
                jsonArrayBuilder.add(jsonSale); // Add each sale to the array
            }

            JsonArray jsonResult = jsonArrayBuilder.build();
            return RestResponse.responseBuilder("true", "200", "Sales found", jsonResult);
        } catch (Exception e) {
            return RestResponse.responseBuilder("false", "500", "An error occurred", Json.createObjectBuilder().add("error", e.getMessage()).build());
        }
    }

    @GET
    @Path("/salesdetails/{salesId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSalesWithDetailsById(@PathParam("salesId") Long salesId) {
        try {
            // Fetch the sales record
            Sales sale = salesRepository.findById(salesId);

            // Fetch the sales details for the given salesId
            List<SalesDetails> detailsList = salesDetailsRepository.findBySalesId(salesId);

            if (sale != null) {
                JsonObjectBuilder saleObjectBuilder = Json.createObjectBuilder()
                        .add("id", sale.getId())
                        .add("customerName", sale.getCustomer().getName())
                        .add("date", sale.getDate().toString())
                        .add("total", sale.getTotal())
                        .add("paymentMode", sale.getPaymentMode());

                // Build sales details array
                JsonArrayBuilder detailsArrayBuilder = Json.createArrayBuilder();

                for (SalesDetails detail : detailsList) {
                    JsonObjectBuilder detailObjectBuilder = Json.createObjectBuilder()
                            .add("productName", detail.getProduct().getName())
                            .add("rate", detail.getRate())
                            .add("quantity", detail.getQuantity())
                            .add("discount", detail.getDiscount());

                    detailsArrayBuilder.add(detailObjectBuilder);
                }

                // Add sales details to the sales JSON object
                saleObjectBuilder.add("details", detailsArrayBuilder);

                // Build final combined response
                JsonObject jsonResponse = saleObjectBuilder.build();

                return RestResponse.responseBuilder("true", "200", "Sales and details retrieved successfully", jsonResponse);
            } else {
                return RestResponse.responseBuilder("false", "404", "Sales not found", null);
            }
        } catch (Exception e) {
            return RestResponse.responseBuilder("false", "500", "An error occurred", Json.createObjectBuilder().add("error", e.getMessage()).build());
        }
    }

    @GET
    @Path("/{id}")
    public Response getSalesById(@PathParam("id") Long id) {
        try {
            Sales sale = salesRepository.findById(id);
            if (sale != null) {
                JsonObject jsonResult = Json.createObjectBuilder()
                        .add("id", sale.getId())
                        .add("customerName", sale.getCustomer().getName()) // Add customer name
                        .add("date", sale.getDate().toString()) // Adjust date format as needed
                        .add("total", sale.getTotal()) // Add total
                        .add("paymentMode", sale.getPaymentMode())
                        .build();
                return RestResponse.responseBuilder("true", "200", "Sales found", jsonResult);
            } else {
                return RestResponse.responseBuilder("false", "404", "Sales not found", null);
            }
        } catch (Exception e) {
            return RestResponse.responseBuilder("false", "500", "An error occurred", Json.createObjectBuilder().add("error", e.getMessage()).build());
        }
    }

    
    @GET
    @Path("/details/{salesId}")
    public Response getSalesDetailsBySalesId(@PathParam("salesId") Long salesId) {
        try {
            // Fetch the sales details for the given salesId
            List<SalesDetails> detailsList = salesDetailsRepository.findBySalesId(salesId);

            if (detailsList != null && !detailsList.isEmpty()) {
                JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

                for (SalesDetails detail : detailsList) {
                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
                            .add("productName", detail.getProduct().getName()) // Add product name
                            .add("rate", detail.getRate()) // Add rate
                            .add("quantity", detail.getQuantity()) // Add quantity
                            .add("discount", detail.getDiscount()) // Add discount
                            .add("salesId", detail.getSales().getId()); // Add foreign key to sales

                    arrayBuilder.add(objectBuilder);
                }

                return RestResponse.responseBuilder("true", "200", "Sales details retrieved successfully", arrayBuilder.build());
            } else {
                return RestResponse.responseBuilder("false", "404", "Sales details not found", null);
            }
        } catch (Exception e) {
            return RestResponse.responseBuilder("false", "500", "An error occurred", Json.createObjectBuilder().add("error", e.getMessage()).build());
        }
    }

    @GET
    @Path("/preferences")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClientLayoutPreferencess() {
        try {
            List<ClientLayoutPreferences> preferencesList = clientLayoutPreferencesRepository.findAll(); // Fetch all client layout preferences
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

            // Check if the preferencesList is empty
            if (preferencesList.isEmpty()) {
                return RestResponse.responseBuilder("false", "404", "No layout preferences found", null);
            }

            // Iterate over the list of client layout preferences and build JSON objects
            for (ClientLayoutPreferences preference : preferencesList) {
                JsonObject jsonPreference = Json.createObjectBuilder()
                        .add("client_id", preference.getId())
                        .add("client_name", preference.getClientName())
                        .add("company_name_align", preference.getCompanyNameAlign())
                        .add("address_align", preference.getAddressAlign())
                        .add("date_align", preference.getDateAlign())
                        .add("customer_name_align", preference.getCustomerNameAlign())
                        .add("sales_invoice_align", preference.getSalesInvoiceAlign())
                        .add("table_align", preference.getTableAlign())
                        .add("grand_total_align", preference.getGrandTotalAlign())
                        .build();
                jsonArrayBuilder.add(jsonPreference); // Add each preference to the array
            }

            JsonArray jsonResult = jsonArrayBuilder.build();
            return RestResponse.responseBuilder("true", "200", "Layout preferences found", jsonResult);
        } catch (Exception e) {
            return RestResponse.responseBuilder("false", "500", "An error occurred", Json.createObjectBuilder().add("error", e.getMessage()).build());
        }
    }

    @GET
    @Path("/preferences/{clientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientLayoutPreferencesById(@PathParam("clientId") Long clientId) {
        try {
            ClientLayoutPreferences preference = clientLayoutPreferencesRepository.findById(clientId);
            if (preference != null) {
                JsonObject jsonResult = Json.createObjectBuilder()
                        .add("client_id", preference.getId())
                        .add("client_name", preference.getClientName())
                        .add("company_name_align", preference.getCompanyNameAlign())
                        .add("address_align", preference.getAddressAlign())
                        .add("date_align", preference.getDateAlign())
                        .add("customer_name_align", preference.getCustomerNameAlign())
                        .add("sales_invoice_align", preference.getSalesInvoiceAlign())
                        .add("table_align", preference.getTableAlign())
                        .add("grand_total_align", preference.getGrandTotalAlign())
                        .build();
                return RestResponse.responseBuilder("true", "200", "Layout preference found", jsonResult);
            } else {
                return RestResponse.responseBuilder("false", "404", "Layout preference not found", null);
            }
        } catch (Exception e) {
            return RestResponse.responseBuilder("false", "500", "An error occurred", Json.createObjectBuilder().add("error", e.getMessage()).build());
        }
    }

    @GET
    @Path("/template")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTemplate() {
        try {
            List<Template> templateList = templateRepository.findAll(); // Fetch all client layout preferences
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

            // Check if the preferencesList is empty
            if (templateList.isEmpty()) {
                return RestResponse.responseBuilder("false", "404", "No template found", null);
            }

            // Iterate over the list of client layout preferences and build JSON objects
            for (Template template : templateList) {
                JsonObject jsonTemplate = Json.createObjectBuilder()
                        .add("template", template.getTemplate())
                        .build();
                jsonArrayBuilder.add(jsonTemplate); // Add each preference to the array
            }

            JsonArray jsonResult = jsonArrayBuilder.build();
            return RestResponse.responseBuilder("true", "200", "Template found", jsonResult);
        } catch (Exception e) {
            return RestResponse.responseBuilder("false", "500", "An error occurred", Json.createObjectBuilder().add("error", e.getMessage()).build());
        }
    }

    @GET
    @Path("/template/{templateId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTemplateById(@PathParam("templateId") Long templateId) {
        try {
            Template template = templateRepository.findById(templateId);
            if (template != null) {
                JsonObject jsonResult = Json.createObjectBuilder()
                        .add("template", template.getTemplate())
                        .build();
                return RestResponse.responseBuilder("true", "200", "Template found", jsonResult);
            } else {
                return RestResponse.responseBuilder("false", "404", "Template not found", null);
            }
        } catch (Exception e) {
            return RestResponse.responseBuilder("false", "500", "An error occurred", Json.createObjectBuilder().add("error", e.getMessage()).build());
        }
    }

}
