import { Tag } from "./tag.model";

export interface Post{
    id: number,
    title: string,
    content: string,
    createdAt: Date,
    lastModificationAt: Date,
    userID: number,
    tags: Tag[],
    commentsCount: number,
    newActivity?: number,
}