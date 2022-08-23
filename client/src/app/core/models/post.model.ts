export interface Post{
    id: number,
    title: string,
    content: string,
    createdAt: Date,
    lastModificationAt: Date,
    username: string,
    tags: [],
    votes: [],
    commentsCount: number,
    followsCount: number,
}