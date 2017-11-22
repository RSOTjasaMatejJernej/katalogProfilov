
package si.fri.rso.socialnetwork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;



@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("katalogProfilov")
public class KatalogProfilovResource {

    @GET
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
}
