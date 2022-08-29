export interface Comment {
    id: number,
    content: string,
    createdAt: Date,
    latModificationAt: Date,
    userID: number,
    postID: number,
    parentID: number | null,
}