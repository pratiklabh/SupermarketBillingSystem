package com.syntech.sbs.api;

import com.syntech.sbs.model.Supplier;
import com.syntech.sbs.repository.SupplierRepository;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/suppliers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SupplierRestApi {

    @Inject
    private SupplierRepository supplierRepository;

    @GET
    public Response getAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();
        return Response.ok(suppliers).build();
    }

    @GET
    @Path("/{id}")
    public Response getSupplierById(@PathParam("id") Long id) {
        Supplier supplier = supplierRepository.findById(id);
        if (supplier != null) {
            return Response.ok(supplier).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    
    @POST
    public Response createSupplier(Supplier supplier) {
        try {
            supplierRepository.save(supplier);
            return Response.status(Response.Status.CREATED).entity(supplier).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateSupplier(@PathParam("id") Long id, Supplier supplier) {
        Supplier existingSupplier = supplierRepository.findById(id);
        if (existingSupplier != null) {
            supplier.setId(id);
            supplierRepository.update(supplier);
            return Response.ok(supplier).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSupplier(@PathParam("id") Long id) {
        Supplier supplier = supplierRepository.findById(id);
        if (supplier != null) {
            supplierRepository.delete(id);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
