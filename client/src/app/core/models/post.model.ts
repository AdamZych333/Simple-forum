import { Tag } from "./tag.model";
import { Votes } from "./vote.model";

export interface Post{
    id: number,
    title: string,
    content: string,
    createdAt: Date,
    lastModificationAt: Date,
    username: string,
    tags: Tag[],
    vote: Votes,
    commentsCount: number,
    followsCount: number,
}