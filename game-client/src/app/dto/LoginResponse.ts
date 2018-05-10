import { LoginRequest } from "./LoginRequest";

export class LoginResponse {
    status: string;
    message: string;
    sessionToken: string;
    loginRequest: LoginRequest;
}
