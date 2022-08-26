export enum VoteTypes{
    UP = "UP", 
    DOWN = "DOWN",
}

export interface Votes{
    type: string,
    postID: number,
    count: number,
    voted: boolean,
}