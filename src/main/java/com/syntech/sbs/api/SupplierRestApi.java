//package com.syntech.sbs.api;
//
//import com.syntech.sbs.model.Supplier;
//import com.syntech.sbs.repository.SupplierRepository;
//import java.util.List;
//import javax.inject.Inject;
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//@Path("/suppliers")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
//public class SupplierRestApi {
//
//    @Inject
//    private SupplierRepository supplierRepository;
//
//    @GET
//    public Response getAllSuppliers() {
//        try {
//            List<Supplier> suppliers = supplierRepository.findAll();
//            return RestResponse.responseBuilder("true", "200", "Suppliers retrieved successfully", suppliers.toString());
//        } catch (Exception e) {
//            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
//        }
//    }
//
//    @GET
//    @Path("/{id}")
//    public Response getSupplierById(@PathParam("id") Long id) {
//        try {
//            Supplier supplier = supplierRepository.findById(id);
//            if (supplier != null) {
//                return RestResponse.responseBuilder("true", "200", "Supplier found", supplier.toString());
//            } else {
//                return RestResponse.responseBuilder("false", "404", "Supplier not found", null);
//            }
//        } catch (Exception e) {
//            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
//        }
//    }
//
//    @POST
//    public Response createSupplier(Supplier supplier) {
//        try {
//            supplierRepository.save(supplier);
//            return RestResponse.responseBuilder("true", "201", "Supplier created successfully", supplier.toString());
//        } catch (Exception e) {
//            return RestResponse.responseBuilder("false", "400", "Failed to create supplier", e.getMessage());
//        }
//    }
//
//    @PUT
//    @Path("/{id}")
//    public Response updateSupplier(@PathParam("id") Long id, Supplier supplier) {
//        try {
//            Supplier existingSupplier = supplierRepository.findById(id);
//            if (existingSupplier != null) {
//                supplier.setId(id);
//                supplierRepository.update(supplier);
//                return RestResponse.responseBuilder("true", "200", "Supplier updated successfully", supplier.toString());
//            } else {
//                return RestResponse.responseBuilder("false", "404", "Supplier not found", null);
//            }
//        } catch (Exception e) {
//            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
//        }
//    }
//
//    @DELETE
//    @Path("/{id}")
//    public Response deleteSupplier(@PathParam("id") Long id) {
//        try {
//            Supplier supplier = supplierRepository.findById(id);
//            if (supplier != null) {
//                supplierRepository.delete(id);
//                return RestResponse.responseBuilder("true", "204", "Supplier deleted successfully", null);
//            } else {
//                return RestResponse.responseBuilder("false", "404", "Supplier not found", null);
//            }
//        } catch (Exception e) {
//            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
//        }
//    }
//}
