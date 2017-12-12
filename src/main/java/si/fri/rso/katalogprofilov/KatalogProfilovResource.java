
package si.fri.rso.katalogprofilov;

import com.kumuluz.ee.common.runtime.EeRuntime;
import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.metrics.annotation.Metered;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;


 import com.kumuluz.ee.logs.LogManager;
 import com.kumuluz.ee.logs.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("katalogProfilov")
@ApplicationScoped
@Log
public class KatalogProfilovResource {
    private Logger log = LogManager.getLogger(KatalogProfilovResource.class.getName());

    @Inject
    private RestProperties restProperties;

    @GET
    @Metered
    public Response getAllProfils() {
        List<Profil> profils = Database.getProfils();
        return Response.ok(profils).build();
    }

    @GET
    @Path("{profilId}")
    public Response getProfil(@PathParam("profilId") String profilId) {
        Profil profil = Database.getProfil(profilId);
        return profil != null
                ? Response.ok(profil).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response addNewProfil(Profil profil) {
        Database.addProfil(profil);
        return Response.ok(profil).build();
    }

    @DELETE
    @Path("{profilId}")
    public Response deleteProfil(@PathParam("profilId") String profilId) {
        Database.deleteProfil(profilId);
        return Response.ok(Response.Status.OK).build();
    }

    @POST
    @Path("healthy")
    public Response setHealth(Boolean healthy) {
        log.info("Setting health to " + healthy);
        restProperties.setHealthy(healthy);
        return Response.ok().build();
    }

    @POST
    @Path("load")
    public Response loadOrder(Integer n) {

        for (int i = 1; i <= n; i++) {
            fibonacci(i);
        }

        return Response.status(Response.Status.OK).build();
    }

    private long fibonacci(int n) {
        if (n <= 1) return n;
        else return fibonacci(n - 1) + fibonacci(n - 2);
    }

    @GET
    @Path("instanceid")
    public Response getInstanceId() {

        String instanceId =
                "{\"instanceId\" : \"" + EeRuntime.getInstance().getInstanceId() + "\"}";

        return Response.ok(instanceId).build();
    }

    @GET
    @Path("info")
    public Response info() {

        JSONObject json = new JSONObject();

        JSONArray clani = new JSONArray();
        clani.put("tj9557");
        clani.put("jj2744");
        clani.put("tj9557");

        JSONArray mikrostoritve = new JSONArray();
        mikrostoritve.put("http://169.51.24.248:31386/v1/katalogProfilov/");
        mikrostoritve.put("http://169.51.24.248:31039/v1/obvestilniSistem/");
        mikrostoritve.put("http://169.51.24.248:32316/v1/sporocilniSistem/");
        mikrostoritve.put("http://169.51.24.248:31386/v1/katalogProfilov/");

        JSONArray github = new JSONArray();
        github.put("https://github.com/RSOTjasaMatejJernej");

        JSONArray travis = new JSONArray();
        travis.put("https://travis-ci.org/RSOTjasaMatejJernej");

        JSONArray dockerhub = new JSONArray();
        dockerhub.put("https://hub.docker.com/r/tjasaj/");

        json.put("clani", clani);
        json.put("opis_projekta", "Nas projekt implementira socialno omrežje.");
        json.put("mikrostoritve", mikrostoritve);
        json.put("github", github);
        json.put("travis", travis);
        json.put("dockerhub", dockerhub);

        return Response.ok(json.toString()).build();
    }

}
