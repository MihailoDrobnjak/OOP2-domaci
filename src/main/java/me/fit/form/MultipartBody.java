package me.fit.form;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.providers.multipart.PartType;
import java.io.InputStream;

public class MultipartBody {

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream fileData;

    @FormParam("fileName")
    @PartType(MediaType.TEXT_PLAIN)
    public String fileName;
}