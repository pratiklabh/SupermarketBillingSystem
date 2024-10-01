package com.syntech.sbs.api;

import com.syntech.sbs.model.Sales;
import com.syntech.sbs.model.SalesDetails;
import com.syntech.sbs.repository.SalesDetailsRepository;
import com.syntech.sbs.repository.SalesRepository;
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
    @Path("/combined")
    public Response getCombinedSalesDetails() {
        try {
            List<Sales> salesList = salesRepository.findAll();
            JsonArrayBuilder combinedArrayBuilder = Json.createArrayBuilder();

            for (Sales sale : salesList) {
                List<SalesDetails> detailsList = salesDetailsRepository.findBySalesId(sale.getId());
                for (SalesDetails detail : detailsList) {
                    String productName = detail.getProduct().getName();
                    BigInteger rate = detail.getRate();
                    BigInteger discount = detail.getDiscount();
                    BigInteger quantity = BigInteger.valueOf(detail.getQuantity());

                    BigInteger total = (rate.subtract(discount)).multiply(quantity);

                    // Create combined sales object
                    JsonObject jsonCombined = Json.createObjectBuilder()
                            .add("sales_id", detail.getSales().getId())
                            .add("date", sale.getDate().toString())
                            .add("productName", productName)
                            .add("rate", rate)
                            .add("quantity", quantity)
                            .add("discount", discount)
                            .add("total", total)
                            .build();

                    combinedArrayBuilder.add(jsonCombined);
                }
            }

            return RestResponse.responseBuilder("true", "200", "Combined sales details retrieved successfully", combinedArrayBuilder.build());
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
    @Path("/combined/{salesId}")
    public Response getCombinedSalesDetailsById(@PathParam("salesId") Long salesId) {
        try {
            Sales sale = salesRepository.findById(salesId);
            if (sale == null) {
                return RestResponse.responseBuilder("false", "404", "Sale not found", null);
            }

            JsonArrayBuilder combinedArrayBuilder = Json.createArrayBuilder();
            List<SalesDetails> detailsList = salesDetailsRepository.findBySalesId(sale.getId());

            for (SalesDetails detail : detailsList) {
                String productName = detail.getProduct().getName();
                BigInteger rate = detail.getRate();
                BigInteger discount = detail.getDiscount();
                BigInteger quantity = BigInteger.valueOf(detail.getQuantity());

                BigInteger total = (rate.subtract(discount)).multiply(quantity);

                // Create combined sales object
                JsonObject jsonCombined = Json.createObjectBuilder()
                        .add("sales_id", detail.getSales().getId())
                        .add("date", sale.getDate().toString())
                        .add("productName", productName)
                        .add("rate", rate)
                        .add("quantity", quantity)
                        .add("discount", discount)
                        .add("total", total)
                        .build();

                combinedArrayBuilder.add(jsonCombined);
            }

            return RestResponse.responseBuilder("true", "200", "Combined sales details retrieved successfully", combinedArrayBuilder.build());
        } catch (Exception e) {
            return RestResponse.responseBuilder("false", "500", "An error occurred", Json.createObjectBuilder().add("error", e.getMessage()).build());
        }
    }

}
