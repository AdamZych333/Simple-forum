export enum VoteType{
    UP='UP', 
    DOWN='DOWN',
}

export interface Vote{
    type: VoteType,
    count: number,
}