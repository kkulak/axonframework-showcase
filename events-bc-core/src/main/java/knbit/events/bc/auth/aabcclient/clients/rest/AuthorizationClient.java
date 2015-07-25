package knbit.events.bc.auth.aabcclient.clients.rest;

import knbit.events.bc.auth.Role;
import knbit.events.bc.auth.aabcclient.authorization.AuthorizationResult;
import lombok.Value;

/**
 * Created by novy on 25.07.15.
 */
class AuthorizationClient {

    private final String authorizationEndpoint;
    private final String tokenHeaderKey;

    public AuthorizationClient(String authorizationEndpoint, String tokenHeaderKey) {
        this.authorizationEndpoint = authorizationEndpoint;
        this.tokenHeaderKey = tokenHeaderKey;
    }

    public AuthorizationResult authorizeWith(String token, Role role) {
        return null;
    }


//    @Override
//    public AuthenticationResult authenticateWith(String token) {
//        return authenticationClient;
//    }

    //
//    @Override
//    public boolean hasRole(String token, Role role) {
//        return new Right<Http>()gotOkAsResponse(token, role);
//    }
//
//    private boolean gotOkAsResponse(String token, Role role) {
//        final HttpEntity<PermissionDto> request = createRequestFor(token, role);
//        final ResponseEntity<PermissionDto> response =
//                restTemplate.exchange(authorizationEndpoint, HttpMethod.POST, request, PermissionDto.class);
//
//        return response.getStatusCode() == HttpStatus.OK;
//    }
//
//    private HttpEntity<PermissionDto> createRequestFor(String token, Role role) {
//        final HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(tokenHeaderKey, token);
//        return new HttpEntity<>(new PermissionDto(role.name()), httpHeaders);
//    }
//


    @Value
    private static class PermissionDto {
        private final String permission;
    }
}
