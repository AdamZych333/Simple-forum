export interface User {
    id: number,
    email: string,
    name: string,
    roles: [{name: string}],
}

export interface AuthenticatedUser extends User {
    token: string,
}