import { RegisterRequest } from "./RegisterRequest";

export class RegisterResponse {
    status: string;
    message: string;
    registerRequest: RegisterRequest;
}
