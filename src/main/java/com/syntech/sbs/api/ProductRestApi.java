//package com.syntech.sbs.api;
//
//import com.syntech.sbs.model.Product;
//import com.syntech.sbs.repository.ProductRepository;
//import java.util.List;
//import javax.inject.Inject;
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//@Path("/products")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
//public class ProductRestApi {
//
//    @Inject
//    private ProductRepository productRepository;
//
//    @GET
//    public Response getAllProducts() {
//        try {
//            List<Product> products = productRepository.findAll();
//            return RestResponse.responseBuilder("true", "200", "Products retrieved successfully", products.toString());
//        } catch (Exception e) {
//            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
//        }
//    }
//
//    @GET
//    @Path("/{id}")
//    public Response getProductById(@PathParam("id") Long id) {
//        try {
//            Product product = productRepository.findById(id);
//            if (product != null) {
//                return RestResponse.responseBuilder("true", "200", "Product found", product.toString());
//            } else {
//                return RestResponse.responseBuilder("false", "404", "Product not found", null);
//            }
//        } catch (Exception e) {
//            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
//        }
//    }
//
//    @POST
//    public Response createProduct(Product product) {
//        try {
//            productRepository.save(product);
//            return RestResponse.responseBuilder("true", "201", "Product created successfully", product.toString());
//        } catch (Exception e) {
//            return RestResponse.responseBuilder("false", "400", "Failed to create product", e.getMessage());
//        }
//    }
//
//    @PUT
//    @Path("/{id}")
//    public Response updateProduct(@PathParam("id") Long id, Product product) {
//        try {
//            Product existingProduct = productRepository.findById(id);
//            if (existingProduct != null) {
//                product.setId(id);
//                productRepository.update(product);
//                return RestResponse.responseBuilder("true", "200", "Product updated successfully", product.toString());
//            } else {
//                return RestResponse.responseBuilder("false", "404", "Product not found", null);
//            }
//        } catch (Exception e) {
//            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
//        }
//    }
//
//    @DELETE
//    @Path("/{id}")
//    public Response deleteProduct(@PathParam("id") Long id) {
//        try {
//            Product product = productRepository.findById(id);
//            if (product != null) {
//                productRepository.delete(id);
//                return RestResponse.responseBuilder("true", "204", "Product deleted successfully", null);
//            } else {
//                return RestResponse.responseBuilder("false", "404", "Product not found", null);
//            }
//        } catch (Exception e) {
//            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
//        }
//    }
//}
