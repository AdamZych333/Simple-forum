import { Tag } from "./tag.model";
import { Votes } from "./vote.model";

export interface Post{
    id: number,
    title: string,
    content: string,
    createdAt: Date,
    lastModificationAt: Date,
    userID: number,
    tags: Tag[],
    vote: Votes,
    commentsCount: number,
    followsCount: number,
    followed: boolean,
}