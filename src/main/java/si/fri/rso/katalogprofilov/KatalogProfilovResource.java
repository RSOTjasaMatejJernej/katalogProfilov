
package si.fri.rso.katalogprofilov;

import com.kumuluz.ee.common.runtime.EeRuntime;
import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.logging.Logger;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("katalogProfilov")
@ApplicationScoped
@Log
public class KatalogProfilovResource {

    private Logger log = Logger.getLogger(KatalogProfilovResource.class.getName());

    @Inject
    private RestProperties restProperties;

    @GET
    @Metered
    public Response getAllProfils() {
        List<Profil> profils = Database.getProfils();
        log.info("getAllProfils().. klic");
        return Response.ok(profils).build();
    }

    @GET
    @Path("demo/info")
    public Response info() {

        JSONObject json = new JSONObject();

        JSONArray clani = new JSONArray();
        clani.put("tj9557");
        clani.put("jj2744");
        clani.put("mp9119");

        JSONArray mikrostoritve = new JSONArray();
        mikrostoritve.put("http://169.51.24.248:32112/v1/katalogProfilov/");
        mikrostoritve.put("http://169.51.24.248:32620/v1/upravljanjeProfilov/1");
        mikrostoritve.put("http://169.51.24.248:31039/v1/obvestilniSistem/");
        mikrostoritve.put("http://169.51.24.248:32316/v1/sporocilniSistem/");

        JSONArray github = new JSONArray();
        github.put("https://github.com/RSOTjasaMatejJernej/katalogProfilov");
        github.put("https://github.com/RSOTjasaMatejJernej/upravljanjeProfilov");
        github.put("https://github.com/RSOTjasaMatejJernej/obvestilniSistem");
        github.put("https://github.com//RSOTjasaMatejJernej/sporocilniSistem");

        JSONArray dockerhub = new JSONArray();
        dockerhub.put("https://hub.docker.com/r/tjasaj/katalogProfilov");
        dockerhub.put("https://hub.docker.com/r/tjasaj/upravljanjeProfilov");
        dockerhub.put("https://hub.docker.com/r/tjasaj/obvestilniSistem");
        dockerhub.put("https://hub.docker.com/r/tjasaj/sporocilniSistem");

        JSONArray travis = new JSONArray();
        travis.put("https://travis-ci.org/RSOTjasaMatejJernej/katalogProfilov");
        travis.put("https://travis-ci.org/RSOTjasaMatejJernej/upravljanjeProfilov");
        travis.put("https://travis-ci.org/RSOTjasaMatejJernej/obvestilniSistem");
        travis.put("https://travis-ci.org/RSOTjasaMatejJernej/sporocilniSistem");

        json.put("clani", clani);
        json.put("opis_projekta", "Nas projekt implementira socialno omrezje.");
        json.put("mikrostoritve", mikrostoritve);
        json.put("github", github);
        json.put("travis", travis);
        json.put("dockerhub", dockerhub);

        return Response.ok(json.toString()).build();
    }

    @GET
    @Path("{profilId}")
    public Response getProfil(@PathParam("profilId") Integer profilId) {
        Profil profil = Database.getProfil(profilId.toString());
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
        restProperties.setHealthy(healthy);
        log.info("Setting health to " + healthy);
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


}