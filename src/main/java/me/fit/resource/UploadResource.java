package me.fit.resource;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.fit.form.MultipartBody;
import me.fit.model.Customer;
import me.fit.model.UploadedFile;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Path("/files")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UploadResource {

    @Inject
    EntityManager em;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    public Response uploadFile(@MultipartForm MultipartBody form, @QueryParam("id") Long id) {

        Customer customer = em.find(Customer.class, id);
        if (customer == null) {
            throw new WebApplicationException("Kupac sa ovim ID-jem ne postoji", 404);
        }

        try {
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File targetFile = new File(UPLOAD_DIR + form.fileName);
            UploadedFile uploadedFile;

            if (targetFile.exists()) {
                List<UploadedFile> existing = em.createQuery(
                                "SELECT f FROM UploadedFile f WHERE f.filename = :path", UploadedFile.class)
                        .setParameter("path", targetFile.getAbsolutePath())
                        .getResultList();

                if (!existing.isEmpty()) {
                    uploadedFile = existing.get(0);
                } else {
                    uploadedFile = new UploadedFile();
                    uploadedFile.filename = targetFile.getAbsolutePath();
                    em.persist(uploadedFile);
                }

                if (!customer.uploadedFiles.contains(uploadedFile)) {
                    customer.uploadedFiles.add(uploadedFile);
                    em.merge(customer);
                }

                return Response.status(Response.Status.OK)
                        .header("X-Status", "Fajl vec postoji, povezan postojeci")
                        .entity(customer)
                        .build();

            } else {
                Files.copy(form.fileData, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                uploadedFile = new UploadedFile();
                uploadedFile.filename = targetFile.getAbsolutePath();
                em.persist(uploadedFile);

                customer.uploadedFiles.add(uploadedFile);
                em.merge(customer);

                return Response.status(Response.Status.CREATED).entity(customer).build();
            }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Greška: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/customer/{id}")
    public Response getCustomerWithFiles(@PathParam("id") Long id) {

        Customer customer = em.find(Customer.class, id);
        if (customer == null) {
            throw new WebApplicationException("Kupac ne postoji", 404);
        }


        for (UploadedFile uf : customer.uploadedFiles) {
            if (uf.filename != null) {
                uf.file = new File(uf.filename);
            }
        }

        return Response.ok(customer).build();
    }
}