//package com.syntech.sbs.api;
//
//import com.syntech.sbs.model.Contact;
//import com.syntech.sbs.repository.ContactRepository;
//import java.util.List;
//import javax.inject.Inject;
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//@Path("/contacts")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
//public class ContactRestApi {
//
//    @Inject
//    private ContactRepository contactRepository;
//
//    @GET
//    public Response getAllContacts() {
//        try {
//            List<Contact> contacts = contactRepository.findAll();
//            return RestResponse.responseBuilder("true", "200", "Contacts retrieved successfully", contacts.toString());
//        } catch (Exception e) {
//            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
//        }
//    }
//
//    @GET
//    @Path("/{id}")
//    public Response getContactById(@PathParam("id") Long id) {
//        try {
//            Contact contact = contactRepository.findById(id);
//            if (contact != null) {
//                return RestResponse.responseBuilder("true", "200", "Contact found", contact.toString());
//            } else {
//                return RestResponse.responseBuilder("false", "404", "Contact not found", null);
//            }
//        } catch (Exception e) {
//            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
//        }
//    }
//
//    @POST
//    public Response createContact(Contact contact) {
//        try {
//            contactRepository.save(contact);
//            return RestResponse.responseBuilder("true", "201", "Contact created successfully", contact.toString());
//        } catch (Exception e) {
//            return RestResponse.responseBuilder("false", "400", "Failed to create contact", e.getMessage());
//        }
//    }
//
//    @PUT
//    @Path("/{id}")
//    public Response updateContact(@PathParam("id") Long id, Contact contact) {
//        try {
//            Contact existingContact = contactRepository.findById(id);
//            if (existingContact != null) {
//                contact.setId(id);
//                contactRepository.update(contact);
//                return RestResponse.responseBuilder("true", "200", "Contact updated successfully", contact.toString());
//            } else {
//                return RestResponse.responseBuilder("false", "404", "Contact not found", null);
//            }
//        } catch (Exception e) {
//            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
//        }
//    }
//
//    @DELETE
//    @Path("/{id}")
//    public Response deleteContact(@PathParam("id") Long id) {
//        try {
//            Contact contact = contactRepository.findById(id);
//            if (contact != null) {
//                contactRepository.delete(id);
//                return RestResponse.responseBuilder("true", "204", "Contact deleted successfully", null);
//            } else {
//                return RestResponse.responseBuilder("false", "404", "Contact not found", null);
//            }
//        } catch (Exception e) {
//            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
//        }
//    }
//}
