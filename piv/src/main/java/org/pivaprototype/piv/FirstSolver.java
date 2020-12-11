package org.pivaprototype.piv;

import org.pivaprototype.piv.socket.Solver;
import org.pivaprototype.socket.payload.Request;
import org.pivaprototype.socket.payload.Response;

public class FirstSolver implements Solver {
    @Override
    public Response<Integer> solve(Request request) {
        Response<Integer> response = new Response();
        response.setStatus(0);
        response.setData(27);
        return response;
    }
}
