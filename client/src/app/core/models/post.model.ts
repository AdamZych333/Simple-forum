import { Tag } from "./tag.model";
import { Vote } from "./vote.model";

export interface Post{
    id: number,
    title: string,
    content: string,
    createdAt: Date,
    lastModificationAt: Date,
    username: string,
    tags: Tag[],
    votes: Vote[],
    commentsCount: number,
    followsCount: number,
}