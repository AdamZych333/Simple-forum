export interface Post{
    id: number,
    title: string,
    content: string,
    createdAt: Date,
    lastModificationAt: Date,
    userID: number,
    tags: [],
    votes: [],
    commentsCount: number,
    followsCount: number,
}