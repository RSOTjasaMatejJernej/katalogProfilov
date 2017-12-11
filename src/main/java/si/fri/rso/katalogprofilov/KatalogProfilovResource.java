
package si.fri.rso.katalogprofilov;

import com.kumuluz.ee.common.runtime.EeRuntime;
import org.eclipse.microprofile.metrics.annotation.Metered;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;



@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("katalogProfilov")
@ApplicationScoped
public class KatalogProfilovResource {

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
}
