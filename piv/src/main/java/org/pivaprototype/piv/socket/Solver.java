package org.pivaprototype.piv.socket;

import org.pivaprototype.socket.payload.Request;
import org.pivaprototype.socket.payload.Response;

@FunctionalInterface
public interface Solver<R, P> {

    Response<R> solve(Request<P> request);

}

