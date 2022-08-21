export interface User {
    id: number,
    email: string,
    name: string,
    token: string,
    roles: [{name: string}],
}